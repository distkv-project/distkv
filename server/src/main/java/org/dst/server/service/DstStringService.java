package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.DstServerProtocol;

public interface DstStringService {

  @BrpcMeta(serviceName = "DstStringService", methodName = "HandleString")
  DstServerProtocol.StringResponse handleString(DstServerProtocol.StringRequest request);
}

