package com.distkv.dst.core;

import com.distkv.dst.core.concepts.DstDicts;
import com.distkv.dst.core.concepts.DstLists;
import com.distkv.dst.core.concepts.DstSets;
import com.distkv.dst.core.concepts.DstStrings;
import com.distkv.dst.core.concepts.DstSortedLinkedLists;
import com.distkv.dst.core.concepts.DstTables;

/**
 * The api of dst store.
 */
public interface KVStore {

  DstStrings strs();

  DstLists lists();

  DstSets sets();

  DstDicts dicts();

  DstSortedLinkedLists sortLists();

  DstTables tables();

}
