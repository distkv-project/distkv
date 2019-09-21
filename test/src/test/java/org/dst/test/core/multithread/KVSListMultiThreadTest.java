package org.dst.test.core.multithread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.common.collect.ImmutableList;
import org.dst.test.benchmark.core.Benchmark;
import org.testng.Assert;

public class KVSListMultiThreadTest implements KVSMultiThreadTestBase {

  @Override
  public Map<String, List<String>> dummyDataForThread() {
    HashMap<String, List<String>> strMap = new HashMap<>();
    for (int i = 0; i < DATA_COUNT; i++) {
      long id = Thread.currentThread().getId();
      synchronized (this) {
        strMap.put("key:" + id + ":" + i, ImmutableList.of("A", "B", "C"));
        LIST_KEY.add("key:" + id + ":" + i);
      }
    }

    return strMap;
  }

  @Override
  public void test() {
    Benchmark benchmark = new Benchmark(THREAD_COUNT);
    benchmark.setTest(() -> {
      Map<String, List<String>> strMap = dummyDataForThread();
      for (String key : strMap.keySet()) {
        List<String> value = strMap.get(key);
        KV_STORE.lists().put(key, value);
      }
    });
    benchmark.run();
    for (int i = 0; i < LIST_KEY.size(); i++) {
      String key = LIST_KEY.get(i);
      Assert.assertEquals(ImmutableList.of("A", "B", "C"), KV_STORE.lists().get(key));
    }
  }
}
