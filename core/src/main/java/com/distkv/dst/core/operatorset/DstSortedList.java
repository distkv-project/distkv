package com.distkv.dst.core.operatorset;

import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import java.util.LinkedList;
import java.util.List;

public interface DstSortedList {

  /**
   * This method will put a key-value pair to map
   *
   * @param key   the key to store
   * @param list the SortedList value to store
   */
  void put(String key, LinkedList<SortedListEntity> list);

  /**
   * This method will del a key-value pair to map
   *
   * @param key   the key to store
   */
  void drop(String key);

  /**
   * This method will put a item to a sortedList
   *
   * @param key   the key to store
   * @param item  the item in sortedList
   */
  void putItem(String key, SortedListEntity item);

  /**
   * This method will del a item in sortedList
   *
   * @param key   the key to store
   * @param member  the itemEntity info in sortedList, Find the item by info
   */
  void delItem(String key, String member);

  /**
   * This method will inc a itemEntity score in sortedList
   *
   * @param key   the key to store
   * @param member  the itemEntity info in sortedList, Find the item by info
   * @param delta amount of change in score
   */
  void incrItem(String key, String member, int delta);

  /**
   * This method will get a top list in map
   *
   * @param key   the key to store
   * @param topNum  the size of topList
   */
  List<SortedListEntity> top(String key, int topNum);
}
