package org.dst.client;

import org.dst.entity.SortedListEntity;
import org.dst.exception.DstException;
import org.dst.exception.KeyNotFoundException;
import org.dst.server.generated.SortedListProtocol;
import org.dst.server.service.DstSortedListService;

import java.util.LinkedList;

import static org.dst.server.generated.CommonProtocol.Status.KEY_NOT_FOUND;
import static org.dst.server.generated.CommonProtocol.Status.OK;

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
}
