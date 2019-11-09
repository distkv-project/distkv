package org.dst.test.common.queue;

import org.dst.common.queue.DefaultConcurrentQueue;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.CountDownLatch;

public class DefaultConcurrentQueueTest {

  private static final int THREAD_COUNT = 20;
  private static final int DATA_COUNT = 100000;

  @Test
  public void testAdd() throws InterruptedException {
    //multi thread test add()
    DefaultConcurrentQueue<Integer> queue = new DefaultConcurrentQueue<>();
    CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
    for (int i = 0; i < THREAD_COUNT; i++) {
      new Thread(() -> {
        for (int j = 0; j < DATA_COUNT; j++) {
          queue.add(j);
        }
        latch.countDown();
      }).start();
    }
    latch.await();
    int totalCount = queue.size();
    Assert.assertEquals(THREAD_COUNT * DATA_COUNT, totalCount);
  }

  @Test
  public void testOffer() throws InterruptedException {
    //multi thread test offer()
    DefaultConcurrentQueue<Long> queue = new DefaultConcurrentQueue<>();
    CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
    for (int i = 0; i < THREAD_COUNT; i++) {
      new Thread(() -> {
        for (int j = 0; j < DATA_COUNT; j++) {
          queue.offer(System.currentTimeMillis());
        }
        latch.countDown();
      }).start();
    }
    latch.await();
    int totalCount = queue.size();
    Assert.assertEquals(THREAD_COUNT * DATA_COUNT, totalCount);
  }

  @Test
  public void testPoll() {
    DefaultConcurrentQueue<String> queue = new DefaultConcurrentQueue<>();
    queue.add("a");
    queue.add("b");
    queue.add("c");
    Assert.assertEquals(queue.poll(), "a");
    //test remove head element
    Assert.assertEquals(queue.size(), 2);
  }

  @Test
  public void testPeek() {
    DefaultConcurrentQueue<String> queue = new DefaultConcurrentQueue<>();
    queue.add("a");
    queue.add("b");
    queue.add("c");
    Assert.assertEquals(queue.peek(), "a");
    Assert.assertEquals(queue.size(), 3);
  }

  @Test
  public void testRemove() {
    DefaultConcurrentQueue<String> queue = new DefaultConcurrentQueue<>();
    queue.add("a");
    queue.add("b");
    queue.add("c");
    Assert.assertEquals(queue.remove(), "a");
    //test remove head element
    Assert.assertEquals(queue.size(), 2);
  }

  @Test
  public void testIsEmpty() {
    DefaultConcurrentQueue<String> queue = new DefaultConcurrentQueue<>();
    Assert.assertEquals(true, queue.isEmpty());
  }

}
