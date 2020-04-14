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

  @Test
  public void testCache() throws InterruptedException {
    Pine.init(getListeningAddress());

    PineCache cache = Pine.newCache((long) 5000);
    cache.newItem("zhangsan");
    Assert.assertEquals(cache.getItem("nihao"),"zhangsan");
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        cache.getItem("zhangsan");
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

  @Test
  public void testKeyNotFoundException() {
    DistkvClient client = newDistkvClient();
    try {
      client.drop("zhangsan");
    } catch (KeyNotFoundException e) {
      Assert.assertTrue(true);
      return;
    } finally {
      client.disconnect();
    }
    Assert.fail();
  }


}
