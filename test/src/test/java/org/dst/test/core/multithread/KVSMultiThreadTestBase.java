package org.dst.test.core.multithread;

import java.util.Map;

public abstract class KVSMultiThreadTestBase<T> {

  /**
   * Number of thread tests
   */
  static final int THREAD_COUNT = 10;

  /**
   * Single thread test data volume
   */
  static final int DATA_COUNT = 10000;

  /**
   * Provide test data for multithreaded testing
   *
   * @return dummy data
   */
  abstract Map<String, T> dummyDataForThread();

  /**
   * Execute testing ,and multithreaded tests may throw InterruptedException
   */
  abstract void test() throws InterruptedException;
}
