package com.distkv.dst.server.service;

import com.distkv.dst.common.utils.FutureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CompletableFuture;
import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.dst.rpc.service.DstSortedListService;
import com.distkv.dst.server.base.DstBaseService;


public class DstSortedListServiceImpl extends DstBaseService implements DstSortedListService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstSortedListServiceImpl.class);

  public DstSortedListServiceImpl(KVStore store) {
    super(store);
  }

  @Override
  public CompletableFuture<SortedListProtocol.PutResponse> put(
      SortedListProtocol.PutRequest request) {
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
    return FutureUtils.newCompletableFuture(responseBuilder.build());
  }

  @Override
  public CompletableFuture<SortedListProtocol.TopResponse> top(
      SortedListProtocol.TopRequest request) {
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
    return FutureUtils.newCompletableFuture(responseBuilder.build());
  }

  @Override
  public CompletableFuture<CommonProtocol.DropResponse> drop(
      CommonProtocol.DropRequest request) {
    CommonProtocol.DropResponse.Builder responseBuilder =
        CommonProtocol.DropResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      getStore().sortLists().drop(request.getKey());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DstException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return FutureUtils.newCompletableFuture(responseBuilder.build());
  }

  @Override
  public CompletableFuture<SortedListProtocol.IncrScoreResponse> incrScore(
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
    return FutureUtils.newCompletableFuture(responseBuilder.build());
  }

  @Override
  public CompletableFuture<SortedListProtocol.PutMemberResponse> putMember(
      SortedListProtocol.PutMemberRequest request) {
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
    return FutureUtils.newCompletableFuture(responseBuilder.build());
  }

  @Override
  public CompletableFuture<SortedListProtocol.RemoveMemberResponse> removeMember(
      SortedListProtocol.RemoveMemberRequest request) {
    SortedListProtocol.RemoveMemberResponse.Builder responseBuilder =
        SortedListProtocol.RemoveMemberResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      getStore().sortLists().removeItem(request.getKey(),request.getMember());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      LOGGER.error("Failed to remove SortedList, caused by key not found: %s", request.getKey());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DstException e) {
      LOGGER.error("Failed to remove SortedList Member, caused by member not found: %s",
          request.getMember());
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return FutureUtils.newCompletableFuture(responseBuilder.build());
  }

  @Override
  public CompletableFuture<SortedListProtocol.GetMemberResponse> getMember(
          SortedListProtocol.GetMemberRequest request) {
    SortedListProtocol.GetMemberResponse.Builder getMemberResponseBuilder =
            SortedListProtocol.GetMemberResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      List<Integer> scoreAndRankingValues =
              getStore().sortLists().getItem(request.getKey(), request.getMember());
      SortedListProtocol.SortedListEntity.Builder sortedListEntityBuilder =
              SortedListProtocol.SortedListEntity.newBuilder();
      sortedListEntityBuilder.setMember(request.getMember());
      sortedListEntityBuilder.setScore(scoreAndRankingValues.get(0));
      getMemberResponseBuilder.setEntity(sortedListEntityBuilder);
      getMemberResponseBuilder.setCount(scoreAndRankingValues.get(1));
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DstException e) {
      LOGGER.error(e.getMessage());
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    getMemberResponseBuilder.setStatus(status);
    return FutureUtils.newCompletableFuture(getMemberResponseBuilder.build());
  }
}
