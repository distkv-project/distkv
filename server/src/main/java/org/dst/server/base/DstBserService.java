package org.dst.server.base;

import org.dst.core.KVStore;

public class DstBserService {
  public KVStore store;

  public DstBserService(KVStore store) {
    this.store = store;
  }
}
