package com.distkv.asyncclient;

import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.service.DistKVSortedListService;

import com.distkv.rpc.service.DistkvService;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

public class DstAsyncSortedListProxy {

  private DistkvService service;

  public DstAsyncSortedListProxy(DistkvService service) {
    this.service = service;
  }

  public CompletableFuture<SortedListProtocol.PutResponse> put(
          String key, LinkedList<SortedListEntity> list) {
    SortedListProtocol.PutRequest.Builder requestBuilder =
            SortedListProtocol.PutRequest.newBuilder();
    requestBuilder.setKey(key);
    LinkedList<SortedListProtocol.SortedListEntity> listEntities =
            new LinkedList<>();
    for (SortedListEntity entity : list) {
      SortedListProtocol.SortedListEntity.Builder builder =
              SortedListProtocol.SortedListEntity.newBuilder();
      builder.setMember(entity.getMember());
      builder.setScore(entity.getScore());
      listEntities.add(builder.build());
    }
    requestBuilder.addAllList(listEntities);
    CompletableFuture<SortedListProtocol.PutResponse> future = service.put(requestBuilder.build());
    return future;
  }

  public CompletableFuture<SortedListProtocol.IncrScoreResponse> incrScore(
          String key, String member, int delta) {
    SortedListProtocol.IncrScoreRequest.Builder requestBuilder =
            SortedListProtocol.IncrScoreRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(member);
    requestBuilder.setDelta(delta);
    CompletableFuture<SortedListProtocol.IncrScoreResponse> future =
            service.incrScore(requestBuilder.build());
    return future;
  }

  public CompletableFuture<SortedListProtocol.TopResponse> top(
          String key, int topNum) {
    SortedListProtocol.TopRequest.Builder topRequestBuilder =
            SortedListProtocol.TopRequest.newBuilder();
    topRequestBuilder.setKey(key);
    topRequestBuilder.setCount(topNum);
    CompletableFuture<SortedListProtocol.TopResponse> future =
            service.top(topRequestBuilder.build());
    return future;
  }

  public CompletableFuture<CommonProtocol.DropResponse> drop(String key) {
    CommonProtocol.DropRequest.Builder requestBuilder =
            CommonProtocol.DropRequest.newBuilder();
    requestBuilder.setKey(key);
    CompletableFuture<CommonProtocol.DropResponse> future =
            service.drop(requestBuilder.build());
    return future;
  }

  public CompletableFuture<SortedListProtocol.RemoveMemberResponse> removeMember(
          String key, String member) {
    SortedListProtocol.RemoveMemberRequest.Builder requestBuilder =
            SortedListProtocol.RemoveMemberRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(member);
    CompletableFuture<SortedListProtocol.RemoveMemberResponse> future =
            service.removeMember(requestBuilder.build());
    return future;
  }

  public CompletableFuture<SortedListProtocol.PutMemberResponse> putMember(
          String key, SortedListEntity entity) {
    SortedListProtocol.PutMemberRequest.Builder requestBuilder =
            SortedListProtocol.PutMemberRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(entity.getMember());
    requestBuilder.setScore(entity.getScore());
    CompletableFuture<SortedListProtocol.PutMemberResponse> future =
            service.putMember(requestBuilder.build());
    return future;
  }

  public CompletableFuture<SortedListProtocol.GetMemberResponse> getMember(
          String key, String member) {
    SortedListProtocol.GetMemberRequest.Builder requestBuilder =
            SortedListProtocol.GetMemberRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(member);
    CompletableFuture<SortedListProtocol.GetMemberResponse> future =
            service.getMember(requestBuilder.build());
    return future;
  }
}
