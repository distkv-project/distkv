package com.distkv.client;

import com.distkv.common.DistKVTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistGetMemberResponse;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistTopResponse;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

public class DistkvSortedListProxy {

  private String typeCode = "E";

  private DistkvService service;

  public DistkvSortedListProxy(DistkvService service) {
    this.service = service;
  }

  public void put(DistkvRequest request) {
    CompletableFuture<DistkvResponse> responseFuture = service.call(request);
    DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void incrScore(DistkvRequest request) {
    CompletableFuture<DistkvResponse> responseFuture = service.call(request);
    DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public LinkedList<SortedListEntity> top(DistkvRequest request) {
    CompletableFuture<DistkvResponse> responseFuture = service.call(request);
    DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    LinkedList<SortedListEntity> list = new LinkedList<>();
    SlistTopResponse slistTopResponse;
    try {
      slistTopResponse = response.getResponse().unpack(SlistTopResponse.class);
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    for (SortedListProtocol.SortedListEntity entity : slistTopResponse.getListList()) {
      list.add(new SortedListEntity(entity.getMember(), entity.getScore()));
    }
    return list;
  }

  public void drop(DistkvRequest request) {
    CompletableFuture<DistkvResponse> responseFuture = service.call(request);
    DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void removeMember(DistkvRequest request) {
    CompletableFuture<DistkvResponse> responseFuture = service.call(request);
    DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void putMember(DistkvRequest request) {
    CompletableFuture<DistkvResponse> responseFuture = service.call(request);
    DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public DistKVTuple<Integer, Integer> getMember(DistkvRequest request) {
    CompletableFuture<DistkvResponse> responseFuture = service.call(request);
    DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    SlistGetMemberResponse slistGetMemberResponse;
    try {
      slistGetMemberResponse = response.getResponse()
          .unpack(SlistGetMemberResponse.class);
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    SortedListProtocol.SortedListEntity sortedListEntity = slistGetMemberResponse.getEntity();
    return new DistKVTuple<>(sortedListEntity.getScore(), slistGetMemberResponse.getCount());
  }
}
