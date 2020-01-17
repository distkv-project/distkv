package com.distkv.server.base;

import com.distkv.core.KVStore;

public class DstBaseService {
  private KVStore store;

  public DstBaseService(KVStore store) {
    this.store = store;
  }

  protected KVStore getStore() {
    return store;
  }
}
