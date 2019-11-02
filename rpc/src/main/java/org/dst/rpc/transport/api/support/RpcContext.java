package org.dst.rpc.transport.api.support;

import org.dst.rpc.transport.api.Channel;

/**
 * @author zrj CreateDate: 2019/11/2
 */
public class RpcContext {

  private static ThreadLocal<RpcContext> LOCAL_CONTEXT = new ThreadLocal<>();

  private volatile boolean used = false;
  private Channel channel;

  public RpcContext(Channel channel) {
    this.channel = channel;
  }

  public static RpcContext getCurrentContext() {
    return LOCAL_CONTEXT.get();
  }

  public static RpcContext createRpcContext(Channel channel) {
    RpcContext context = new RpcContext(channel);
    LOCAL_CONTEXT.set(context);
    return context;
  }

  public static void removeRpcContext() {
    LOCAL_CONTEXT.remove();
  }

  public Channel getChannel() {
    return channel;
  }

  public boolean isUsed() {
    return used;
  }

  public void setUsed() {
    this.used = true;
  }
}
