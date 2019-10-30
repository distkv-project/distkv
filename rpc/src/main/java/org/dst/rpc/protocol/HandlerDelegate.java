package org.dst.rpc.protocol;

import org.dst.rpc.transport.api.Handler;
import org.dst.rpc.transport.api.async.Request;

/**
 * @author zrj CreateDate: 2019/10/28
 */
public class HandlerDelegate implements Handler {

  private Invoker serverImpl;

  public HandlerDelegate(Invoker serverImpl) {
    this.serverImpl = serverImpl;
  }

  @Override
  public String getServerName() {
    return serverImpl.getInterface().getName();
  }

  @Override
  public Object handle(Object message) {
    return serverImpl.invoke((Request) message);
  }
}
