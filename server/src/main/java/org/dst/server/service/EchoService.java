package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.EchoExample;

public interface EchoService {

  @BrpcMeta(serviceName = "EchoService", methodName = "0")
  EchoExample.EchoResponse echo(EchoExample.EchoRequest request);
}
