package org.dst.rpc.transport.netty;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import org.dst.rpc.core.exception.DstException;
import org.dst.rpc.transport.api.AbstractChannel;
import org.dst.rpc.transport.api.Channel;
import org.dst.rpc.transport.api.Handler;
import org.dst.rpc.transport.api.async.DefaultResponse;
import org.dst.rpc.transport.api.async.Request;
import org.dst.rpc.transport.api.async.Response;
import org.dst.rpc.transport.api.support.RpcContext;

/**
 * @author zrj CreateDate: 2019/11/2
 */
public class ServerChannelHandler extends ChannelDuplexHandler {

  private Handler handler;
  private NettyServer nettyServer;

  public ServerChannelHandler(NettyServer nettyServer) {
    this.nettyServer = nettyServer;
    this.handler = nettyServer.getRoutableHandler();
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    Object object = nettyServer.getCodec().decode((byte[]) msg);
    if (!(object instanceof Request)) {
      throw new DstException(
          "ServerChannelHandler: unsupported message type when decode: " + object.getClass());
    }
    if (nettyServer.getExecutor() != null) {
      nettyServer.getExecutor().execute(() -> processRequest(ctx, (Request) object));
    } else {
      processRequest(ctx, (Request) object);
    }
  }

  private void processRequest(ChannelHandlerContext ctx, Request request) {
    RpcContext context = createRpcContext(ctx, request);
    Response response = (Response) handler.handle(request);
    if(!context.isUsed()) {
      response.setRequestId(request.getRequestId());
      sendResponse(ctx, response);
    }
  }

  private RpcContext createRpcContext(ChannelHandlerContext ctx, Request request) {
    Channel channel = new AbstractChannel() {

      @Override
      public void send(Object message) {
        Response response = new DefaultResponse();
        response.setRequestId(request.getRequestId());
        if(message instanceof Exception) {
          response.setException((Exception) message);
        } else {
          response.setValue(message);
        }
        sendResponse(ctx, response);
      }
    };
    return RpcContext.createRpcContext(channel);
  }

  private ChannelFuture sendResponse(ChannelHandlerContext ctx, Response response) {
    byte[] msg = nettyServer.getCodec().encode(response);
    if (ctx.channel().isActive()) {
      return ctx.channel().writeAndFlush(msg);
    }
    return null;
  }

}
