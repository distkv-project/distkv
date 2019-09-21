package org.dst.server.base;

import org.dst.core.KVStore;

public class DstBaseService {
  private KVStore store;

  public DstBaseService(KVStore store) {
    this.store = store;
  }

  protected KVStore getStore() {
    return store;
  }
}
