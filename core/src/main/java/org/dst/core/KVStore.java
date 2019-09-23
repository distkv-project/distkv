package org.dst.core;

import org.dst.core.operatorset.DstString;
import org.dst.core.operatorset.DstList;
import org.dst.core.operatorset.DstSet;
import org.dst.core.operatorset.DstDict;
import org.dst.core.operatorset.DstTable;

/**
 * The api of dst store.
 */
public interface KVStore {

  DstString strs();

  DstList lists();

  DstSet sets();

  DstDict dicts();

  DstTable tables();

}
