package com.distkv.distributed.failuredetection;

import com.distkv.supplier.DmetaTestUtil;
import com.distkv.supplier.MasterSlaveSyncTestUtil;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * The group of tests is test failure detection of store server.
 */
public class StoreServerFailureDetection {

  @Test
  public void testMasterStoreServerDead() throws InterruptedException {
    DmetaTestUtil.startAllDmetaProcess();
    TimeUnit.SECONDS.sleep(3);
    MasterSlaveSyncTestUtil.startAllProcess();
    TimeUnit.SECONDS.sleep(3);
  }
}
