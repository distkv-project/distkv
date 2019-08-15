package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.DstServerProtocol;

public interface DstSetService {
  @BrpcMeta(serviceName = "DstService", methodName = "setPut")
  DstServerProtocol.SetPutResponse setPut(DstServerProtocol.SetPutRequest request);

  @BrpcMeta(serviceName = "DstService", methodName = "setGet")
  DstServerProtocol.SetGetResponse setGet(DstServerProtocol.SetGetRequest request);
}
