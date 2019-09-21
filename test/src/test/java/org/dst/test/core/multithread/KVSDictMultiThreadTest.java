package org.dst.test.core.multithread;

import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.ImmutableMap;
import org.dst.test.benchmark.core.Benchmark;
import org.testng.Assert;

public class KVSDictMultiThreadTest implements KVSMultiThreadTestBase {

  @Override
  public Map<String, Map<String, String>> dummyDataForThread() {
    HashMap<String, Map<String, String>> strMap = new HashMap<>();
    for (int i = 0; i < DATA_COUNT; i++) {
      long id = Thread.currentThread().getId();
      synchronized (this) {
        strMap.put("key:" + id + ":" + i, ImmutableMap.of("K1", "V1"));
        LIST_KEY.add("key:" + id + ":" + i);
      }
    }

    return strMap;
  }

  @Override
  public void test() {
    Benchmark benchmark = new Benchmark(THREAD_COUNT);
    benchmark.setTest(() -> {
      Map<String, Map<String, String>> strMap = dummyDataForThread();
      for (String key : strMap.keySet()) {
        Map<String, String> value = strMap.get(key);
        KV_STORE.dicts().put(key, value);
      }
    });
    benchmark.run();
    for (int i = 0; i < LIST_KEY.size(); i++) {
      String key = LIST_KEY.get(i);
      Assert.assertEquals(ImmutableMap.of("K1", "V1"), KV_STORE.dicts().get(key));
    }
  }
}
