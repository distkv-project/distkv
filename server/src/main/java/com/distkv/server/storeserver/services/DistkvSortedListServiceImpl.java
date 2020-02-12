package com.distkv.server.storeserver.services;

import com.distkv.common.RequestTypeEnum;
import com.distkv.rpc.service.DistkvSortedListService;
import com.distkv.server.storeserver.runtime.StoreRuntime;
import java.util.concurrent.CompletableFuture;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;

public class DistkvSortedListServiceImpl implements DistkvSortedListService {

  public DistkvSortedListServiceImpl(StoreRuntime storeRuntime) {
    this.storeRuntime = storeRuntime;
  }

  private StoreRuntime storeRuntime;

  @Override
  public CompletableFuture<SortedListProtocol.PutResponse> put(
        SortedListProtocol.PutRequest request) {
    CompletableFuture<SortedListProtocol.PutResponse> future = new CompletableFuture<>();
    storeRuntime.getWorkerPool().postRequest(
          request.getKey(), RequestTypeEnum.SLIST_PUT, request, future);
    return future;
  }

  @Override
  public CompletableFuture<SortedListProtocol.TopResponse> top(
        SortedListProtocol.TopRequest request) {
    CompletableFuture<SortedListProtocol.TopResponse> future = new CompletableFuture<>();
    storeRuntime.getWorkerPool().postRequest(
          request.getKey(), RequestTypeEnum.SLIST_TOP, request, future);
    return future;
  }

  @Override
  public CompletableFuture<CommonProtocol.DropResponse> drop(
        CommonProtocol.DropRequest request) {
    CompletableFuture<CommonProtocol.DropResponse> future = new CompletableFuture<>();
    storeRuntime.getWorkerPool().postRequest(
          request.getKey(), RequestTypeEnum.SLIST_DROP, request, future);
    return future;
  }

  @Override
  public CompletableFuture<SortedListProtocol.IncrScoreResponse> incrScore(
        SortedListProtocol.IncrScoreRequest request) {
    CompletableFuture<SortedListProtocol.IncrScoreResponse> future = new CompletableFuture<>();
    storeRuntime.getWorkerPool().postRequest(
          request.getKey(), RequestTypeEnum.SLIST_INCR_SCORE, request, future);
    return future;
  }

  @Override
  public CompletableFuture<SortedListProtocol.PutMemberResponse> putMember(
        SortedListProtocol.PutMemberRequest request) {
    CompletableFuture<SortedListProtocol.PutMemberResponse> future = new CompletableFuture<>();
    storeRuntime.getWorkerPool().postRequest(
          request.getKey(), RequestTypeEnum.SLIST_PUT_MEMBER, request, future);
    return future;
  }

  @Override
  public CompletableFuture<SortedListProtocol.RemoveMemberResponse> removeMember(
        SortedListProtocol.RemoveMemberRequest request) {
    CompletableFuture<SortedListProtocol.RemoveMemberResponse> future = new CompletableFuture<>();
    storeRuntime.getWorkerPool().postRequest(
          request.getKey(), RequestTypeEnum.SLIST_REMOVE_MEMBER, request, future);
    return future;
  }

  @Override
  public CompletableFuture<SortedListProtocol.GetMemberResponse> getMember(
        SortedListProtocol.GetMemberRequest request) {
    CompletableFuture<SortedListProtocol.GetMemberResponse> future = new CompletableFuture<>();
    storeRuntime.getWorkerPool().postRequest(
          request.getKey(), RequestTypeEnum.SLIST_GET_MEMBER, request, future);
    return future;
  }
}
