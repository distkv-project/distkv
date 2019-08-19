package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.StringProtocol;

public interface DstStringService {

  @BrpcMeta(serviceName = "DstStringService", methodName = "strPut")
  StringProtocol.PutResponse strPut(StringProtocol.PutRequest request);

  @BrpcMeta(serviceName = "DstStringService", methodName = "strGet")
  StringProtocol.GetResponse strGet(StringProtocol.GetRequest request);

}
