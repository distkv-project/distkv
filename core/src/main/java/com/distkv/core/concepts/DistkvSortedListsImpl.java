package com.distkv.core.concepts;

import com.distkv.common.DistkvTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.exception.SortedListIncrScoreOutOfRangeException;
import com.distkv.common.exception.SortedListMemberNotFoundException;
import com.distkv.common.exception.SortedListMembersDuplicatedException;
import com.distkv.common.exception.SortedListTopNumIsNonNegativeException;
import com.distkv.core.DistkvMapInterface;
import com.distkv.core.struct.slist.SortedList;
import com.distkv.core.struct.slist.SortedListLinkedImpl;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class DistkvSortedListsImpl extends DistkvConcepts<SortedList>
    implements DistkvSortedLists<SortedList> {

  private static Logger LOGGER = LoggerFactory.getLogger(DistkvSortedListsImpl.class);

  public DistkvSortedListsImpl(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public void get(String key, Builder builder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void put(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    SortedListProtocol.SlistPutRequest slistPutRequest = requestBody
        .unpack(SortedListProtocol.SlistPutRequest.class);
    CommonProtocol.Status status;
    try {
      LinkedList<SortedListEntity> linkedList = new LinkedList<>();
      for (int i = 0; i < slistPutRequest.getListCount(); i++) {
        linkedList.add(new SortedListEntity(slistPutRequest.getList(i).getMember(),
            slistPutRequest.getList(i).getScore()));
      }
      put(key, linkedList);
      status = CommonProtocol.Status.OK;
    } catch (DistkvKeyDuplicatedException e) {
      status = CommonProtocol.Status.DUPLICATED_KEY;
    } catch (DistkvException e) {
      LOGGER.error("Failed to put a slist to store: {1}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    builder.setStatus(status);
  }

  @Override
  public void put(String key, SortedList value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void put(String key, List<SortedListEntity> value) {
    if (distkvKeyValueMap.containsKey(key)) {
      throw new DistkvKeyDuplicatedException(key);
    }
    SortedList sortedList = new SortedListLinkedImpl();
    if (!sortedList.put(value)) {
      throw new SortedListMembersDuplicatedException(key);
    }
    distkvKeyValueMap.put(key, sortedList);
  }

  @Override
  public void drop(String key, Builder builder) {

    CommonProtocol.Status status;
    try {
      drop(key);
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DistkvException e) {
      LOGGER.error("Failed to drop a slist in store: {1}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    builder.setStatus(status);
  }

  @Override
  public void putMember(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    SortedListProtocol.SlistPutMemberRequest slistPutMemberRequest = requestBody
        .unpack(SortedListProtocol.SlistPutMemberRequest.class);
    CommonProtocol.Status status;
    try {
      putMember(key, new SortedListEntity(slistPutMemberRequest.getMember(),
          slistPutMemberRequest.getScore()));
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DistkvException e) {
      LOGGER.error("Failed to put a slist number in store: {1}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    builder.setStatus(status);
  }

  @Override
  public void putMember(String key, SortedListEntity item) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final SortedList sortedList = get(key);
    sortedList.putItem(item);
  }

  @Override
  public void removeMember(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    SortedListProtocol.SlistRemoveMemberRequest slistRemoveMemberRequest = requestBody
        .unpack(SortedListProtocol.SlistRemoveMemberRequest.class);
    CommonProtocol.Status status;
    try {
      removeMember(key, slistRemoveMemberRequest.getMember());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (SortedListMemberNotFoundException e) {
      status = CommonProtocol.Status.SLIST_MEMBER_NOT_FOUND;
    } catch (DistkvException e) {
      LOGGER.error("Failed to remove slist member in store :{1}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    builder.setStatus(status);
  }

  public void removeMember(String key, String member) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final SortedList sortedList = get(key);
    final boolean isFound = sortedList.removeItem(member);
    if (!isFound) {
      throw new SortedListMemberNotFoundException(key);
    }
  }

  @Override
  public void incrScore(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    SortedListProtocol.SlistIncrScoreRequest slistIncrScoreRequest = requestBody
        .unpack(SortedListProtocol.SlistIncrScoreRequest.class);
    CommonProtocol.Status status;
    try {
      incrScore(key, slistIncrScoreRequest.getMember(), slistIncrScoreRequest.getDelta());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (SortedListMemberNotFoundException e) {
      status = CommonProtocol.Status.SLIST_MEMBER_NOT_FOUND;
    } catch (DistkvException e) {
      LOGGER.error("Failed to incr a slist score in store: {1}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    builder.setStatus(status);
  }

  @Override
  public void incrScore(String key, String member, int delta) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final SortedList sortedList = get(key);
    final int resultByIncrScore = sortedList.incrScore(member, delta);
    if (0 == resultByIncrScore) {
      throw new SortedListMemberNotFoundException(key);
    } else if (-1 == resultByIncrScore) {
      throw new SortedListIncrScoreOutOfRangeException(key);
    }
  }

  @Override
  public void top(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    SortedListProtocol.SlistTopRequest slistTopRequest = requestBody
        .unpack(SortedListProtocol.SlistTopRequest.class);
    CommonProtocol.Status status;
    try {
      List<SortedListEntity> topList =
          top(key, slistTopRequest.getCount());
      ListIterator<SortedListEntity> listIterator = topList.listIterator();
      SortedListProtocol.SlistTopResponse.Builder slistBuilder =
          SortedListProtocol.SlistTopResponse.newBuilder();
      while (listIterator.hasNext()) {
        SortedListEntity entity = listIterator.next();
        SortedListProtocol.SortedListEntity.Builder slistEntity =
            SortedListProtocol.SortedListEntity.newBuilder();
        slistEntity.setScore(entity.getScore());
        slistEntity.setMember(entity.getMember());
        slistBuilder.addList(slistEntity.build());
      }
      builder.setResponse(Any.pack(slistBuilder.build()));
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (SortedListTopNumIsNonNegativeException e) {
      status = CommonProtocol.Status.SLIST_TOPNUM_BE_POSITIVE;
    } catch (DistkvException e) {
      LOGGER.error("Failed to get a slist top in store: {1}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    builder.setStatus(status);
  }

  @Override
  public List<SortedListEntity> top(String key, int topNum) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final SortedList sortedList = get(key);
    if (topNum > sortedList.size()) {
      topNum = sortedList.size();
    }
    if (topNum <= 0) {
      throw new SortedListTopNumIsNonNegativeException(key, topNum);
    }
    return sortedList.subList(1, topNum);
  }

  @Override
  public void getMember(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    SortedListProtocol.SlistGetMemberRequest slistGetMemberRequest =
        requestBody.unpack(SortedListProtocol.SlistGetMemberRequest.class);
    CommonProtocol.Status status;
    try {
      DistkvTuple<Integer, Integer> tuple =
          getMember(key, slistGetMemberRequest.getMember());
      SortedListProtocol.SortedListEntity.Builder slistEntity =
          SortedListProtocol.SortedListEntity.newBuilder();
      slistEntity.setMember(slistGetMemberRequest.getMember());
      slistEntity.setScore(tuple.getFirst());
      SortedListProtocol.SlistGetMemberResponse.Builder slistBuilder =
          SortedListProtocol.SlistGetMemberResponse.newBuilder();
      slistBuilder.setEntity(slistEntity);
      slistBuilder.setCount(tuple.getSecond());
      builder.setResponse(Any.pack(slistBuilder.build()));
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (SortedListMemberNotFoundException e) {
      status = CommonProtocol.Status.SLIST_MEMBER_NOT_FOUND;
    } catch (DistkvException e) {
      LOGGER.error("Failed to get slist member in store :{1}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    builder.setStatus(status);
  }

  public DistkvTuple<Integer, Integer> getMember(String key, String member) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final SortedList sortedLists = get(key);
    DistkvTuple<Integer, Integer> result = sortedLists.getItem(member);
    if (null == result) {
      throw new SortedListMemberNotFoundException(key);
    }
    return result;
  }

}
