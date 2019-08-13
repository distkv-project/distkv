package test.org.dst.rpc;

import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.baidu.brpc.client.RpcClientOptions;
import com.baidu.brpc.protocol.Options;
import com.google.common.collect.ImmutableList;
import junit.framework.Assert;
import org.dst.server.generated.DstServerProtocol;
import org.dst.server.service.DstStringService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    DstServerProtocol.StringPutRequest stringPutRequest =
            DstServerProtocol.StringPutRequest.newBuilder()
        .setKey("k1")
        .setValue("v1")
        .build();
    DstServerProtocol.StringPutResponse stringResponse = stringService.strPut(stringPutRequest);
    Assertions.assertEquals("ok", stringResponse.getResult());

    // Test string get request
    DstServerProtocol.StringGetRequest strGetRequest = DstServerProtocol.StringGetRequest
        .newBuilder()
        .setKey("k1")
        .build();
    DstServerProtocol.StringGetResponse stringGetRequest = stringService.strGet(strGetRequest);
    Assertions.assertEquals("v1", stringGetRequest.getResult());
    client.stop();

    TestUtil.stopRpcServer();
  }

  @Test
  void testListRpcCall() {
    TestUtil.startRpcServer();

    // TODO(qwang): Remove this duplicated code.
    RpcClientOptions options = new RpcClientOptions();
    options.setProtocolType(Options.ProtocolType.PROTOCOL_BAIDU_STD_VALUE);
    options.setWriteTimeoutMillis(1000);
    options.setReadTimeoutMillis(1000);
    options.setMaxTotalConnections(1000);
    options.setMinIdleConnections(10);
    final String url = "list://127.0.0.1:8082";

    RpcClient client = new RpcClient(url, options);
    DstStringService listService = BrpcProxy.getProxy(client, DstStringService.class);

    // Test list put.
    DstServerProtocol.ListPutRequest.Builder putRequestbuilder =
            DstServerProtocol.ListPutRequest.newBuilder();
    putRequestbuilder.setKey("k1");
    final List<String> values = ImmutableList.of("v1", "v2", "v3");
    for (int i = 0; i < values.size(); ++i) {
      putRequestbuilder.setValues(i, values.get(i));
    }

    DstServerProtocol.ListPutResponse listPutresponse =
            listService.listPut(putRequestbuilder.build());
    Assert.assertEquals("ok", listPutresponse.getResult());

    // Test list get.
    DstServerProtocol.ListGetRequest.Builder getRequestBuilder =
            DstServerProtocol.ListGetRequest.newBuilder();
    getRequestBuilder.setKey("k1");
    DstServerProtocol.ListGetResponse listGetResponse =
            listService.listGet(getRequestBuilder.build());

    Assert.assertEquals("ok", listGetResponse.getResult());
    Assert.assertEquals(values, listGetResponse.getValuesList());
    client.stop();
    TestUtil.stopRpcServer();
  }
}
