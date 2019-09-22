package org.dst.test.core.multithread;

import java.util.ArrayList;
import java.util.List;
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

  protected List<String> KEYS =new ArrayList<String>();
  /**
   * Provide test data for multithreaded testing
   *
   * @return dummy data
   */
  abstract Map<String, T> dummyDataForThread();

  /**
   * The goal of this method is to get all the keys
   * that need to be added to the memory,
   * to test whether the data is added correctly under multithreading.
   *
   * @return Collection of keys
   */
  abstract List<String> getAllKeys(Map<String, List<String>> dummyData);

  /**
   * Execute testing ,and multithreaded tests may throw InterruptedException
   */
  abstract void test() throws InterruptedException;
}
