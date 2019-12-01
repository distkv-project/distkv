package com.distkv.dst.common.utils;

import com.distkv.dst.common.exception.DstException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureUtils {

  /**
   * Get the object from the given future. This will throw DstException.
   */
  public static <T> T get(Future<T> future) throws DstException {
    try {
      return future.get();
    } catch (InterruptedException e) {
      throw new DstException("Failed to get object from future.", e);
    } catch (ExecutionException e) {
      throw new DstException("Failed to get object from future.", e);
    }
  }

}
