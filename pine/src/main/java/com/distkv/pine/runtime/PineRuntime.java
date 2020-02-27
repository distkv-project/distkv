package com.distkv.pine.runtime;

import com.distkv.client.DefaultDistkvClient;
import com.distkv.client.DistkvClient;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.PipeRuntimeShutdownFailedException;
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
    try {
      distkvClient.disconnect();
    } catch (DistkvException e) {
      throw new PipeRuntimeShutdownFailedException(
          String.format("Failed shutdown the client : %s", e.getMessage()));
    }
  }

  public PineTopper newTopper() {
    return new PineTopperImpl(distkvClient);
  }

}
