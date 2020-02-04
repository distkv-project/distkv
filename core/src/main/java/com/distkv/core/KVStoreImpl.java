package com.distkv.core;

import com.distkv.core.concepts.DistKVListsImpl;
import com.distkv.core.concepts.DistKVSetsImpl;
import com.distkv.core.concepts.DstSortedLists;
import com.distkv.core.concepts.DistKVStrings;
import com.distkv.core.concepts.DistKVDictsImpl;
import com.distkv.core.concepts.DistKVStringsImpl;
import com.distkv.core.concepts.DistKVDicts;
import com.distkv.core.concepts.DistKVSets;
import com.distkv.core.concepts.DistKVLists;
import com.distkv.core.concepts.DistKVSortedListsImpl;

public class KVStoreImpl implements KVStore {

  private DistKVStringsImpl strs;

  private DistKVListsImpl lists;

  private DistKVSetsImpl sets;

  private DistKVDictsImpl dicts;

  private DstSortedLists sortedLists;

  public KVStoreImpl() {
    this.strs = new DistKVStringsImpl();
    this.lists = new DistKVListsImpl();
    this.sets = new DistKVSetsImpl();
    this.dicts = new DistKVDictsImpl();
    this.sortedLists = new DistKVSortedListsImpl();
  }

  @Override
  public DistKVStrings strs() {
    return strs;
  }

  @Override
  public DistKVLists lists() {
    return lists;
  }

  @Override
  public DistKVSets sets() {
    return sets;
  }

  @Override
  public DistKVDicts dicts() {
    return dicts;
  }

  @Override
  public DstSortedLists sortLists() {
    return sortedLists;
  }

}
