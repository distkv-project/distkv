package org.dst.client;

import org.dst.common.exception.DstException;
import org.dst.common.exception.KeyNotFoundException;
import org.dst.common.entity.sortedList.SortedListEntity;
import org.dst.rpc.protobuf.generated.SortedListProtocol;
import org.dst.rpc.service.DstSortedListService;

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
      builder.setInfo(entity.getInfo());
      builder.setScore(entity.getScore());
      listEntities.add(builder.build());
    }
    requestBuilder.addAllList(listEntities);
    SortedListProtocol.PutResponse response =
        service.put(requestBuilder.build());
    switch (response.getStatus()) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      default:
        throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public void incItem(String key, String info) {
    SortedListProtocol.IncItemRequest.Builder requestBuilder =
        SortedListProtocol.IncItemRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setInfo(info);
    SortedListProtocol.IncItemResponse response =
        service.incItem(requestBuilder.build());
    switch (response.getStatus()) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      default:
        throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public LinkedList<SortedListEntity> top(String key, int topNum) {
    SortedListProtocol.TopRequest.Builder topRequestBuilder =
        SortedListProtocol.TopRequest.newBuilder();
    topRequestBuilder.setKey(key);
    topRequestBuilder.setTopNum(topNum);
    SortedListProtocol.TopResponse response =
        service.top(topRequestBuilder.build());
    switch (response.getStatus()) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      default:
        throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
    LinkedList<SortedListEntity> list = new LinkedList<>();
    for (SortedListProtocol.SortedListEntity entity : response.getListList()) {
      list.add(new SortedListEntity(entity.getInfo(), entity.getScore()));
    }
    return list;
  }

  public void del(String key) {
    SortedListProtocol.DelRequest.Builder requestBuilder =
        SortedListProtocol.DelRequest.newBuilder();
    requestBuilder.setKey(key);
    SortedListProtocol.DelResponse response =
        service.del(requestBuilder.build());
    switch (response.getStatus()) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      default:
        throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public void delItem(String key, String info) {
    SortedListProtocol.DelItemRequest.Builder requestBuilder =
        SortedListProtocol.DelItemRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setInfo(info);
    SortedListProtocol.DelItemResponse response =
        service.delItem(requestBuilder.build());
    switch (response.getStatus()) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      default:
        throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public void putItem(String key, SortedListEntity entity) {
    SortedListProtocol.PutItemRequest.Builder requestBuilder =
        SortedListProtocol.PutItemRequest.newBuilder();
    requestBuilder.setKey(key);
    requestBuilder.setInfo(entity.getInfo());
    requestBuilder.setScore(entity.getScore());
    SortedListProtocol.PutItemResponse response =
        service.putItem(requestBuilder.build());
    switch (response.getStatus()) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      default:
        throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }
}
