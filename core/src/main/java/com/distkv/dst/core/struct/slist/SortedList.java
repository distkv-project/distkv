package com.distkv.dst.core.struct.slist;

import com.distkv.dst.common.entity.sortedList.SortedListEntity;

import java.util.List;

public interface SortedList {

  void put(String key, List<SortedListEntity> list);

}
