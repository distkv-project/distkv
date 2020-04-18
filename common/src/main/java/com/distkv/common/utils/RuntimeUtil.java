package com.distkv.common.utils;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class RuntimeUtil {
  private static final int WAIT_INTERVAL_MS = 200;

  public static boolean waitForCondition(Supplier<Boolean> condition, int timeoutMs) {
    int waitTime = 0;
    while (true) {
      if (condition.get()) {
        return true;
      }
      try {
        java.util.concurrent.TimeUnit.MILLISECONDS.sleep(WAIT_INTERVAL_MS);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      waitTime += WAIT_INTERVAL_MS;
      if (waitTime > timeoutMs) {
        break;
      }
    }
    return false;
  }

  /**
   * Sleep timeoutMs milliseconds without exception throwing.
   */
  public static boolean sleepWithoutException(int timeoutMs) {
    try {
      TimeUnit.MILLISECONDS.sleep(timeoutMs);
    } catch (InterruptedException e) {
      return false;
    }
    return true;
  }
}
