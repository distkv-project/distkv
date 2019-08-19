package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.SetProtocol;

public interface DstSetService {
  @BrpcMeta(serviceName = "DstSetService", methodName = "put")
  SetProtocol.PutResponse put(SetProtocol.PutRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "get")
  SetProtocol.GetResponse get(SetProtocol.GetRequest request);
}
