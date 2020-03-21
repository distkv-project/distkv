package com.distkv.core.concepts;

import com.distkv.common.DistkvTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvWrongRequestFormatException;
import com.distkv.common.exception.SortedListIncrScoreOutOfRangeException;
import com.distkv.common.exception.SortedListMemberNotFoundException;
import com.distkv.common.exception.SortedListMembersDuplicatedException;
import com.distkv.common.exception.SortedListTopNumIsNonNegativeException;
import com.distkv.core.DistkvMapInterface;
import com.distkv.core.struct.slist.SortedList;
import com.distkv.core.struct.slist.SortedListLinkedImpl;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistGetMemberRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistGetMemberResponse;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistIncrScoreRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistPutMemberRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistPutRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistRemoveMemberRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistTopRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistTopResponse;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class DistkvSortedListsImpl extends DistkvConcepts<SortedList>
    implements DistkvSortedLists {

  public DistkvSortedListsImpl(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public void get(String key, Builder builder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void put(String key, Any request) throws DistkvException {
    try {
      SlistPutRequest slistPutRequest = request.unpack(SlistPutRequest.class);
      LinkedList<SortedListEntity> linkedList = new LinkedList<>();
      for (int i = 0; i < slistPutRequest.getListCount(); i++) {
        linkedList.add(new SortedListEntity(slistPutRequest.getList(i).getMember(),
            slistPutRequest.getList(i).getScore()));
      }
      put(key, linkedList);
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public void put(String key, SortedList value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void put(String key, List<SortedListEntity> value) throws DistkvException {
    SortedList sortedList = new SortedListLinkedImpl();
    if (!sortedList.put(value)) {
      throw new SortedListMembersDuplicatedException(key);
    }
    distkvKeyValueMap.put(key, sortedList);
  }

  @Override
  public void putMember(String key, Any request) throws DistkvException {
    try {
      SlistPutMemberRequest slistPutMemberRequest = request
          .unpack(SlistPutMemberRequest.class);
      putMember(key, new SortedListEntity(slistPutMemberRequest.getMember(),
          slistPutMemberRequest.getScore()));
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public void putMember(String key, SortedListEntity item) throws DistkvException {
    final SortedList sortedList = get(key);
    sortedList.putItem(item);
  }

  @Override
  public void removeMember(String key, Any request)
      throws DistkvException {
    try {
      SlistRemoveMemberRequest slistRemoveMemberRequest = request
          .unpack(SlistRemoveMemberRequest.class);
      removeMember(key, slistRemoveMemberRequest.getMember());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  public void removeMember(String key, String member) throws DistkvException {
    final SortedList sortedList = get(key);
    final boolean isFound = sortedList.removeItem(member);
    if (!isFound) {
      throw new SortedListMemberNotFoundException(key);
    }
  }

  @Override
  public void incrScore(String key, Any request) throws DistkvException {
    try {
      SlistIncrScoreRequest slistIncrScoreRequest = request
          .unpack(SlistIncrScoreRequest.class);
      incrScore(key, slistIncrScoreRequest.getMember(), slistIncrScoreRequest.getDelta());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public void incrScore(String key, String member, int delta) throws DistkvException {
    final SortedList sortedList = get(key);
    final int resultByIncrScore = sortedList.incrScore(member, delta);
    if (0 == resultByIncrScore) {
      throw new SortedListMemberNotFoundException(key);
    } else if (-1 == resultByIncrScore) {
      throw new SortedListIncrScoreOutOfRangeException(key);
    }
  }

  @Override
  public void top(String key, Any request, Builder builder)
      throws DistkvException {
    try {
      SlistTopRequest slistTopRequest = request.unpack(SlistTopRequest.class);
      List<SortedListEntity> topList = top(key, slistTopRequest.getCount());
      ListIterator<SortedListEntity> listIterator = topList.listIterator();
      SlistTopResponse.Builder slistBuilder = SlistTopResponse.newBuilder();

      while (listIterator.hasNext()) {
        SortedListEntity entity = listIterator.next();
        SortedListProtocol.SortedListEntity.Builder slistEntity =
            SortedListProtocol.SortedListEntity.newBuilder();
        slistEntity.setScore(entity.getScore());
        slistEntity.setMember(entity.getMember());
        slistBuilder.addList(slistEntity.build());
      }
      builder.setResponse(Any.pack(slistBuilder.build()));
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public List<SortedListEntity> top(String key, int topNum) throws DistkvException {
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
  public void getMember(String key, Any request, Builder builder) throws DistkvException {
    try {
      SlistGetMemberRequest slistGetMemberRequest =
          request.unpack(SlistGetMemberRequest.class);
      DistkvTuple<Integer, Integer> tuple =
          getMember(key, slistGetMemberRequest.getMember());
      SortedListProtocol.SortedListEntity.Builder slistEntity =
          SortedListProtocol.SortedListEntity.newBuilder();
      slistEntity.setMember(slistGetMemberRequest.getMember());
      slistEntity.setScore(tuple.getFirst());
      SlistGetMemberResponse.Builder slistBuilder = SlistGetMemberResponse.newBuilder();
      slistBuilder.setEntity(slistEntity);
      slistBuilder.setCount(tuple.getSecond());
      builder.setResponse(Any.pack(slistBuilder.build()));
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  public DistkvTuple<Integer, Integer> getMember(String key, String member) throws DistkvException {
    final SortedList sortedLists = get(key);
    DistkvTuple<Integer, Integer> result = sortedLists.getItem(member);
    if (null == result) {
      throw new SortedListMemberNotFoundException(key);
    }
    return result;
  }

}
