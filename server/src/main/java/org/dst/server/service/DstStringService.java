package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.DstServerProtocol;

public interface DstStringService {

  @BrpcMeta(serviceName = "DstService", methodName = "strPut")
  DstServerProtocol.StringPutResponse strPut(DstServerProtocol.StringPutRequest request);

  @BrpcMeta(serviceName = "DstService", methodName = "strGet")
  DstServerProtocol.StringGetResponse strGet(DstServerProtocol.StringGetRequest request);

}

