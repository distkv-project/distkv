package com.distkv.client;

import com.distkv.common.DistKVTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.service.DistKVSortedListService;

import java.util.LinkedList;

public class DstSortedListProxy {

  private String typeCode = "E";

  private DistKVSortedListService service;

  public DstSortedListProxy(DistKVSortedListService service) {
    this.service = service;
  }

  public void put(String key, LinkedList<SortedListEntity> list) {
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
    SortedListProtocol.PutResponse response = FutureUtils.get(
        service.put(requestBuilder.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void incrScore(String key, String member, int delta) {
    SortedListProtocol.IncrScoreRequest.Builder requestBuilder =
        SortedListProtocol.IncrScoreRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(member);
    requestBuilder.setDelta(delta);
    SortedListProtocol.IncrScoreResponse response = FutureUtils.get(
        service.incrScore(requestBuilder.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public LinkedList<SortedListEntity> top(String key, int topNum) {
    SortedListProtocol.TopRequest.Builder topRequestBuilder =
        SortedListProtocol.TopRequest.newBuilder();
    topRequestBuilder.setKey(key);
    topRequestBuilder.setCount(topNum);
    SortedListProtocol.TopResponse response = FutureUtils.get(
        service.top(topRequestBuilder.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    LinkedList<SortedListEntity> list = new LinkedList<>();
    for (SortedListProtocol.SortedListEntity entity : response.getListList()) {
      list.add(new SortedListEntity(entity.getMember(), entity.getScore()));
    }
    return list;
  }

  public void drop(String key) {
    CommonProtocol.DropRequest.Builder requestBuilder =
        CommonProtocol.DropRequest.newBuilder();
    requestBuilder.setKey(key);
    CommonProtocol.DropResponse response = FutureUtils.get(
        service.drop(requestBuilder.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void removeMember(String key, String member) {
    SortedListProtocol.RemoveMemberRequest.Builder requestBuilder =
        SortedListProtocol.RemoveMemberRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(member);
    SortedListProtocol.RemoveMemberResponse response = FutureUtils.get(
        service.removeMember(requestBuilder.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void putMember(String key, SortedListEntity entity) {
    SortedListProtocol.PutMemberRequest.Builder requestBuilder =
        SortedListProtocol.PutMemberRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(entity.getMember());
    requestBuilder.setScore(entity.getScore());
    SortedListProtocol.PutMemberResponse response = FutureUtils.get(
        service.putMember(requestBuilder.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public DistKVTuple<Integer, Integer> getMember(String key, String member) {
    SortedListProtocol.GetMemberRequest.Builder getMemberRequest =
        SortedListProtocol.GetMemberRequest.newBuilder();
    getMemberRequest.setKey(key);
    getMemberRequest.setMember(member);
    SortedListProtocol.GetMemberResponse response = FutureUtils.get(
        service.getMember(getMemberRequest.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    SortedListProtocol.SortedListEntity sortedListEntity = response.getEntity();
    return new DistKVTuple<>(sortedListEntity.getScore(), response.getCount());
  }
}
