package org.dst.rpc.protocol.example.pb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.dst.rpc.protobuf.generated.CommonProtocol.Status;
import org.dst.rpc.protobuf.generated.StringProtocol.GetRequest;
import org.dst.rpc.protobuf.generated.StringProtocol.GetResponse;
import org.dst.rpc.transport.api.Channel;
import org.dst.rpc.transport.api.async.DefaultResponse;
import org.dst.rpc.transport.api.async.Response;
import org.dst.rpc.transport.api.support.RpcContext;

/**
 * @author zrj CreateDate: 2019/10/28
 */
public class IServerImpl implements IServer {

  private ExecutorService executorService = Executors.newFixedThreadPool(8);

  @Override
  public GetResponse say(GetRequest request) {
    GetResponse response = GetResponse.newBuilder()
        .setStatus(Status.OK)
        .setValue(request.getKey() + "'s value from rpc")
        .build();
    return response;
  }

  @Override
  public Response asyncSay(GetRequest request) {
    GetResponse result = GetResponse.newBuilder()
        .setStatus(Status.OK)
        .setValue(request.getKey() + "'s value from rpc async")
        .build();
    Response response = new DefaultResponse();
    response.setValue(result);
    sleep(5000);
    return response;
  }

  @Override
  public Response asyncServerSay(GetRequest request) {
    GetResponse result = GetResponse.newBuilder()
        .setStatus(Status.OK)
        .setValue(request.getKey() + "'s value from rpc async server")
        .build();

    Channel channel = RpcContext.getCurrentContext().getChannel();
    executorService.submit(() -> {
      sleep(5000);
      channel.send(result);
    });
    return null;
  }

  private void sleep(long t) {
    try {
      Thread.sleep(t);
    } catch (Exception e) {
      // ignore
    }
  }
}
