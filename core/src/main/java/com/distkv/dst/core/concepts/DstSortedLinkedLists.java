package com.distkv.dst.core.concepts;

import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import java.util.LinkedList;

public interface DstSortedLinkedLists extends DstSortedLists {

  /**
   * This method will put a key-value pair to map.
   *
   * @param key The key to store.
   * @param list The SortedList value to store.
   */
  void put(String key, LinkedList<SortedListEntity> list);
}
