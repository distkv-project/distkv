package com.distkv.core;

import com.distkv.core.concepts.DistkvListsImpl;
import com.distkv.core.concepts.DistkvSetsImpl;
import com.distkv.core.concepts.DistkvSortedLists;
import com.distkv.core.concepts.DistkvStrings;
import com.distkv.core.concepts.DistkvDictsImpl;
import com.distkv.core.concepts.DistkvStringsImpl;
import com.distkv.core.concepts.DistkvDicts;
import com.distkv.core.concepts.DistkvSets;
import com.distkv.core.concepts.DistkvLists;
import com.distkv.core.concepts.DistkvSortedListsImpl;

public class KVStoreImpl implements KVStore {

  private DistkvStringsImpl strs;

  private DistkvListsImpl lists;

  private DistkvSetsImpl sets;

  private DistkvDictsImpl dicts;

  private DistkvSortedLists sortedLists;

  public KVStoreImpl() {
    this.strs = new DistkvStringsImpl();
    this.lists = new DistkvListsImpl();
    this.sets = new DistkvSetsImpl();
    this.dicts = new DistkvDictsImpl();
    this.sortedLists = new DistkvSortedListsImpl();
  }

  @Override
  public DistkvStrings strs() {
    return strs;
  }

  @Override
  public DistkvLists lists() {
    return lists;
  }

  @Override
  public DistkvSets sets() {
    return sets;
  }

  @Override
  public DistkvDicts dicts() {
    return dicts;
  }

  @Override
  public DistkvSortedLists sortLists() {
    return sortedLists;
  }

}
