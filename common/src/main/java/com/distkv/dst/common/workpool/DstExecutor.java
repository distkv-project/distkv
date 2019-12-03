package com.distkv.dst.common.workpool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface DstExecutor {

  /**
   * Executes the given task at some time in the future.  The task may
   * execute in a new thread, in a pooled thread, or in the calling
   * thread, at the discretion of the Executor implementation.
   *
   * @param runnable the runnable task
   */
  void exec(Runnable runnable);
  /**
   * Initiates an orderly shutdown in which previously submitted
   * tasks are executed, but no new tasks will be accepted.
   * Invocation has no additional effect if already shut down.
   */
  void shutdown();

  /**
   * Returns true if this executor has been shut down.
   *
   * @return true if this executor has been shut down
   */
  boolean isShutdown();

  /**
   * Submits a value-returning task for execution and returns a
   * Future representing the pending results of the task. The
   * Future's get method will return the task's result upon
   * successful completion.
   *
   * @param task the task to submit
   * @param <T> the type of the task's result
   * @return a Future representing pending completion of the task
   *
   */
  <T> Future<T> submit(Callable<T> task);
}
