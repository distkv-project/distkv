package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.protocol.ListProtocol;

public interface DstListService {

  @BrpcMeta(serviceName = "DstListService", methodName = "listPut")
  ListProtocol.ListPutResponse listPut(ListProtocol.ListPutRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "listGet")
  ListProtocol.ListGetResponse listGet(ListProtocol.ListGetRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "listDel")
  ListProtocol.ListDelResponse listDel(ListProtocol.ListDelRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "listLPut")
  ListProtocol.ListLPutResponse listLPut(ListProtocol.ListLPutRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "listRPut")
  ListProtocol.ListRPutResponse listRPut(ListProtocol.ListRPutRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "listLDel")
  ListProtocol.ListLDelResponse listLDel(ListProtocol.ListLDelRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "listRDel")
  ListProtocol.ListRDelResponse listRDel(ListProtocol.ListRDelRequest request);



}
