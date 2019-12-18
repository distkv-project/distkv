package com.distkv.dst.core.operatorImpl;

import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.core.operatorset.DstConcepts;
import com.distkv.dst.core.operatorset.DstSortedLists;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Arrays;

public class DstSortedListsImpl extends DstConcepts<LinkedList<SortedListEntity>>
    implements DstSortedLists {

  private boolean isFound;

  public DstSortedListsImpl() {
  }

  @Override
  public void put(String key, LinkedList<SortedListEntity> list) {
    // Note(qwang): Overwrite for do a sort.
    Collections.sort(list);
    dstKeyValueMap.put(key, list);
  }

  @Override
  public void putMember(String key, SortedListEntity item) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    LinkedList list = dstKeyValueMap.get(key);
    ListIterator<SortedListEntity> iterator = list.listIterator();
    while (iterator.hasNext()) {
      SortedListEntity now = iterator.next();
      if (now.compareTo(item) > 0) {
        iterator.previous();
        iterator.add(item);
        break;
      }
    }
    if (!iterator.hasNext()) {
      iterator.add(item);
    }
  }

  @Override
  public void removeMember(String key, String member) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    LinkedList<SortedListEntity> list = dstKeyValueMap.get(key);
    ListIterator<SortedListEntity> iterator = list.listIterator();
    isFound = false;
    while (iterator.hasNext()) {
      SortedListEntity now = iterator.next();
      if (now.getMember().equals(member)) {
        isFound = true;
        iterator.remove();
      }
    }
    if (isFound == false) {
      throw new DstException("not find info at SortedList");
    }
  }

  @Override
  public void incrScore(String key, String member, int delta) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    LinkedList<SortedListEntity> list = dstKeyValueMap.get(key);
    ListIterator<SortedListEntity> iterator = list.listIterator();
    boolean isFounnd = false;
    while (iterator.hasNext()) {
      SortedListEntity now = iterator.next();
      if (now.getMember().equals(member)) {
        isFounnd = true;
        now.setScore(now.getScore() + delta);
        if (iterator.nextIndex() != 1) {
          iterator.remove();
          while (iterator.hasPrevious()) {
            SortedListEntity entity = iterator.previous();
            if (now.compareTo(entity) <= 0) {
              iterator.add(now);
              break;
            }
            if (now.compareTo(entity) > 0) {
              iterator.next();
              iterator.add(now);
              break;
            }
          }
          break;
        }
      }
    }
    if (isFounnd == false) {
      throw new DstException("not find info at SortedList");
    }
  }

  @Override
  public List<SortedListEntity> top(String key, int topNum) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    LinkedList list = dstKeyValueMap.get(key);
    if (topNum > list.size()) {
      topNum = list.size();
    }
    if (topNum < 0) {
      throw new DstException("topNum must be bigger than 0");
    }
    List<SortedListEntity> topList = list.subList(0, topNum);
    return topList;
  }

  @Override
  public List<Integer> getMember(String key, String member) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    int ranking = 1;
    final LinkedList<SortedListEntity> sortedListEntities = dstKeyValueMap.get(key);

    for (final SortedListEntity sortedListEntity : sortedListEntities) {
      if (sortedListEntity.getMember().equals(member)) {
        return Arrays.asList(sortedListEntity.getScore(), ranking);
      }
      ranking++;
    }
    throw new DstException("not find info at SortedList");
  }

}
