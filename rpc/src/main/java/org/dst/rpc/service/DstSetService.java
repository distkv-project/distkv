package org.dst.rpc.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.rpc.protobuf.generated.SetProtocol;

public interface DstSetService {
  @BrpcMeta(serviceName = "DstSetService", methodName = "put")
  SetProtocol.PutResponse put(SetProtocol.PutRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "get")
  SetProtocol.GetResponse get(SetProtocol.GetRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "delete")
  SetProtocol.DeleteResponse delete(SetProtocol.DeleteRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "dropByKey")
  SetProtocol.DropByKeyResponse dropByKey(SetProtocol.DropByKeyRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "exists")
  SetProtocol.ExistsResponse exists(SetProtocol.ExistsRequest request);

}
