package com.distkv.core.concepts;

import com.distkv.common.DistkvTuple;
import com.distkv.common.entity.sortedList.SlistEntity;
import com.distkv.common.utils.Status;
import com.distkv.core.struct.slist.Slist;
import java.util.List;

public interface DistkvSlists {

  /**
   * This method will put a key-value pair to map.
   *
   * @param key The key to store.
   * @param list The SortedList value to store.
   */
  void put(String key, Slist list);

  /**
   * This method will del a key-value pair to map.
   *
   * @param key The key to store.
   */
  Status drop(String key);

  /**
   * This method will put a item to a sortedList
   *
   * @param key   the key to store
   * @param item  the item in sortedList
   */
  void putMember(String key, SlistEntity item);

  /**
   * This method will del a item in sortedList
   *
   * @param key   the key to store
   * @param member  the itemEntity info in sortedList, Find the item by info
   */
  void removeMember(String key, String member);

  /**
   * This method will inc a itemEntity score in sortedList
   *
   * @param key   the key to store
   * @param member  the itemEntity info in sortedList, Find the item by info
   * @param delta amount of change in score
   */
  void incrScore(String key, String member, int delta);

  /**
   * This method will get a top list in map
   *
   * @param key   the key to store
   * @param topNum  the size of topList
   */
  List<SlistEntity> top(String key, int topNum);

  /**
   * Get the score and ranking values of the given key and member
   *
   * @param key the key to store
   * @param member the itemEntity info in sortedList, Find the item by info
   * @return the DistKVTuple value which the first element is the score and
   * the second element is the ranking of the given key and member
   */
  DistkvTuple<Integer, Integer> getMember(String key, String member);
}
