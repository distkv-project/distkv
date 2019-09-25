package org.dst.server.service;

import org.dst.common.exception.DstException;
import org.dst.common.exception.KeyNotFoundException;
import org.dst.core.KVStore;
import org.dst.common.entity.sortedList.SortedListEntity;
import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.SortedListProtocol;
import org.dst.rpc.service.DstSortedListService;
import org.dst.server.base.DstBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class DstSortedListServiceImpl extends DstBaseService implements DstSortedListService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstSortedListServiceImpl.class);

  public DstSortedListServiceImpl(KVStore store) {
    super(store);
  }

  @Override
  public SortedListProtocol.PutResponse put(SortedListProtocol.PutRequest request) {
    SortedListProtocol.PutResponse.Builder responseBuilder =
        SortedListProtocol.PutResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      LinkedList<SortedListEntity> linkedList = new LinkedList<>();
      for (int i = 0; i < request.getListCount(); i++) {
        linkedList.add(new SortedListEntity(request.getList(i).getInfo(),
            request.getList(i).getScore()));
      }
      getStore().sortLists().put(request.getKey(), linkedList);
      status = CommonProtocol.Status.OK;
    } catch (DstException e) {
      LOGGER.error("Failed to put a list to store: {}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public SortedListProtocol.TopResponse top(SortedListProtocol.TopRequest request) {
    SortedListProtocol.TopResponse.Builder responseBuilder =
        SortedListProtocol.TopResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      List<SortedListEntity> topList =
          getStore().sortLists().top(request.getKey(), request.getTopNum());
      ListIterator<SortedListEntity> listIterator = topList.listIterator();
      while (listIterator.hasNext()) {
        SortedListEntity entity = listIterator.next();
        SortedListProtocol.SortedListEntity.Builder builder =
            SortedListProtocol.SortedListEntity.newBuilder();
        builder.setScore(entity.getScore());
        builder.setInfo(entity.getInfo());
        responseBuilder.addList(builder.build());
      }
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DstException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public SortedListProtocol.DelResponse del(SortedListProtocol.DelRequest request) {
    SortedListProtocol.DelResponse.Builder responseBuilder =
        SortedListProtocol.DelResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      getStore().sortLists().del(request.getKey());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DstException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public SortedListProtocol.IncItemResponse incItem(SortedListProtocol.IncItemRequest request) {
    SortedListProtocol.IncItemResponse.Builder responseBuilder =
        SortedListProtocol.IncItemResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      getStore().sortLists().incItem(request.getKey(), request.getInfo());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DstException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public SortedListProtocol.PutItemResponse putItem(SortedListProtocol.PutItemRequest request) {
    SortedListProtocol.PutItemResponse.Builder responseBuilder =
        SortedListProtocol.PutItemResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      getStore().sortLists().putItem(request.getKey(),
          new SortedListEntity(request.getInfo(), request.getScore()));
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DstException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public SortedListProtocol.DelItemResponse delItem(SortedListProtocol.DelItemRequest request) {
    SortedListProtocol.DelItemResponse.Builder responseBuilder =
        SortedListProtocol.DelItemResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      getStore().sortLists().delItem(request.getKey(),request.getInfo());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DstException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }
}
