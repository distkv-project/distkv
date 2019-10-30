package org.dst.rpc.transport.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.dst.rpc.core.codec.Codec;
import org.dst.rpc.core.common.URL;
import org.dst.rpc.core.exception.DstException;
import org.dst.rpc.transport.api.AbstractChannel;
import org.dst.rpc.transport.api.AbstractServer;
import org.dst.rpc.transport.api.Channel;
import org.dst.rpc.transport.api.Handler;
import org.dst.rpc.transport.api.async.Request;
import org.dst.rpc.transport.api.async.Response;
import org.dst.rpc.transport.codec.DstCodec;
import org.dst.rpc.transport.codec.ProtoBufSerialization;
import org.dst.rpc.transport.netty.codec.NettyDecoder;
import org.dst.rpc.transport.netty.codec.NettyEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NettyServer extends AbstractServer {

  private static Logger logger = LoggerFactory.getLogger(NettyServer.class);

  private io.netty.channel.Channel serverChannel;
  private NioEventLoopGroup bossGroup;
  private NioEventLoopGroup workerGroup;


  public NettyServer(URL url, Handler handler) {
    super(url);
    getRoutableHandler().registerHandler(handler);
    bossGroup = new NioEventLoopGroup(1);
    workerGroup = new NioEventLoopGroup();
  }


  /**
   * fixme Endpoint 和 Channel 是一对多的关系。 fixme Channel这个设计 目前好像没有什么用
   */
  @Override
  protected Channel createChannel() {
    Channel channel = new AbstractChannel() {
      @Override
      protected void doOpen() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
              @Override
              protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("decoder", new NettyDecoder());
                pipeline.addLast("encoder", new NettyEncoder());
                pipeline.addLast("handler", new ServerChannelHandler(getRoutableHandler()));
              }
            });
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
          ChannelFuture f = serverBootstrap.bind(getUrl().getPort()).sync();
          serverChannel = f.channel();
        } catch (Exception e) {
          // todo: log
          logger.error("NettyServer bind error", e);
        }
      }

      @Override
      protected void doClose() {
        if (serverChannel != null) {
          serverChannel.close();
        }
        if (bossGroup != null) {
          bossGroup.shutdownGracefully();
          bossGroup = null;
        }
        if (workerGroup != null) {
          workerGroup.shutdownGracefully();
          workerGroup = null;
        }
      }

      /**
       * 这里千万不要使用serverChannel.write，serverChannel是父channel，用来接收accept事件的，
       * 真正的worker channel不再这里。
       */
      @Override
      public void send(Object message) {
        throw new UnsupportedOperationException();
      }

      @Override
      public void receive(Object message) {
        throw new UnsupportedOperationException();
      }

    };
    Codec codec = new DstCodec(new ProtoBufSerialization());
    channel.setCodec(codec);
    return channel;
  }


  private class ServerChannelHandler extends ChannelDuplexHandler {

    private Handler handler;

    public ServerChannelHandler(Handler handler) {
      this.handler = handler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
      Object object = getChannel().getCodec().decode((byte[]) msg);
      if (!(object instanceof Request)) {
        throw new DstException(
            "ServerChannelHandler: unsupported message type when decode: " + object.getClass());
      }
      if (getExecutor() != null) {
        getExecutor().execute(() -> processRequest(ctx, (Request) object));
      } else {
        processRequest(ctx, (Request) object);
      }
    }

    private void processRequest(ChannelHandlerContext ctx, Request msg) {
      // 如果方法中含有channel参数的话，自动注入
      Response response = (Response) handler.handle(msg);
      response.setRequestId(msg.getRequestId());
      sendResponse(ctx, response);
    }

    private ChannelFuture sendResponse(ChannelHandlerContext ctx, Response response) {
      byte[] msg = getChannel().getCodec().encode(response);
      if (ctx.channel().isActive()) {
        return ctx.channel().writeAndFlush(msg);
      }
      return null;
    }
  }
}
