package com.distkv.dst.common.workpool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * Provides default implementations of DstExecutor
 * execution methods. This class implements the submit,
 */
public abstract class AbstractExecutor implements DstExecutor {

  @Override
  public <T> Future<T> submit(Callable<T> task) {
    if (task == null) {
      throw new NullPointerException();
    }
    RunnableFuture<T> futureTask = new FutureTask<>(task);
    exec(futureTask);
    return futureTask;
  }
}
