package com.distkv.core;

import com.distkv.core.concepts.DistkvInts;
import com.distkv.core.concepts.DistkvIntsImpl;
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

  // The store proxy of string concept.
  private DistkvStringsImpl strs;

  // The store proxy of int concept.
  private DistkvInts ints;

  // The store proxy of list concept.
  private DistkvListsImpl lists;

  // The store proxy of set concept.
  private DistkvSetsImpl sets;

  // The store proxy of dict concept.
  private DistkvDictsImpl dicts;

  // The store proxy of sorted list concept.
  private DistkvSortedLists sortedLists;

  public KVStoreImpl() {
    DistkvMapInterface<String, Object> distkvKeyValueMap = new DistkvHashMapImpl<>();
    this.strs = new DistkvStringsImpl(distkvKeyValueMap);
    this.lists = new DistkvListsImpl(distkvKeyValueMap);
    this.sets = new DistkvSetsImpl(distkvKeyValueMap);
    this.dicts = new DistkvDictsImpl(distkvKeyValueMap);
    this.sortedLists = new DistkvSortedListsImpl(distkvKeyValueMap);
    this.ints = new DistkvIntsImpl(distkvKeyValueMap);
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

  @Override
  public DistkvInts ints() {
    return ints;
  }

}
