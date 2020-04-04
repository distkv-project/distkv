package com.distkv.pine;

import com.distkv.pine.api.Pine;
import com.distkv.pine.components.cache.PineCache;
import com.distkv.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PineCacheTest extends BaseTestSupplier {

  @Test
  public void testCache() {
    Pine.init(getListeningAddress());
    PineCache cache = Pine.newCache();
    cache.newItem("sss",1000);
    cache.newItem("adc", 2000);
    cache.expire("adc");

    {
      cache.newItem("1");
      Assert.assertFalse(cache.expire("1"));

    }
  }
}
