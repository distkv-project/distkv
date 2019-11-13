package org.dst.rpc.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.SetProtocol;

public interface DstSetService {
  @BrpcMeta(serviceName = "DstSetService", methodName = "put")
  SetProtocol.PutResponse put(SetProtocol.PutRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "get")
  SetProtocol.GetResponse get(SetProtocol.GetRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "remove")
  SetProtocol.RemoveResponse remove(SetProtocol.RemoveRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "drop")
  CommonProtocol.DropResponse drop(CommonProtocol.DropRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "exists")
  SetProtocol.ExistsResponse exists(SetProtocol.ExistsRequest request);

}
