package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.common.protobuf.generated.StringProtocol;

public interface DstStringService {

  @BrpcMeta(serviceName = "DstStringService", methodName = "put")
  StringProtocol.PutResponse put(StringProtocol.PutRequest request);

  @BrpcMeta(serviceName = "DstStringService", methodName = "get")
  StringProtocol.GetResponse get(StringProtocol.GetRequest request);

}
