package com.distkv.dst.asyncclient;

import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.dst.rpc.service.DstSortedListService;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

public class DstSortedListProxy {

  DstSortedListService service;

  public DstSortedListProxy(DstSortedListService service) { this.service = service; }

  public CompletableFuture<SortedListProtocol.PutResponse> asyncPut(
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

  public CompletableFuture<SortedListProtocol.IncrScoreResponse> asyncIncrItem(
          String key, String member, int delta) {
    SortedListProtocol.IncrScoreRequest.Builder requestBuilder =
            SortedListProtocol.IncrScoreRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(member);
    requestBuilder.setDelta(delta);
    CompletableFuture<SortedListProtocol.IncrScoreResponse> future =
            service.incrItem(requestBuilder.build());
    return future;
  }

  public CompletableFuture<SortedListProtocol.TopResponse> asyncTop(
          String key, int topNum) {
    SortedListProtocol.TopRequest.Builder topRequestBuilder =
            SortedListProtocol.TopRequest.newBuilder();
    topRequestBuilder.setKey(key);
    topRequestBuilder.setCount(topNum);
    CompletableFuture<SortedListProtocol.TopResponse> future =
            service.top(topRequestBuilder.build());
    return future;
  }

  public CompletableFuture<CommonProtocol.DropResponse> asyncDrop(String key) {
    CommonProtocol.DropRequest.Builder requestBuilder =
            CommonProtocol.DropRequest.newBuilder();
    requestBuilder.setKey(key);
    CompletableFuture<CommonProtocol.DropResponse> future =
            service.drop(requestBuilder.build());
    return future;
  }

  public CompletableFuture<SortedListProtocol.DelMemberResponse> asyncDelItem(
          String key, String member) {
    SortedListProtocol.DelMemberRequest.Builder requestBuilder =
            SortedListProtocol.DelMemberRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(member);
    CompletableFuture<SortedListProtocol.DelMemberResponse> future =
            service.delItem(requestBuilder.build());
    return future;
  }

  public CompletableFuture<SortedListProtocol.PutMemberResponse> asyncPutItem(
          String key, SortedListEntity entity) {
    SortedListProtocol.PutMemberRequest.Builder requestBuilder =
            SortedListProtocol.PutMemberRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(entity.getMember());
    requestBuilder.setScore(entity.getScore());
    CompletableFuture<SortedListProtocol.PutMemberResponse> future =
            service.putItem(requestBuilder.build());
    return future;
  }
}
