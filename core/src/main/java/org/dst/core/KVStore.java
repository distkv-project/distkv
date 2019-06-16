package org.dst.core;

import org.dst.core.operatorset.*;

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
