package com.distkv.dst.core.struct.slist;

import com.distkv.dst.common.entity.sortedList.SortedListEntity;

import java.util.List;

public interface SortedList {

  int getSize();

  boolean put(List<SortedListEntity> sortedListEntities);

  void putItem(SortedListEntity sortedListEntity);

  boolean removeItem(String member);

  int incrScore(String member, int delta);

  List<SortedListEntity> subList(int start, int end);

  List<Integer> getItem(String member);
}
