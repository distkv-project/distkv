package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.SetProtocol;

public interface DstSetService {
  @BrpcMeta(serviceName = "DstSetService", methodName = "setPut")
  SetProtocol.SetPutResponse put(SetProtocol.SetPutRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "setGet")
  SetProtocol.SetGetResponse get(SetProtocol.SetGetRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "setDelete")
  SetProtocol.SetDeleteResponse delete(SetProtocol.SetDeleteRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "setDropByKey")
  SetProtocol.SetDropByKeyResponse dropByKey(SetProtocol.SetDropByKeyRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "setExist")
  SetProtocol.SetExistResponse exist(SetProtocol.SetExistRequest request);
}
