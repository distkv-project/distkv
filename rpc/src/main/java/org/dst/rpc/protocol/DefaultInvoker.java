package org.dst.rpc.protocol;


import org.dst.rpc.core.common.URL;
import org.dst.rpc.transport.api.Client;
import org.dst.rpc.transport.api.async.Request;
import org.dst.rpc.transport.api.async.Response;

/**
 * @author zrj CreateDate: 2019/10/28
 */
public class DefaultInvoker<T> implements Invoker<T> {

  private Client client;

  public DefaultInvoker(Client client) {
    this.client = client;
  }

  @Override
  public URL getURL() {
    return null;
  }

  @Override
  public Class<T> getInterface() {
    return null;
  }

  @Override
  public Response invoke(Request request) {
    return client.invoke(request);
  }

}
