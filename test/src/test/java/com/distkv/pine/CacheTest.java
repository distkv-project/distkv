package com.distkv.pine;

import com.distkv.client.DistkvClient;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.RuntimeUtil;
import com.distkv.pine.api.Pine;
import com.distkv.pine.components.cache.PineCache;
import com.distkv.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CacheTest extends BaseTestSupplier {

  private DistkvClient distkvClient;

  @Test
  public void testCache() throws InterruptedException {
    Pine.init(getListeningAddress());

    PineCache cache = Pine.newCache((long) 1);
    cache.newItem("zhangsan");
    cache.newItem("lisi");
    Thread.sleep(2000);
    Assert.assertFalse(cache.isExpired("zhangsan"));
    Assert.assertThrows(KeyNotFoundException.class, () ->  distkvClient.strs().get("wangwu"));
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {

        return true;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 6 * 1000);
    Assert.assertTrue(result);

    boolean expiredIf = RuntimeUtil.waitForCondition(() -> {
      try {
        cache.isExpired("zhangsan");
        return true;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 5 * 1000);
    Assert.assertTrue(expiredIf);

    Pine.shutdown();
  }

}
