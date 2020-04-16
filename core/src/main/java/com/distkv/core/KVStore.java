package com.distkv.core;

import com.distkv.core.concepts.DistkvInts;
import com.distkv.core.concepts.DistkvSets;
import com.distkv.core.concepts.DistkvSlists;
import com.distkv.core.concepts.DistkvStrings;
import com.distkv.core.concepts.DistkvLists;
import com.distkv.core.concepts.DistkvDicts;

/**
 * The api of distkv store.
 */
public interface KVStore {

  boolean drop(String key);

  boolean exists(String key);

  DistkvStrings strs();

  DistkvLists lists();

  DistkvSets sets();

  DistkvDicts dicts();

  DistkvSlists sortLists();

  DistkvInts ints();
}
