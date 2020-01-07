package com.distkv.dst.core.concepts;

import com.distkv.dst.common.exception.DstKeyDuplicatedException;
import com.distkv.dst.common.DstTuple;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.exception.SortedListMembersDuplicatedException;
import com.distkv.dst.common.exception.SortedListIncrScoreOutOfRangeException;
import com.distkv.dst.common.exception.SortedListMemberNotFoundException;
import com.distkv.dst.common.exception.SortedListTopNumBeNonNegativeException;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import com.distkv.dst.core.struct.slist.SortedList;
import com.distkv.dst.core.struct.slist.SortedListLinkedImpl;

import java.util.List;
import java.util.LinkedList;

public class DstSortedListsImpl
    extends DstConcepts<SortedList>
    implements DstSortedLists {
  public DstSortedListsImpl() {

  }

  @Override
  public void put(String key, LinkedList<SortedListEntity> list) {
    if (dstKeyValueMap.containsKey(key)) {
      throw new DstKeyDuplicatedException(key);
    }
    SortedList sortedList = new SortedListLinkedImpl();
    if (!sortedList.put(list)) {
      throw new SortedListMembersDuplicatedException(key);
    }
    dstKeyValueMap.put(key, sortedList);
  }

  @Override
  public void putMember(String key, SortedListEntity item) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final SortedList sortedList = dstKeyValueMap.get(key);
    sortedList.putItem(item);
  }

  @Override
  public void removeMember(String key, String member) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final SortedList sortedList = dstKeyValueMap.get(key);
    final boolean isFound = sortedList.removeItem(member);
    if (!isFound) {
      throw new SortedListMemberNotFoundException(key);
    }
  }

  @Override
  public void incrScore(String key, String member, int delta) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final SortedList sortedList = dstKeyValueMap.get(key);
    final int resultByIncrScore = sortedList.incrScore(member, delta);
    if (0 == resultByIncrScore) {
      throw new SortedListMemberNotFoundException(key);
    } else if (-1 == resultByIncrScore) {
      throw new SortedListIncrScoreOutOfRangeException(key);
    }
  }

  @Override
  public List<SortedListEntity> top(String key, int topNum) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final SortedList sortedList = dstKeyValueMap.get(key);
    if (topNum > sortedList.size()) {
      topNum = sortedList.size();
    }
    if (topNum <= 0) {
      throw new SortedListTopNumBeNonNegativeException(key, topNum);
    }
    return sortedList.subList(1, topNum);
  }

  @Override
  public DstTuple<Integer, Integer> getMember(String key, String member) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final SortedList sortedLists = dstKeyValueMap.get(key);
    DstTuple<Integer, Integer> result = sortedLists.getItem(member);
    if (null == result) {
      throw new SortedListMemberNotFoundException(key);
    }
    return result;
  }

}
