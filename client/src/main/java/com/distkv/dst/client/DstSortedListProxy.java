package com.distkv.dst.client;

import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import com.distkv.dst.common.utils.FutureUtils;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.dst.rpc.service.DstSortedListService;
import java.util.LinkedList;

public class DstSortedListProxy {
  DstSortedListService service;

  public DstSortedListProxy(DstSortedListService service) {
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
    CheckStatusUtil.checkStatus(response.getStatus(), key);
  }

  public void incrItem(String key, String member, int delta) {
    SortedListProtocol.IncrScoreRequest.Builder requestBuilder =
        SortedListProtocol.IncrScoreRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(member);
    requestBuilder.setDelta(delta);
    SortedListProtocol.IncrScoreResponse response = FutureUtils.get(
        service.incrItem(requestBuilder.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), key);
  }

  public LinkedList<SortedListEntity> top(String key, int topNum) {
    SortedListProtocol.TopRequest.Builder topRequestBuilder =
        SortedListProtocol.TopRequest.newBuilder();
    topRequestBuilder.setKey(key);
    topRequestBuilder.setCount(topNum);
    SortedListProtocol.TopResponse response = FutureUtils.get(
        service.top(topRequestBuilder.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), key);
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
    CheckStatusUtil.checkStatus(response.getStatus(), key);
  }

  public void delItem(String key, String member) {
    SortedListProtocol.DelMemberRequest.Builder requestBuilder =
        SortedListProtocol.DelMemberRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(member);
    SortedListProtocol.DelMemberResponse response = FutureUtils.get(
        service.delItem(requestBuilder.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), key);
  }

  public void putItem(String key, SortedListEntity entity) {
    SortedListProtocol.PutMemberRequest.Builder requestBuilder =
        SortedListProtocol.PutMemberRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(entity.getMember());
    requestBuilder.setScore(entity.getScore());
    SortedListProtocol.PutMemberResponse response = FutureUtils.get(
        service.putItem(requestBuilder.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), key);
  }
}
