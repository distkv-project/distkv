package com.distkv.dst.client;

import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import com.distkv.dst.common.utils.FutureUtils;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.dst.rpc.service.DstSortedListService;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
    checkException(response.getStatus(),key);
  }

  public void incrItem(String key, String member, int delta) {
    SortedListProtocol.IncrScoreRequest.Builder requestBuilder =
        SortedListProtocol.IncrScoreRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(member);
    requestBuilder.setDelta(delta);
    SortedListProtocol.IncrScoreResponse response = FutureUtils.get(
        service.incrItem(requestBuilder.build()));
    checkException(response.getStatus(),key);
  }

  public LinkedList<SortedListEntity> top(String key, int topNum) {
    SortedListProtocol.TopRequest.Builder topRequestBuilder =
        SortedListProtocol.TopRequest.newBuilder();
    topRequestBuilder.setKey(key);
    topRequestBuilder.setCount(topNum);
    SortedListProtocol.TopResponse response = FutureUtils.get(
        service.top(topRequestBuilder.build()));
    checkException(response.getStatus(),key);
    LinkedList<SortedListEntity> list = new LinkedList<>();
    for (SortedListProtocol.SortedListEntity entity : response.getListList()) {
      list.add(new SortedListEntity(entity.getMember(), entity.getScore()));
    }
    return list;
  }

  public void del(String key) {
    CommonProtocol.DropRequest.Builder requestBuilder =
        CommonProtocol.DropRequest.newBuilder();
    requestBuilder.setKey(key);
    CommonProtocol.DropResponse response = FutureUtils.get(
        service.drop(requestBuilder.build()));
    checkException(response.getStatus(),key);
  }

  public void removeItem(String key, String member) {
    SortedListProtocol.RemoveMemberRequest.Builder requestBuilder =
        SortedListProtocol.RemoveMemberRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(member);
    SortedListProtocol.RemoveMemberResponse response = FutureUtils.get(
        service.removeItem(requestBuilder.build()));
    checkException(response.getStatus(),key);
  }

  public void putItem(String key, SortedListEntity entity) {
    SortedListProtocol.PutMemberRequest.Builder requestBuilder =
        SortedListProtocol.PutMemberRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setMember(entity.getMember());
    requestBuilder.setScore(entity.getScore());
    SortedListProtocol.PutMemberResponse response = FutureUtils.get(
        service.putItem(requestBuilder.build()));
    checkException(response.getStatus(),key);
  }

  public List<Integer> getItem(String key, String member) {
    SortedListProtocol.GetMemberRequest.Builder getMemberRequest =
            SortedListProtocol.GetMemberRequest.newBuilder();
    getMemberRequest.setKey(key);
    getMemberRequest.setMember(member);
    SortedListProtocol.GetMemberResponse getMemberResponse = FutureUtils.get(
            service.getItem(getMemberRequest.build()));
    checkException(getMemberResponse.getStatus(), key);
    SortedListProtocol.SortedListEntity sortedListEntity = getMemberResponse.getEntity();
    return Arrays.asList(sortedListEntity.getScore(), getMemberResponse.getCount());
  }

  private void checkException(CommonProtocol.Status status, String key) {
    switch (status) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      default:
        throw new DstException(String.format("Error code is %d", status.getNumber()));
    }
  }
}
