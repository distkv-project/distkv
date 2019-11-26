package com.distkv.dst.test.core.multithread;

import com.google.common.collect.ImmutableSet;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.core.KVStoreImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;

public class KVSSetMultiThreadTest extends KVSMultiThreadTestBase<Set<String>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(KVSSetMultiThreadTest.class);

  @Override
  public Map<String, Set<String>> dummyDataForThread() {
    HashMap<String, Set<String>> strMap = new HashMap<>();
    for (int i = 0; i < DATA_COUNT; i++) {
      long id = Thread.currentThread().getId();
      strMap.put("key:" + id + ":" + i, ImmutableSet.of("A", "B", "C"));
    }
    return strMap;
  }

  @Test
  @Override
  public void test() throws InterruptedException {
    KVStore kvStore = new KVStoreImpl();
    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < THREAD_COUNT; i++) {
      Thread thread = new Thread(() -> {
        Map<String, Set<String>> strMap = dummyDataForThread();
        storeTempKeys(strMap);
        long startTime = System.currentTimeMillis();
        for (String key : strMap.keySet()) {
          kvStore.sets().put(key, strMap.get(key));
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
      Assert.assertEquals(ImmutableSet.of("A", "B", "C"), kvStore.sets().get(key));
    }
  }
}
