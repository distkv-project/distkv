package test.org.dst.rpc;

import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.baidu.brpc.client.RpcClientOptions;
import com.baidu.brpc.protocol.Options;
import org.dst.server.generated.EchoExample;
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

    String url = "list://localhost:8082";

    RpcClient client = new RpcClient(url, options);

    //Construct a request
    EchoExample.EchoRequest request = EchoExample.EchoRequest
            .newBuilder().setMessage("Hefei University of Technology!").build();

    EchoService echoService = BrpcProxy.getProxy(client, EchoService.class);

    EchoExample.EchoResponse response = echoService.echo(request);

    Assertions.assertEquals("Hefei University of Technology!",response.getMessage());

    client.stop();

    TestUtil.stopRpcServer();

  }
}
