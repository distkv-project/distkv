package com.distkv.core;

import com.distkv.core.concepts.DistKVSets;
import com.distkv.core.concepts.DstSortedLists;
import com.distkv.core.concepts.DistKVStrings;
import com.distkv.core.concepts.DistKVLists;
import com.distkv.core.concepts.DistKVDicts;

/**
 * The api of dst store.
 */
public interface KVStore {

  DistKVStrings strs();

  DistKVLists lists();

  DistKVSets sets();

  DistKVDicts dicts();

  DstSortedLists sortLists();

}
