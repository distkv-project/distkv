package org.dst.rpc.protocol.example.pb;

import org.dst.rpc.protocol.Reference;
import org.dst.rpc.protocol.example.pb.StringProtocol.GetRequest;

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

    GetRequest request = GetRequest.newBuilder()
        .setKey("mock key")
        .build();
    System.out.println(server.say(request).getValue());
  }

}
