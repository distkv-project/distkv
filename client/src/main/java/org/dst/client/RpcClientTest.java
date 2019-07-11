package org.dst.client;

import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.baidu.brpc.client.RpcClientOptions;
import com.baidu.brpc.protocol.Options;
import org.dst.server.service.EchoExample;
import org.dst.server.service.EchoService;

public class RpcClientTest {
  public static void main(String[] args) {

    RpcClientOptions options = new RpcClientOptions();
    options.setProtocolType(Options.ProtocolType.PROTOCOL_BAIDU_STD_VALUE);
    options.setWriteTimeoutMillis(1000);
    options.setReadTimeoutMillis(1000);
    options.setMaxTotalConnections(1000);
    options.setMinIdleConnections(10);

    String url = "list://localhost:8082";

    RpcClient client = new RpcClient(url, options);

    //构造请求
    EchoExample.EchoRequest request = EchoExample.EchoRequest
            .newBuilder().setMessage("HFUT").build();

    EchoService echoService = BrpcProxy.getProxy(client, EchoService.class);

    EchoExample.EchoResponse response = echoService.echo(request);
    System.out.printf("sync call service=EchoService.echo success, "
                    + "request=%s,response=%s\n",
            request.getMessage(), response.getMessage());

    client.stop();


  }
}
