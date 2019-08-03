package test.org.dst.rpc;

import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.baidu.brpc.client.RpcClientOptions;
import com.baidu.brpc.protocol.Options;
import org.dst.core.operatorset.DstString;
import org.dst.server.generated.DstServerProtocol;
import org.dst.server.generated.EchoExample;
import org.dst.server.service.DstStringService;
import org.dst.server.service.EchoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DstRpcServerTest {

  @Test
  public void testRpcServer() {
    TestUtil.startRpcServer();

    RpcClientOptions options = new RpcClientOptions();
    options.setProtocolType(Options.ProtocolType.PROTOCOL_BAIDU_STD_VALUE);
    options.setWriteTimeoutMillis(1000);
    options.setReadTimeoutMillis(1000);
    options.setMaxTotalConnections(1000);
    options.setMinIdleConnections(10);

    String url = "list://127.0.0.1:8082";

    RpcClient client = new RpcClient(url, options);

    //Construct a request
//    EchoExample.EchoRequest request = EchoExample.EchoRequest
//            .newBuilder().setMessage("Hefei University of Technology!").build();
//
//    EchoService echoService = BrpcProxy.getProxy(client, EchoService.class);
//    EchoExample.EchoResponse response = echoService.echo(request);
//    Assertions.assertEquals("Hefei University of Technology!",response.getMessage());

    // Test string request
    DstServerProtocol.StringRequest stringRequest = DstServerProtocol.StringRequest.newBuilder()
        .setRequestType(DstServerProtocol.StringRequestType.PUT)
        .setKey("k1")
        .setValue("v1")
        .build();

    DstStringService stringService = BrpcProxy.getProxy(client, DstStringService.class);
    DstServerProtocol.StringResponse stringResponse = stringService.handleString(stringRequest);
    Assertions.assertEquals(DstServerProtocol.StringResponseType.STATUS_RESPONSE, stringResponse.getResponseType());
    Assertions.assertEquals("ok", stringResponse.getResult());
    client.stop();

    TestUtil.stopRpcServer();

  }
}
