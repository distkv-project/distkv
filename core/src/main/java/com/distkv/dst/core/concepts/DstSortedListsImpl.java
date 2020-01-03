package com.distkv.dst.core.concepts;

import com.distkv.dst.common.DstTuple;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.exception.SortedListMemberNotFoundException;
import com.distkv.dst.common.exception.SortedListTopNumBePositiveException;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.util.ListIterator;

public class DstSortedListsImpl extends DstConcepts<LinkedList<SortedListEntity>>
    implements DstSortedLists {

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
    boolean isFound = false;
    while (iterator.hasNext()) {
      SortedListEntity now = iterator.next();
      if (now.getMember().equals(member)) {
        isFound = true;
        iterator.remove();
      }
    }
    if (!isFound) {
      throw new SortedListMemberNotFoundException(key);
    }
  }

  @Override
  public void incrScore(String key, String member, int delta) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    LinkedList<SortedListEntity> list = dstKeyValueMap.get(key);
    ListIterator<SortedListEntity> iterator = list.listIterator();
    boolean isFound = false;
    while (iterator.hasNext()) {
      SortedListEntity now = iterator.next();
      if (now.getMember().equals(member)) {
        isFound = true;
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
    if (!isFound) {
      throw new SortedListMemberNotFoundException(key);
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
      throw new SortedListTopNumBePositiveException(key, topNum);
    }
    List<SortedListEntity> topList = list.subList(0, topNum);
    return topList;
  }

  @Override
  public DstTuple<Integer, Integer> getMember(String key, String member) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    int ranking = 1;
    final LinkedList<SortedListEntity> sortedListEntities = dstKeyValueMap.get(key);

    for (final SortedListEntity sortedListEntity : sortedListEntities) {
      if (sortedListEntity.getMember().equals(member)) {
        return new DstTuple<>(sortedListEntity.getScore(), ranking);
      }
      ranking++;
    }
    throw new SortedListMemberNotFoundException(key);
  }

}
