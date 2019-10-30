package org.dst.rpc.protocol.example.sync;

import org.dst.rpc.protocol.Reference;

/**
 * @author zrj CreateDate: 2019/10/28
 */
public class Client {

  public static void main(String[] args) throws Exception {
    Reference<IServer> reference = new Reference<>();
    reference.setAddress("dst://127.0.0.1:8080");
    reference.setAsync(false);
    reference.setInterfaceClass(IServer.class);

    IServer server = reference.getReference();
    System.out.println(server.say());
    System.out.println(server.say("dst1"));
  }

}
