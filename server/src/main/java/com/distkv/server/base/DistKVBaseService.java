package com.distkv.server.base;

import com.distkv.core.KVStore;

public class DistKVBaseService {
  private KVStore store;

  public DistKVBaseService(KVStore store) {
    this.store = store;
  }

  protected KVStore getStore() {
    return store;
  }
}
