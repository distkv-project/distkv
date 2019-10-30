package org.dst.rpc.protocol.example.async;

import org.dst.rpc.protocol.Reference;
import org.dst.rpc.transport.api.async.AsyncResponse;

/**
 * @author zrj CreateDate: 2019/10/28
 */
public class Client {

  public static void main(String[] args) throws Exception {
    Reference<IServer> reference = new Reference<>();
    reference.setAddress("dst://127.0.0.1:8080");
    reference.setAsync(true);
    reference.setInterfaceClass(IServer.class);

    IServer server = reference.getReference();
    AsyncResponse response = (AsyncResponse) server.say();
    long b = System.currentTimeMillis();
    System.out.println("getResponse " + b);
    response.await();
    long e = System.currentTimeMillis();
    System.out.println("getValue " + e + " cost: " + (e - b));
    System.out.println(response.getValue().toString());
  }

}
