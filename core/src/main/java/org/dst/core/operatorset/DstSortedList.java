package org.dst.core.operatorset;

import org.dst.entity.SortedListEntity;

import java.util.LinkedList;
import java.util.List;

public interface DstSortedList {

  void put(String key, LinkedList<SortedListEntity> list);

  void del(String key);

  void putItem(String key, SortedListEntity item);

  void delItem(String key, String info);

  void incItem(String key, String info);

  List<SortedListEntity> top(String key, int topNum);
}
