package com.distkv.client;

import com.distkv.common.DistkvTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistGetMemberResponse;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistPutRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistTopResponse;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

public class DistkvSortedListProxy {

  private String typeCode = "E";

  private DistkvService service;

  public DistkvSortedListProxy(DistkvService service) {
    this.service = service;
  }

  public void put(String key, LinkedList<SortedListEntity> list) {
    LinkedList<SortedListProtocol.SortedListEntity> listEntities = new LinkedList<>();
    list.forEach((v) -> {
      SortedListProtocol.SortedListEntity.Builder builder =
          SortedListProtocol.SortedListEntity.newBuilder();
      builder.setMember(v.getMember());
      builder.setScore(v.getScore());
      listEntities.add(builder.build());
    });
    SlistPutRequest slistPutRequest = SlistPutRequest
        .newBuilder()
        .addAllList(listEntities)
        .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequest(Any.pack(slistPutRequest))
        .setRequestType(RequestType.SORTED_LIST_PUT)
        .build();
    CompletableFuture<DistkvResponse> responseFuture = service.call(request);
    DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void incrScore(String key, String member, int delta) {
    SortedListProtocol.SlistIncrScoreRequest slistIncrScoreRequest =
        SortedListProtocol.SlistIncrScoreRequest.newBuilder()
            .setDelta(delta)
            .setMember(member)
            .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequest(Any.pack(slistIncrScoreRequest))
        .setRequestType(RequestType.SORTED_LIST_INCR_SCORE)
        .build();
    CompletableFuture<DistkvResponse> responseFuture = service.call(request);
    DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public LinkedList<SortedListEntity> top(String key, int topNum)
      throws InvalidProtocolBufferException {
    SortedListProtocol.SlistTopRequest slistTopRequest = SortedListProtocol.SlistTopRequest
        .newBuilder()
        .setCount(topNum)
        .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequest(Any.pack(slistTopRequest))
        .setRequestType(RequestType.SORTED_LIST_TOP)
        .build();
    CompletableFuture<DistkvResponse> responseFuture = service.call(request);
    DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    LinkedList<SortedListEntity> list = new LinkedList<>();
    SlistTopResponse slistTopResponse = response.getResponse().unpack(SlistTopResponse.class);
    for (SortedListProtocol.SortedListEntity entity : slistTopResponse.getListList()) {
      list.add(new SortedListEntity(entity.getMember(), entity.getScore()));
    }
    return list;
  }

  public void drop(String key) {
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_DROP)
        .build();
    CompletableFuture<DistkvResponse> responseFuture = service.call(request);
    DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void removeMember(String key, String member) {
    SortedListProtocol.SlistRemoveMemberRequest slistRemoveMemberRequest =
        SortedListProtocol.SlistRemoveMemberRequest.newBuilder().setMember(member).build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_REMOVE_MEMBER)
        .setRequest(Any.pack(slistRemoveMemberRequest))
        .build();
    CompletableFuture<DistkvResponse> responseFuture = service.call(request);
    DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void putMember(String key, SortedListEntity entity) {
    SortedListProtocol.SlistPutMemberRequest slistPutMemberRequest =
        SortedListProtocol.SlistPutMemberRequest.newBuilder()
            .setScore(entity.getScore())
            .setMember(entity.getMember())
            .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_PUT_MEMBER)
        .setRequest(Any.pack(slistPutMemberRequest))
        .build();
    CompletableFuture<DistkvResponse> responseFuture = service.call(request);
    DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public DistkvTuple<Integer, Integer> getMember(String key, String member)
      throws InvalidProtocolBufferException {
    SortedListProtocol.SlistGetMemberRequest slistGetMemberRequest =
        SortedListProtocol.SlistGetMemberRequest.newBuilder()
            .setMember(member)
            .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_GET_MEMBER)
        .setRequest(Any.pack(slistGetMemberRequest))
        .build();
    CompletableFuture<DistkvResponse> responseFuture = service.call(request);
    DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    SlistGetMemberResponse slistGetMemberResponse = response.getResponse()
        .unpack(SlistGetMemberResponse.class);
    SortedListProtocol.SortedListEntity sortedListEntity = slistGetMemberResponse.getEntity();
    return new DistkvTuple<>(sortedListEntity.getScore(), slistGetMemberResponse.getCount());
  }
}
