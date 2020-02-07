package com.distkv.asyncclient;

import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

public class DistkvAsyncSortedListProxy {

  private DistkvService service;

  public DistkvAsyncSortedListProxy(DistkvService service) {
    this.service = service;
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> put(
      String key, LinkedList<SortedListEntity> list) {

    LinkedList<SortedListProtocol.SortedListEntity> listEntities = new LinkedList<>();
    list.forEach((v) -> {
      SortedListProtocol.SortedListEntity.Builder sortedListEntity =
          SortedListProtocol.SortedListEntity.newBuilder();
      sortedListEntity.setMember(v.getMember());
      sortedListEntity.setScore(v.getScore());
      listEntities.add(sortedListEntity.build());
    });

    SortedListProtocol.SlistPutRequest slistPutRequest =
        SortedListProtocol.SlistPutRequest.newBuilder().addAllList(listEntities).build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_PUT)
        .setRequest(Any.pack(slistPutRequest))
        .build();
    return service.call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> incrScore(
      String key, String member, int delta) {
    SortedListProtocol.SlistIncrScoreRequest slistInceScoreRequest =
        SortedListProtocol.SlistIncrScoreRequest.newBuilder()
            .setDelta(delta)
            .setMember(member)
            .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_INCR_SCORE)
        .setRequest(Any.pack(slistInceScoreRequest))
        .build();
    return service.call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> top(String key, int topNum) {

    SortedListProtocol.SlistTopRequest slistTopRequest =
        SortedListProtocol.SlistTopRequest.newBuilder()
            .setCount(topNum)
            .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_TOP)
        .setRequest(Any.pack(slistTopRequest))
        .build();
    return service.call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> drop(String key) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_DROP)
        .build();
    return service.call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> removeMember(String key, String member) {
    SortedListProtocol.SlistRemoveMemberRequest slistRemoveMemberRequest =
        SortedListProtocol.SlistRemoveMemberRequest.newBuilder()
            .setMember(member)
            .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_REMOVE_MEMBER)
        .setRequest(Any.pack(slistRemoveMemberRequest))
        .build();
    return service.call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> putMember(
      String key, SortedListEntity entity) {
    SortedListProtocol.SlistPutMemberRequest slistPutMemberRequest =
        SortedListProtocol.SlistPutMemberRequest.newBuilder()
            .setMember(entity.getMember())
            .setScore(entity.getScore())
            .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_PUT_MEMBER)
        .setRequest(Any.pack(slistPutMemberRequest))
        .build();
    return service.call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> getMember(
      String key, String member) {
    SortedListProtocol.SlistGetMemberRequest slistGetMemberRequest =
        SortedListProtocol.SlistGetMemberRequest.newBuilder()
            .setMember(member)
            .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_GET_MEMBER)
        .setRequest(Any.pack(slistGetMemberRequest))
        .build();
    return service.call(request);
  }
}
