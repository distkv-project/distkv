package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.DistkvTuple;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.exception.SortedListMembersDuplicatedException;
import com.distkv.common.exception.SortedListIncrScoreOutOfRangeException;
import com.distkv.common.exception.SortedListMemberNotFoundException;
import com.distkv.common.exception.SortedListTopNumIsNonNegativeException;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.core.DistkvMapInterface;
import com.distkv.core.struct.slist.Slist;
import com.distkv.core.struct.slist.SlistLinkedImpl;

import java.util.List;
import java.util.LinkedList;

public class DistkvSortedListsImpl
    extends DistkvConcepts<Slist>
    implements DistkvSortedLists {

  public DistkvSortedListsImpl(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public void put(String key, LinkedList<SortedListEntity> list) {
    if (distkvKeyValueMap.containsKey(key)) {
      throw new DistkvKeyDuplicatedException(key);
    }
    Slist slist = new SlistLinkedImpl();
    if (!slist.put(list)) {
      throw new SortedListMembersDuplicatedException(key);
    }
    distkvKeyValueMap.put(key, slist);
  }

  @Override
  public void putMember(String key, SortedListEntity item) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final Slist slist = get(key);
    slist.putItem(item);
  }

  @Override
  public void removeMember(String key, String member) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final Slist slist = get(key);
    final boolean isFound = slist.removeItem(member);
    if (!isFound) {
      throw new SortedListMemberNotFoundException(key);
    }
  }

  @Override
  public void incrScore(String key, String member, int delta) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final Slist slist = get(key);
    final int resultByIncrScore = slist.incrScore(member, delta);
    if (0 == resultByIncrScore) {
      throw new SortedListMemberNotFoundException(key);
    } else if (-1 == resultByIncrScore) {
      throw new SortedListIncrScoreOutOfRangeException(key);
    }
  }

  @Override
  public List<SortedListEntity> top(String key, int topNum) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final Slist slist = get(key);
    if (topNum > slist.size()) {
      topNum = slist.size();
    }
    if (topNum <= 0) {
      throw new SortedListTopNumIsNonNegativeException(key, topNum);
    }
    return slist.subList(1, topNum);
  }

  @Override
  public DistkvTuple<Integer, Integer> getMember(String key, String member) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    final Slist sortedLists = get(key);
    DistkvTuple<Integer, Integer> result = sortedLists.getItem(member);
    if (null == result) {
      throw new SortedListMemberNotFoundException(key);
    }
    return result;
  }

}
