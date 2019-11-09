package org.dst.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.dst.common.exception.DstException;
import org.dst.common.exception.KeyNotFoundException;
import org.dst.core.KVStore;
import org.dst.common.entity.sortedList.SortedListEntity;
import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.SortedListProtocol;
import org.dst.rpc.service.DstSortedListService;
import org.dst.server.base.DstBaseService;


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
        linkedList.add(new SortedListEntity(request.getList(i).getMember(),
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
          getStore().sortLists().top(request.getKey(), request.getCount());
      ListIterator<SortedListEntity> listIterator = topList.listIterator();
      while (listIterator.hasNext()) {
        SortedListEntity entity = listIterator.next();
        SortedListProtocol.SortedListEntity.Builder builder =
            SortedListProtocol.SortedListEntity.newBuilder();
        builder.setScore(entity.getScore());
        builder.setMember(entity.getMember());
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
  public CommonProtocol.DropResponse drop(CommonProtocol.DropRequest request) {
    CommonProtocol.DropResponse.Builder responseBuilder =
        CommonProtocol.DropResponse.newBuilder();
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
  public SortedListProtocol.IncrScoreResponse incrItem(
      SortedListProtocol.IncrScoreRequest request) {
    SortedListProtocol.IncrScoreResponse.Builder responseBuilder =
        SortedListProtocol.IncrScoreResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      getStore().sortLists().incrItem(request.getKey(),
          request.getMember(), request.getDelta());
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
  public SortedListProtocol.PutMemberResponse putItem(SortedListProtocol.PutMemberRequest request) {
    SortedListProtocol.PutMemberResponse.Builder responseBuilder =
        SortedListProtocol.PutMemberResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      getStore().sortLists().putItem(request.getKey(),
          new SortedListEntity(request.getMember(), request.getScore()));
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
  public SortedListProtocol.DelMemberResponse delItem(SortedListProtocol.DelMemberRequest request) {
    SortedListProtocol.DelMemberResponse.Builder responseBuilder =
        SortedListProtocol.DelMemberResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      getStore().sortLists().delItem(request.getKey(),request.getMember());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      LOGGER.error("Failed to delete SortedList, caused by key not found: %s", request.getKey());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DstException e) {
      LOGGER.error("Failed to delete SortedList Member, caused by member not found: %s",
          request.getMember());
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }
}
