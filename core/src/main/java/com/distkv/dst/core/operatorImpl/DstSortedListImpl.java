package com.distkv.dst.core.operatorImpl;

import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.core.operatorset.DstSortedList;
import com.distkv.dst.core.DstMapInterface;
import com.distkv.dst.core.DstHashMapImpl;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Arrays;

public class DstSortedListImpl implements DstSortedList {

  DstMapInterface<String, LinkedList<SortedListEntity>> sortedListMap;

  public DstSortedListImpl() {
    sortedListMap = new DstHashMapImpl<>();
  }

  @Override
  public void put(String key, LinkedList<SortedListEntity> list) {
    Collections.sort(list);
    sortedListMap.put(key, list);
  }

  @Override
  public void drop(String key) {
    if (!sortedListMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    sortedListMap.remove(key);
  }

  @Override
  public void putMember(String key, SortedListEntity item) {
    if (!sortedListMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    LinkedList list = sortedListMap.get(key);
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
    if (!sortedListMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    LinkedList<SortedListEntity> list = sortedListMap.get(key);
    ListIterator<SortedListEntity> iterator = list.listIterator();
    boolean isFounnd = false;
    while (iterator.hasNext()) {
      SortedListEntity now = iterator.next();
      if (now.getMember().equals(member)) {
        isFounnd = true;
        iterator.remove();
      }
    }
    if (isFounnd == false) {
      throw new DstException("not find info at SortedList");
    }
  }

  @Override
  public void incrScore(String key, String member, int delta) {
    if (!sortedListMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    LinkedList<SortedListEntity> list = sortedListMap.get(key);
    synchronized (this) {
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
  }

  @Override
  public List<SortedListEntity> top(String key, int topNum) {
    if (!sortedListMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    LinkedList list = sortedListMap.get(key);
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
    if (!sortedListMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    int ranking = 1;
    final LinkedList<SortedListEntity> sortedListEntities = sortedListMap.get(key);

    for (final SortedListEntity sortedListEntity : sortedListEntities) {
      if (sortedListEntity.getMember().equals(member)) {
        return Arrays.asList(sortedListEntity.getScore(), ranking);
      }
      ranking++;
    }
    throw new DstException("not find info at SortedList");
  }

}
