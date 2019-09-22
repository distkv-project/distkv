package org.dst.test.core.multithread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.common.collect.ImmutableList;
import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class KVSListMultiThreadTest extends KVSMultiThreadTestBase {

  private static final Logger LOGGER = LoggerFactory.getLogger(KVSSetMultiThreadTest.class);

  @Override
  public Map<String, List<String>> dummyDataForThread() {
    HashMap<String, List<String>> strMap = new HashMap<>();
    for (int i = 0; i < DATA_COUNT; i++) {
      long id = Thread.currentThread().getId();
        strMap.put("key:" + id + ":" + i, ImmutableList.of("A", "B", "C"));
    }
    return strMap;
  }

  @Override
  List<String> getAllKeys(Map dummyData) {
    return null;
  }

  @Override
  public void test() throws InterruptedException {
    KVStore kvStore = new KVStoreImpl();
    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < THREAD_COUNT; i++) {
      Thread thread = new Thread(() -> {
        Map<String, List<String>> strMap = dummyDataForThread();
        getAllKeys(strMap);
        long startTime = System.currentTimeMillis();
        for (String key : strMap.keySet()) {
          kvStore.lists().put(key, strMap.get(key));
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
    for (int i = 0; i < KEYS.size(); i++) {
      Object o = KEYS.get(i);
      Assert.assertEquals(ImmutableList.of("A", "B", "C"), kvStore.lists().get(key));
    }
  }
}
