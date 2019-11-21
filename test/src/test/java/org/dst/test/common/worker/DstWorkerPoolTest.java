package org.dst.test.common.worker;

import org.dst.common.worker.DstWorkerPool;
import org.testng.annotations.Test;

public class DstWorkerPoolTest {
  @Test
  public void test() {

    DstWorkerPool dstWorkerPool = new DstWorkerPool();
    for (int i = 0; i < 200; i++) {
      dstWorkerPool.exec(new Thread(() -> {
        try {
          System.out.println("Exec-Thread-" + Thread.currentThread().getId());
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      })
      );
    }
  }

}
