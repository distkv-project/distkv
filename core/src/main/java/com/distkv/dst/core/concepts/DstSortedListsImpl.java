package com.distkv.dst.core.concepts;

import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.exception.SortedListDuplicatedMembersException;
import com.distkv.dst.common.exception.SortedListIncrScoreOutOfRange;
import com.distkv.dst.common.exception.SortedListMemberNotFoundException;
import com.distkv.dst.common.exception.SortedListTopNumBePositiveException;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import com.distkv.dst.core.struct.slist.SortedList;
import com.distkv.dst.core.struct.slist.SortedListLinkedListImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class DstSortedListsImpl
    extends DstConcepts<SortedList>
    implements DstSortedLists {
  public DstSortedListsImpl() {

  }

  @Override
  public void put(String key, LinkedList<SortedListEntity> list) {
    SortedList sortedList = new SortedListLinkedListImpl();
    if (!sortedList.put(list)) {
      throw new SortedListDuplicatedMembersException(key);
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
    final int isFound = sortedList.incrScore(member, delta);
    if (0 == isFound) {
      throw new SortedListMemberNotFoundException(key);
    } else if (-1 == isFound) {
      throw new SortedListIncrScoreOutOfRange(key);
    }
  }

  @Override
  public List<SortedListEntity> top(String key, int topNum) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final SortedList sortedList = dstKeyValueMap.get(key);
    if (topNum > sortedList.getSize()) {
      topNum = sortedList.getSize();
    }
    if (topNum < 0) {
      throw new SortedListTopNumBePositiveException(key, topNum);
    }
    if (topNum == 0) {
      return new ArrayList<>();
    }
    return sortedList.subList(1, topNum);
  }

  @Override
  public List<Integer> getMember(String key, String member) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final SortedList sortedList = dstKeyValueMap.get(key);
    final List<Integer> resultList = sortedList.getItem(member);
    if (null == resultList) {
      throw new SortedListMemberNotFoundException(key);
    }
    return resultList;
  }

}
