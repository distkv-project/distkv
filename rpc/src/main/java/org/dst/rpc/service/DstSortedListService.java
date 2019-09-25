package org.dst.rpc.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.rpc.protobuf.generated.SortedListProtocol;


public interface DstSortedListService {
  @BrpcMeta(serviceName = "DstSortedListService", methodName = "put")
  SortedListProtocol.PutResponse put(SortedListProtocol.PutRequest request);

  @BrpcMeta(serviceName = "DstSortedListService", methodName = "top")
  SortedListProtocol.TopResponse top(SortedListProtocol.TopRequest request);

  @BrpcMeta(serviceName = "DstSortedListService", methodName = "del")
  SortedListProtocol.DelResponse del(SortedListProtocol.DelRequest request);

  @BrpcMeta(serviceName = "DstSortedListService", methodName = "incItem")
  SortedListProtocol.IncItemResponse incItem(SortedListProtocol.IncItemRequest request);

  @BrpcMeta(serviceName = "DstSortedListService", methodName = "putItem")
  SortedListProtocol.PutItemResponse putItem(SortedListProtocol.PutItemRequest request);

  @BrpcMeta(serviceName = "DstSortedListService", methodName = "delItem")
  SortedListProtocol.DelItemResponse delItem(SortedListProtocol.DelItemRequest request);
}
