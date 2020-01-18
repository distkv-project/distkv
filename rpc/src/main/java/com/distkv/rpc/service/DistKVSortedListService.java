package com.distkv.rpc.service;

import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import java.util.concurrent.CompletableFuture;


public interface DistKVSortedListService {

  CompletableFuture<SortedListProtocol.PutResponse> put(
      SortedListProtocol.PutRequest request);

  CompletableFuture<SortedListProtocol.TopResponse> top(
      SortedListProtocol.TopRequest request);

  CompletableFuture<CommonProtocol.DropResponse> drop(
      CommonProtocol.DropRequest request);

  CompletableFuture<SortedListProtocol.IncrScoreResponse> incrScore(
      SortedListProtocol.IncrScoreRequest request);

  CompletableFuture<SortedListProtocol.PutMemberResponse> putMember(
      SortedListProtocol.PutMemberRequest request);

  CompletableFuture<SortedListProtocol.RemoveMemberResponse> removeMember(
      SortedListProtocol.RemoveMemberRequest request);

  CompletableFuture<SortedListProtocol.GetMemberResponse> getMember(
          SortedListProtocol.GetMemberRequest request);
}
