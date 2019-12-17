package com.distkv.dst.core;

import com.distkv.dst.core.operatorImpl.DstSortedListsImpl;
import com.distkv.dst.core.operatorImpl.DstDictsImpl;
import com.distkv.dst.core.operatorImpl.DstSetsImpl;
import com.distkv.dst.core.operatorImpl.DstStringsImpl;
import com.distkv.dst.core.operatorImpl.DstTablesImpl;
import com.distkv.dst.core.operatorImpl.DstListsImpl;
import com.distkv.dst.core.operatorset.DstTables;
import com.distkv.dst.core.operatorset.DstDicts;
import com.distkv.dst.core.operatorset.DstSortedLists;
import com.distkv.dst.core.operatorset.DstSets;
import com.distkv.dst.core.operatorset.DstLists;
import com.distkv.dst.core.operatorset.DstStrings;

public class KVStoreImpl implements KVStore {

  private DstStringsImpl strs;

  private DstListsImpl lists;

  private DstSetsImpl sets;

  private DstDictsImpl dicts;

  private DstSortedLists sortedLists;

  private DstTablesImpl tables;

  public KVStoreImpl() {
    this.strs = new DstStringsImpl();
    this.lists = new DstListsImpl();
    this.sets = new DstSetsImpl();
    this.dicts = new DstDictsImpl();
    this.tables = new DstTablesImpl();
    this.sortedLists = new DstSortedListsImpl();
  }

  @Override
  public DstStrings strs() {
    return strs;
  }

  @Override
  public DstLists lists() {
    return lists;
  }

  @Override
  public DstSets sets() {
    return sets;
  }

  @Override
  public DstDicts dicts() {
    return dicts;
  }

  @Override
  public DstSortedLists sortLists() {
    return sortedLists;
  }

  @Override
  public DstTables tables() {
    return tables;
  }

}
