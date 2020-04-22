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

    PineCache cache = Pine.newCache((long) 1000);
    cache.newItem("zhangsan");
    cache.newItem("lisi");
    Assert.assertFalse(cache.isExpired("zhangsan"));
    boolean flg = RuntimeUtil.waitForCondition(() -> {
      try {
        cache.isExpired("lisi");
        return true;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 5 * 1000);
    Assert.assertTrue(flg);

   // Assert.assertThrows(KeyNotFoundException.class, () ->  distkvClient.strs().get("wangwu"));

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
