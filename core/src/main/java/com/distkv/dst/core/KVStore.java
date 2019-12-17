package com.distkv.dst.core;

import com.distkv.dst.core.operatorset.DstDicts;
import com.distkv.dst.core.operatorset.DstLists;
import com.distkv.dst.core.operatorset.DstSets;
import com.distkv.dst.core.operatorset.DstStrings;
import com.distkv.dst.core.operatorset.DstSortedLists;
import com.distkv.dst.core.operatorset.DstTables;

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
