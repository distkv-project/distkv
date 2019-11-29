package com.distkv.dst.test.core.multithread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class KVSDictMultiThreadTest extends KVSMultiThreadTestBase<Map<String, String>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(KVSSetMultiThreadTest.class);

  @Override
  public Map<String, Map<String, String>> dummyDataForThread() {
    HashMap<String, Map<String, String>> strMap = new HashMap<>();
    for (int i = 0; i < DATA_COUNT; i++) {
      long id = Thread.currentThread().getId();
      strMap.put("key:" + id + ":" + i, ImmutableMap.of("K1", "V1"));
    }
    return strMap;
  }

  @Test
  @Override
  public void test() throws InterruptedException {
    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < THREAD_COUNT; i++) {
      Thread thread = new Thread(() -> {
        Map<String, Map<String, String>> strMap = dummyDataForThread();
        storeTempKeys(strMap);
        long startTime = System.currentTimeMillis();
        for (String key : strMap.keySet()) {
          KV_STORE.dicts().put(key, strMap.get(key));
        }
        LOGGER.info("Thread-ID-" + Thread.currentThread().getId()
              + "   Cost Time :" + (System.currentTimeMillis() - startTime) + "s");
      });
      threads.add(thread);
      thread.start();
    }
    for (int i = 0; i < THREAD_COUNT; i++) {
      threads.get(i).join();
    }
    // check thread safety
    for (int i = 0; i < targetKeys.size(); i++) {
      String key = targetKeys.get(i);
      Assert.assertEquals(ImmutableMap.of("K1", "V1"), KV_STORE.dicts().get(key));
    }
  }
}
