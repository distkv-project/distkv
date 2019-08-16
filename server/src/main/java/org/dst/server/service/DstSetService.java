package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.SetProtocol;

public interface DstSetService {
  @BrpcMeta(serviceName = "DstSetService", methodName = "setPut")
  SetProtocol.SetPutResponse setPut(SetProtocol.SetPutRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "setGet")
  SetProtocol.SetGetResponse setGet(SetProtocol.SetGetRequest request);
}
