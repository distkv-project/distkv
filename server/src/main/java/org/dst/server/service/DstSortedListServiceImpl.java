package org.dst.server.service;

import org.dst.core.KVStore;
import org.dst.entity.SortedListEntity;
import org.dst.exception.DictKeyNotFoundException;
import org.dst.exception.DstException;
import org.dst.server.base.DstBaseService;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.SortedListProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;


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
      for (int i = 0; i < request.getInfoCount(); i++) {
        linkedList.add(new SortedListEntity(request.getInfo(i),request.getScore(i)));
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
    try{
      List<SortedListEntity> topList = getStore().sortLists().top(request.getKey(), request.getTopNum());
      status = CommonProtocol.Status.OK;
    } catch (DictKeyNotFoundException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DstException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return null;
  }

  @Override
  public SortedListProtocol.DelResponse del(SortedListProtocol.DelRequest request) {
    return null;
  }

  @Override
  public SortedListProtocol.IncItemResponse incItem(SortedListProtocol.IncItemRequest request) {
    return null;
  }

  @Override
  public SortedListProtocol.PutItemResponse putItem(SortedListProtocol.PutItemRequest request) {
    return null;
  }

  @Override
  public SortedListProtocol.DelItemResponse delItem(SortedListProtocol.DelItemRequest request) {
    return null;
  }
}
