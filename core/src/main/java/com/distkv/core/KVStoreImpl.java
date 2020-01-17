package com.distkv.core;

import com.distkv.core.concepts.DstListsImpl;
import com.distkv.core.concepts.DstSetsImpl;
import com.distkv.core.concepts.DstSortedLists;
import com.distkv.core.concepts.DstStrings;
import com.distkv.core.concepts.DstDictsImpl;
import com.distkv.core.concepts.DstStringsImpl;
import com.distkv.core.concepts.DstTablesImpl;
import com.distkv.core.concepts.DstTables;
import com.distkv.core.concepts.DstDicts;
import com.distkv.core.concepts.DstSets;
import com.distkv.core.concepts.DstLists;
import com.distkv.core.concepts.DstSortedListsImpl;

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
