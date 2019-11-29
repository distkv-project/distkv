package com.distkv.dst.core;

import com.distkv.dst.core.operatorset.DstDict;
import com.distkv.dst.core.operatorset.DstList;
import com.distkv.dst.core.operatorset.DstSet;
import com.distkv.dst.core.operatorset.DstString;
import com.distkv.dst.core.operatorset.DstSortedList;
import com.distkv.dst.core.operatorset.DstTable;

/**
 * The api of dst store.
 */
public interface KVStore {

  DstString strs();

  DstList lists();

  DstSet sets();

  DstDict dicts();

  DstSortedList sortLists();

  DstTable tables();

}
