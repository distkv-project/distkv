package com.distkv.core;

import com.distkv.core.concepts.DstSets;
import com.distkv.core.concepts.DstSortedLists;
import com.distkv.core.concepts.DstStrings;
import com.distkv.core.concepts.DstLists;
import com.distkv.core.concepts.DstDicts;
import com.distkv.core.concepts.DstTables;

/**
 * The api of dst store.
 */
public interface KVStore {

  DstStrings strs();

  DstLists lists();

  DstSets sets();

  DstDicts dicts();

  DstSortedLists sortLists();

  DstTables tables();

}
