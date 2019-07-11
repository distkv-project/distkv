package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;

public interface EchoService {

  @BrpcMeta(serviceName = "EchoService", methodName = "0")
  EchoExample.EchoResponse echo(EchoExample.EchoRequest request);
}
