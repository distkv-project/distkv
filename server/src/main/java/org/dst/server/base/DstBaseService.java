package org.dst.server.base;

import org.dst.core.KVStore;

public class DstBaseService {
  public KVStore store;

  public DstBaseService(KVStore store) {
    this.store = store;
  }
}
