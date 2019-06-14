package org.dst.core;

/**
 * The api of kv-store.
 */
import org.dst.core.operatorset.DstString;

interface KVStore {

  DstString str();

  //ListOperatorSet list();

  //SetOperatorSet set();

  //DictOperatorSet dict();

  //TableOperatorSet table();

}
