package org.dst.test.core.multithread;


import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KVSListMultiThreadTest extends KVSMultiThreadTestBase<List<String>> {

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

  @Test
  @Override
  public void test() throws InterruptedException {
    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < THREAD_COUNT; i++) {
      Thread thread = new Thread(() -> {
        Map<String, List<String>> strMap = dummyDataForThread();
        storeTempKeys(strMap);
        long startTime = System.currentTimeMillis();
        for (String key : strMap.keySet()) {
          KV_STORE.lists().put(key, strMap.get(key));
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
      Assert.assertEquals(ImmutableList.of("A", "B", "C"), KV_STORE.lists().get(key));
    }
  }
}
