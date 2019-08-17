package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.ListProtocol;

public interface DstListService {

  @BrpcMeta(serviceName = "DstListService", methodName = "listPut")
  ListProtocol.ListPutResponse listPut(ListProtocol.ListPutRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "listGet")
  ListProtocol.ListGetResponse listGet(ListProtocol.ListGetRequest request);
}
