package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.SetProtocol;

public interface DstSetService {
  @BrpcMeta(serviceName = "DstSetService", methodName = "setPut")
  SetProtocol.putResponse put(SetProtocol.putRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "setGet")
  SetProtocol.getResponse get(SetProtocol.getRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "setDelete")
  SetProtocol.deleteResponse delete(SetProtocol.deleteRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "setDropByKey")
  SetProtocol.dropByKeyResponse dropByKey(SetProtocol.dropByKeyRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "setExists")
  SetProtocol.existsResponse exists(SetProtocol.existsRequest request);
}
