package com.distkv.pine.runtime;

import com.distkv.client.DistkvClient;
import com.distkv.pine.components.PineTopper;
import com.distkv.pine.components.PineTopperImpl;

public class PineRuntime {

  private DistkvClient client;

  public void init() {

  }

  public void shutdown() {

  }

  public PineTopper newTopper() {
    return new PineTopperImpl();
  }

}
