package com.distkv.asyncclient;

import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.SlistProtocol;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

public class DistkvAsyncSortedListProxy extends DistkvAbstractAsyncProxy {

  public DistkvAsyncSortedListProxy(DistkvAsyncClient client, DistkvService service) {
    super(client, service);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> put(
      String key, LinkedList<SortedListEntity> list) {

    LinkedList<SlistProtocol.SortedListEntity> listEntities = new LinkedList<>();
    list.forEach((v) -> {
      SlistProtocol.SortedListEntity.Builder sortedListEntity =
          SlistProtocol.SortedListEntity.newBuilder();
      sortedListEntity.setMember(v.getMember());
      sortedListEntity.setScore(v.getScore());
      listEntities.add(sortedListEntity.build());
    });

    SlistProtocol.SlistPutRequest slistPutRequest =
        SlistProtocol.SlistPutRequest.newBuilder().addAllList(listEntities).build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_PUT)
        .setRequest(Any.pack(slistPutRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> incrScore(
      String key, String member, int delta) {
    SlistProtocol.SlistIncrScoreRequest slistInceScoreRequest =
        SlistProtocol.SlistIncrScoreRequest.newBuilder()
            .setDelta(delta)
            .setMember(member)
            .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_INCR_SCORE)
        .setRequest(Any.pack(slistInceScoreRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> top(String key, int topNum) {

    SlistProtocol.SlistTopRequest slistTopRequest =
        SlistProtocol.SlistTopRequest.newBuilder()
            .setCount(topNum)
            .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_TOP)
        .setRequest(Any.pack(slistTopRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> removeMember(String key, String member) {
    SlistProtocol.SlistRemoveMemberRequest slistRemoveMemberRequest =
        SlistProtocol.SlistRemoveMemberRequest.newBuilder()
            .setMember(member)
            .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_REMOVE_MEMBER)
        .setRequest(Any.pack(slistRemoveMemberRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> putMember(
      String key, SortedListEntity entity) {
    SlistProtocol.SlistPutMemberRequest slistPutMemberRequest =
        SlistProtocol.SlistPutMemberRequest.newBuilder()
            .setMember(entity.getMember())
            .setScore(entity.getScore())
            .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_PUT_MEMBER)
        .setRequest(Any.pack(slistPutMemberRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> getMember(
      String key, String member) {
    SlistProtocol.SlistGetMemberRequest slistGetMemberRequest =
        SlistProtocol.SlistGetMemberRequest.newBuilder()
            .setMember(member)
            .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_GET_MEMBER)
        .setRequest(Any.pack(slistGetMemberRequest))
        .build();
    return call(request);
  }

}
