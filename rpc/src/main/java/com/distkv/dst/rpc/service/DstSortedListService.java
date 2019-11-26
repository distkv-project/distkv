package com.distkv.dst.rpc.service;

import com.baidu.brpc.protocol.BrpcMeta;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SortedListProtocol;


public interface DstSortedListService {
  @BrpcMeta(serviceName = "DstSortedListService", methodName = "put")
  SortedListProtocol.PutResponse put(SortedListProtocol.PutRequest request);

  @BrpcMeta(serviceName = "DstSortedListService", methodName = "top")
  SortedListProtocol.TopResponse top(SortedListProtocol.TopRequest request);

  @BrpcMeta(serviceName = "DstSortedListService", methodName = "drop")
  CommonProtocol.DropResponse drop(CommonProtocol.DropRequest request);

  @BrpcMeta(serviceName = "DstSortedListService", methodName = "incrItem")
  SortedListProtocol.IncrScoreResponse incrItem(SortedListProtocol.IncrScoreRequest request);

  @BrpcMeta(serviceName = "DstSortedListService", methodName = "putItem")
  SortedListProtocol.PutMemberResponse putItem(SortedListProtocol.PutMemberRequest request);

  @BrpcMeta(serviceName = "DstSortedListService", methodName = "delItem")
  SortedListProtocol.DelMemberResponse delItem(SortedListProtocol.DelMemberRequest request);
}
