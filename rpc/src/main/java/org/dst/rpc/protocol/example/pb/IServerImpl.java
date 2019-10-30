package org.dst.rpc.protocol.example.pb;

import org.dst.rpc.protobuf.generated.CommonProtocol.Status;
import org.dst.rpc.protocol.example.pb.StringProtocol.GetRequest;
import org.dst.rpc.protocol.example.pb.StringProtocol.GetResponse;

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
}
