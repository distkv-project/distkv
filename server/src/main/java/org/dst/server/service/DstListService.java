package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.ListProtocol;

public interface DstListService {

  @BrpcMeta(serviceName = "DstListService", methodName = "put")
  ListProtocol.PutResponse listPut(ListProtocol.PutRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "get")
  ListProtocol.GetResponse listGet(ListProtocol.GetRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "del")
  ListProtocol.DelResponse listDel(ListProtocol.DelRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "lPut")
  ListProtocol.LPutResponse listLPut(ListProtocol.LPutRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "rPut")
  ListProtocol.RPutResponse listRPut(ListProtocol.RPutRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "lDel")
  ListProtocol.LDelResponse listLDel(ListProtocol.LDelRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "rDel")
  ListProtocol.RDelResponse listRDel(ListProtocol.RDelRequest request);



}
