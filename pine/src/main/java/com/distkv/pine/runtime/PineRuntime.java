package com.distkv.pine.runtime;

import com.distkv.client.DefaultDistkvClient;
import com.distkv.client.DistkvClient;
import com.distkv.pine.components.topper.PineTopper;
import com.distkv.pine.components.topper.PineTopperImpl;

public class PineRuntime {


  /**
   * The distkv sync client.
   */
  private DistkvClient distkvClient;

  public void init(String address) {
    distkvClient = new DefaultDistkvClient(address);
  }

  public void shutdown() {

  }

  public PineTopper newTopper() {
    return new PineTopperImpl();
  }

}
