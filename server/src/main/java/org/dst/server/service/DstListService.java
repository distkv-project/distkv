package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.ListProtocol;

public interface DstListService {

  @BrpcMeta(serviceName = "DstListService", methodName = "put")
  ListProtocol.PutResponse put(ListProtocol.PutRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "get")
  ListProtocol.GetResponse get(ListProtocol.GetRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "del")
  ListProtocol.DelResponse del(ListProtocol.DelRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "lput")
  ListProtocol.LPutResponse lput(ListProtocol.LPutRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "rput")
  ListProtocol.RPutResponse rput(ListProtocol.RPutRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "ldel")
  ListProtocol.LDelResponse ldel(ListProtocol.LDelRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "rdel")
  ListProtocol.RDelResponse rdel(ListProtocol.RDelRequest request);

}
