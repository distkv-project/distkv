package com.distkv.pine.runtime;

import com.distkv.pine.components.PineTopper;
import com.distkv.pine.components.PineTopperImpl;

public class PineRuntime {

  public void init() {

  }

  public void shutdown() {

  }

  public PineTopper getTopper() {
    return new PineTopperImpl();
  }

}
