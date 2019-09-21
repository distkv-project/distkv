package org.dst.test.core.multithread;

import java.util.HashMap;
import java.util.Map;
import org.dst.test.benchmark.core.Benchmark;
import org.testng.Assert;

public class KVSStrMultiThreadTest implements KVSMultiThreadTestBase {
  private static final String TEST_STRING = "DST";

  @Override
  public Map<String, String> dummyDataForThread() {
    HashMap<String, String> strMap = new HashMap<>();
    for (int i = 0; i < DATA_COUNT; i++) {
      long id = Thread.currentThread().getId();
      synchronized (this) {
        strMap.put("key:" + id + ":" + i, TEST_STRING);
        LIST_KEY.add("key:" + id + ":" + i);
      }
    }

    return strMap;
  }

  @Override
  public void test() {
    Benchmark benchmark = new Benchmark(THREAD_COUNT);
    benchmark.setTest(() -> {
      Map<String, String> strMap = dummyDataForThread();
      for (String key : strMap.keySet()) {
        String value = strMap.get(key);
        KV_STORE.strs().put(key, value);
      }
    });
    benchmark.run();
    for (int i = 0; i < LIST_KEY.size(); i++) {
      String key = LIST_KEY.get(i);
      Assert.assertEquals(TEST_STRING, KV_STORE.strs().get(key));
    }
  }
}
