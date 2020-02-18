package com.distkv.pine.api;

import com.distkv.pine.runtime.PineRuntime;

public class Pine {

  private static PineRuntime runtime;

  public static void init() {
    runtime = new PineRuntime();
    runtime.init();
  }

  public static void shutdown() {
    if (runtime != null) {
      runtime.shutdown();
    }
  }

  public static PineTopper getTopper() {
    return runtime.getTopper();
  }

}
