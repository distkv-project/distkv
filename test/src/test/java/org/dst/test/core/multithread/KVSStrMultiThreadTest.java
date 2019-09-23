package org.dst.test.core.multithread;

import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KVSStrMultiThreadTest extends KVSMultiThreadTestBase<String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(KVSSetMultiThreadTest.class);

  private static final String TEST_STRING = "DST";

  @Override
  public Map<String, String> dummyDataForThread() {
    HashMap<String, String> strMap = new HashMap<>();
    for (int i = 0; i < DATA_COUNT; i++) {
      long id = Thread.currentThread().getId();
      strMap.put("key:" + id + ":" + i, TEST_STRING);
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
        Map<String, String> strMap = dummyDataForThread();
        storeTempKeys(strMap);
        long startTime = System.currentTimeMillis();
        for (String key : strMap.keySet()) {
          kvStore.strs().put(key, strMap.get(key));
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
      Assert.assertEquals(TEST_STRING, kvStore.strs().get(key));
    }
  }
}
