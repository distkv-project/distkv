package org.dst.test.core.multithread;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.google.common.collect.ImmutableSet;
import org.dst.test.benchmark.core.Benchmark;
import org.testng.Assert;

public class KVSSetMultiThreadTest implements KVSMultiThreadTestBase {
  @Override
  public Map<String, Set<String>> dummyDataForThread() {
    HashMap<String, Set<String>> strMap = new HashMap<>();
    for (int i = 0; i < DATA_COUNT; i++) {
      long id = Thread.currentThread().getId();
      synchronized (this) {
        strMap.put("key:" + id + ":" + i, ImmutableSet.of("A", "B", "C"));
        LIST_KEY.add("key:" + id + ":" + i);
      }
    }

    return strMap;
  }

  @Override
  public void test() {
    Benchmark benchmark = new Benchmark(THREAD_COUNT);
    benchmark.setTest(() -> {
      Map<String, Set<String>> strMap = dummyDataForThread();
      for (String key : strMap.keySet()) {
        Set<String> value = strMap.get(key);
        KV_STORE.sets().put(key, value);
      }
    });
    benchmark.run();
    for (int i = 0; i < LIST_KEY.size(); i++) {
      String key = LIST_KEY.get(i);
      Assert.assertEquals(ImmutableSet.of("A", "B", "C"), KV_STORE.sets().get(key));
    }
  }
}
