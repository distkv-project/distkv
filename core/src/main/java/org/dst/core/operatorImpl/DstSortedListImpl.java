package org.dst.core.operatorImpl;

import org.dst.core.operatorset.DstSortedList;
import org.dst.entity.SortedListEntity;
import org.dst.exception.DstException;
import org.dst.exception.KeyNotFoundException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.ListIterator;


public class DstSortedListImpl implements DstSortedList {

  HashMap<String, LinkedList<SortedListEntity>> sortedListMap;

  public DstSortedListImpl() {
    sortedListMap = new HashMap<>();
  }

  @Override
  public void put(String key, LinkedList<SortedListEntity> list) {
    Collections.sort(list);
    sortedListMap.put(key, list);
  }

  @Override
  public void del(String key) {
    sortedListMap.remove(key);
  }

  @Override
  public void putItem(String key, SortedListEntity item) {
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
  public void delItem(String key, String info) {
    LinkedList<SortedListEntity> list = sortedListMap.get(key);
    ListIterator<SortedListEntity> iterator = list.listIterator();
    while (iterator.hasNext()) {
      SortedListEntity now = iterator.next();
      if (now.getInfo().equals(info)) {
        iterator.remove();
      }
    }
  }

  @Override
  public void incItem(String key, String info) {
    LinkedList<SortedListEntity> list = sortedListMap.get(key);
    synchronized (this) {
      ListIterator<SortedListEntity> iterator = list.listIterator();
      while (iterator.hasNext()) {
        SortedListEntity now = iterator.next();
        if (now.getInfo().equals(info)) {
          now.setScore(now.getScore() + 1);
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
}
