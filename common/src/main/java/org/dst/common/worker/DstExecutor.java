package org.dst.common.worker;

public interface DstExecutor {

  /**
   * Executes the given task at some time in the future.  The command
   * may execute in a new thread, in a pooled thread, or in the calling
   * thread, at the discretion of the Executor implementation.
   *
   * @param runnable the task
   */
  void exec(Runnable runnable);
}
