package org.dst.rpc.protocol.example.pb;

import org.dst.rpc.protobuf.generated.CommonProtocol.Status;
import org.dst.rpc.protobuf.generated.StringProtocol.GetRequest;
import org.dst.rpc.protobuf.generated.StringProtocol.GetResponse;
import org.dst.rpc.transport.api.async.DefaultResponse;
import org.dst.rpc.transport.api.async.Response;

/**
 * @author zrj CreateDate: 2019/10/28
 */
public class IServerImpl implements IServer {

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

  private void sleep(long t) {
    try {
      Thread.sleep(t);
    } catch (Exception e) {
      // ignore
    }
  }
}
