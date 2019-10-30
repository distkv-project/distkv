package org.dst.rpc.protocol.example.pb;

import org.dst.rpc.protobuf.generated.StringProtocol.GetRequest;
import org.dst.rpc.protobuf.generated.StringProtocol.GetResponse;
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

    GetRequest request = GetRequest.newBuilder()
        .setKey("mock key")
        .build();

    // 这里是同步测试，如果要测试同步，先注释掉这行代码后面的代码，然后setAsync改为false
    //System.out.println(server.say(request).getValue());

    AsyncResponse response = (AsyncResponse) server.asyncSay(request);
    long b = System.currentTimeMillis();
    System.out.println("getResponse " + b);
    response.await();
    long e = System.currentTimeMillis();
    System.out.println("getValue " + e + " cost: " + (e - b));
    System.out.println(((GetResponse) response.getValue()).getValue());
  }

}
