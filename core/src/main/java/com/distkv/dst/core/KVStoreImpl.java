package com.distkv.dst.core;

import com.distkv.dst.core.operatorImpl.DstSortedListImpl;
import com.distkv.dst.core.operatorImpl.DstDictImpl;
import com.distkv.dst.core.operatorImpl.DstSetImpl;
import com.distkv.dst.core.operatorImpl.DstStringImpl;
import com.distkv.dst.core.operatorImpl.DstTableImpl;
import com.distkv.dst.core.operatorImpl.DstListImpl;
import com.distkv.dst.core.operatorset.DstTable;
import com.distkv.dst.core.operatorset.DstDict;
import com.distkv.dst.core.operatorset.DstSortedList;
import com.distkv.dst.core.operatorset.DstSet;
import com.distkv.dst.core.operatorset.DstList;
import com.distkv.dst.core.operatorset.DstString;

public class KVStoreImpl implements KVStore {

  private DstStringImpl strs;

  private DstListImpl lists;

  private DstSetImpl sets;

  private DstDictImpl dicts;

  private DstSortedList sortedLists;

  private DstTableImpl tables;

  public KVStoreImpl() {
    this.strs = new DstStringImpl();
    this.lists = new DstListImpl();
    this.sets = new DstSetImpl();
    this.dicts = new DstDictImpl();
    this.tables = new DstTableImpl();
    this.sortedLists = new DstSortedListImpl();
  }

  @Override
  public DstString strs() {
    return strs;
  }

  @Override
  public DstList lists() {
    return lists;
  }

  @Override
  public DstSet sets() {
    return sets;
  }

  @Override
  public DstDict dicts() {
    return dicts;
  }

  @Override
  public DstSortedList sortLists() {
    return sortedLists;
  }

  @Override
  public DstTable tables() {
    return tables;
  }

}
