package org.dst.core;

import org.dst.core.operatorset.DstString;
import org.dst.core.operatorset.DstList;
import org.dst.core.operatorset.DstSet;
import org.dst.core.operatorset.DstDict;
import org.dst.core.operatorset.DstTable;

/**
 * The api of kv-store.
 */
public interface KVStore {

  DstString str();

  DstList list();

  DstSet set();

  DstDict dict();

  DstTable table();

}
