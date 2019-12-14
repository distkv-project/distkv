package com.distkv.dst.rpc.service;

import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SortedListProtocol;
import java.util.concurrent.CompletableFuture;


public interface DstSortedListService {

  CompletableFuture<SortedListProtocol.PutResponse> put(
      SortedListProtocol.PutRequest request);

  CompletableFuture<SortedListProtocol.TopResponse> top(
      SortedListProtocol.TopRequest request);

  CompletableFuture<CommonProtocol.DropResponse> drop(
      CommonProtocol.DropRequest request);

  CompletableFuture<SortedListProtocol.IncrScoreResponse> incrItem(
      SortedListProtocol.IncrScoreRequest request);

  CompletableFuture<SortedListProtocol.PutMemberResponse> putItem(
      SortedListProtocol.PutMemberRequest request);

  CompletableFuture<SortedListProtocol.RemoveMemberResponse> removeItem(
      SortedListProtocol.RemoveMemberRequest request);

  CompletableFuture<SortedListProtocol.GetMemberResponse> getItem(
          SortedListProtocol.GetMemberRequest request);
}
