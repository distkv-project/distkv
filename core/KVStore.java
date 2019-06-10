package org.dst.core;

/**
 * The api of kv-store.
 */
interface KVStore {

  StrOperatorSet str();

  ListOperatorSet list();

  SetOperatorSet set();

  DictOperatorSet dict();

  TableOperatorSet table();

}
