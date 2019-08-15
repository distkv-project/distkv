package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.DstServerSetProtocol;

public interface DstSetService {
  @BrpcMeta(serviceName = "DstSetService", methodName = "setPut")
  DstServerSetProtocol.SetPutResponse setPut(DstServerSetProtocol.SetPutRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "setGet")
  DstServerSetProtocol.SetGetResponse setGet(DstServerSetProtocol.SetGetRequest request);
}
