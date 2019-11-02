package org.dst.rpc.transport.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.dst.rpc.core.common.URL;
import org.dst.rpc.transport.api.AbstractServer;
import org.dst.rpc.transport.api.Handler;
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
    super(url, new DstCodec(new ProtoBufSerialization()));
    getRoutableHandler().registerHandler(handler);
    bossGroup = new NioEventLoopGroup(1);
    workerGroup = new NioEventLoopGroup();
  }

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
            pipeline.addLast("handler", new ServerChannelHandler(NettyServer.this));
          }
        });
    serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
    serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
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

}
