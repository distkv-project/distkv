package org.dst.test.core.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;

public interface KVSMultiThreadTestBase<T> {
  KVStore KV_STORE = new KVStoreImpl();
  int THREAD_COUNT = 10;
  int DATA_COUNT = 10000;
  List<String> LIST_KEY = new ArrayList<>();

  Map<String, T> dummyDataForThread();

  void test();
}
