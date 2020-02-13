package com.distkv.common.utils;

import com.distkv.common.exception.DistkvException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureUtils {

  /**
   * Get the object from the given future. This will throw DstException.
   */
  public static <T> T get(Future<T> future) throws DistkvException {
    try {
      return future.get();
    } catch (ExecutionException | InterruptedException e) {
      throw new DistkvException("Failed to get object from future.", e);
    }
  }

  /**
   * Create a completable future object with the completed value.
   */
  public static <T> CompletableFuture<T> newCompletableFuture(T value) {
    CompletableFuture<T> completableFuture = new CompletableFuture<>();
    completableFuture.complete(value);
    return completableFuture;
  }

}
