package test.org.dst.rpc;

import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.baidu.brpc.client.RpcClientOptions;
import com.baidu.brpc.protocol.Options;
import org.dst.server.generated.DstServerProtocol;
import org.dst.server.service.DstStringService;
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

    DstStringService stringService = BrpcProxy.getProxy(client, DstStringService.class);

    // Test string put request
    DstServerProtocol.StringPutRequest stringPutRequest = DstServerProtocol.StringPutRequest.newBuilder()
        .setKey("k1")
        .setValue("v1")
        .build();
    DstServerProtocol.StringPutResponse stringResponse = stringService.strPut(stringPutRequest);
    Assertions.assertEquals("ok", stringResponse.getResult());

    // Test string get request
    DstServerProtocol.StringGetRequest strGetRequest = DstServerProtocol.StringGetRequest.newBuilder()
        .setKey("k1")
        .build();
    DstServerProtocol.StringGetResponse stringGetRequest = stringService.strGet(strGetRequest);
    Assertions.assertEquals("v1", stringGetRequest.getResult());
    client.stop();

    TestUtil.stopRpcServer();
  }
}
