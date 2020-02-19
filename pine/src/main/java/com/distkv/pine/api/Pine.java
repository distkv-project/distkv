package com.distkv.pine.api;

import com.distkv.pine.components.topper.PineTopper;
import com.distkv.pine.runtime.PineRuntime;

/**
 * The API class of Pine.
 */
public class Pine {

  /**
   * The Pine runtime.
   */
  private static PineRuntime runtime;

  /**
   * Initial the Pine.
   *
   * @param address The address of distkv server to connect.
   */
  public static void init(String address) {
    runtime = new PineRuntime();
    runtime.init(address);
  }

  /**
   * Shutdown the Pine.
   */
  public static void shutdown() {
    if (runtime != null) {
      runtime.shutdown();
      runtime = null;
    }
  }

  public static PineTopper newTopper() {
    return runtime.newTopper();
  }

}
