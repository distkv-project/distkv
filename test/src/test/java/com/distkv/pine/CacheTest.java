package com.distkv.pine;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.RuntimeUtil;
import com.distkv.pine.api.Pine;
import com.distkv.pine.components.cache.PineCache;
import com.distkv.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(singleThreaded = true)
public class CacheTest extends BaseTestSupplier {

  @Test
  public void testCache() {
    Pine.init(getListeningAddress());

    PineCache cache = Pine.newCache((long) 1000);
    cache.newItem("zhangsan");
    cache.newItem("lisi");
    //test  key not expired
    Assert.assertFalse(cache.isExpired("zhangsan"));

    //test key expired
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        return cache.isExpired("zhangsan");
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 3 * 1000);
    Assert.assertTrue(result);
    Pine.shutdown();
  }

}
