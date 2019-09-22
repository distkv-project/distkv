package org.dst.test.core.multithread;

import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class KVSMultiThreadTestBase<T> {
  /**
   * Storage of all data types
   */
  static final KVStore KV_STORE = new KVStoreImpl();

  /**
   * Number of thread tests
   */
  static final int THREAD_COUNT = 10;

  /**
   * Single thread test data volume
   */
  static final int DATA_COUNT = 100000;

  protected List<String> targetKeys = new ArrayList<String>();

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
  protected void storeTempKeys(Map<String, T> dummyData) {
    Set<String> key = dummyData.keySet();
    synchronized (this) {
      targetKeys.addAll(key);
    }
  }

  /**
   * Execute testing ,and multithreaded tests may throw InterruptedException
   */
  abstract void test() throws InterruptedException;
}
