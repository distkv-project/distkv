package com.distkv.core;

import com.distkv.core.concepts.DistkvSets;
import com.distkv.core.concepts.DistkvSortedLists;
import com.distkv.core.concepts.DistkvStrings;
import com.distkv.core.concepts.DistkvLists;
import com.distkv.core.concepts.DistkvDicts;

/**
 * The api of dst store.
 */
public interface KVStore {

  DistkvStrings strs();

  DistkvLists lists();

  DistkvSets sets();

  DistkvDicts dicts();

  DistkvSortedLists sortLists();

}
