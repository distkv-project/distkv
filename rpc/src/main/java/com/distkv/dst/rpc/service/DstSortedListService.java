package com.distkv.dst.rpc.service;

import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SortedListProtocol;


public interface DstSortedListService {
  SortedListProtocol.PutResponse put(SortedListProtocol.PutRequest request);

  SortedListProtocol.TopResponse top(SortedListProtocol.TopRequest request);

  CommonProtocol.DropResponse drop(CommonProtocol.DropRequest request);

  SortedListProtocol.IncrScoreResponse incrItem(SortedListProtocol.IncrScoreRequest request);

  SortedListProtocol.PutMemberResponse putItem(SortedListProtocol.PutMemberRequest request);

  SortedListProtocol.DelMemberResponse delItem(SortedListProtocol.DelMemberRequest request);
}
