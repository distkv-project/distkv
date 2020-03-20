package com.distkv.core;

import com.distkv.core.concepts.DistkvBaseOperation;
import com.distkv.core.concepts.DistkvDicts;
import com.distkv.core.concepts.DistkvInts;
import com.distkv.core.concepts.DistkvLists;
import com.distkv.core.concepts.DistkvSets;
import com.distkv.core.concepts.DistkvSortedLists;

/**
 * The api of distkv store.
 */
public interface KVStore {

  DistkvBaseOperation strs();

  DistkvLists lists();

  DistkvSets sets();

  DistkvDicts dicts();

  DistkvSortedLists sortLists();

  DistkvInts ints();
}
