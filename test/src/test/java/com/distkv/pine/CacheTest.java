package com.distkv.pine;

import com.distkv.common.exception.KeyNotFoundException;
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
    //cache.newItem("zhangsan");
    cache.newItems("zhangsan");
    Assert.assertEquals(cache.getItem("nihao"),"zhangsan");
    Thread.sleep((long)6000);
    Assert.assertThrows(KeyNotFoundException.class, () -> cache.getItem("zhangsan"));
    Pine.shutdown();
  }

}
