package org.dst.rpc.protocol.example.async;

import org.dst.rpc.transport.api.async.DefaultResponse;
import org.dst.rpc.transport.api.async.Response;

/**
 * @author zrj CreateDate: 2019/10/28
 */
public class IServerImpl implements IServer {

  @Override
  public Response say() {
    Response response = new DefaultResponse();
    response.setValue("dst");
    sleep(5000);
    return response;
  }

  @Override
  public Response say(String name) {
    Response response = new DefaultResponse();
    response.setValue("dst " + name);
    sleep(5000);
    return response;
  }

  private void sleep(long t) {
    try {
      Thread.sleep(t);
    } catch (Exception e) {
      // ignore
    }
  }
}
