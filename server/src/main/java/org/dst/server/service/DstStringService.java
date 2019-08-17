package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.StringProtocol;

public interface DstStringService {

  @BrpcMeta(serviceName = "DstStringService", methodName = "strPut")
  StringProtocol.StringPutResponse strPut(StringProtocol.StringPutRequest request);

  @BrpcMeta(serviceName = "DstStringService", methodName = "strGet")
  StringProtocol.StringGetResponse strGet(StringProtocol.StringGetRequest request);

}
