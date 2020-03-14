package com.distkv.pine;

import com.distkv.common.exception.DistkvException;
import com.distkv.pine.api.Pine;
import com.distkv.pine.components.liker.PineLiker;
import com.distkv.supplier.BaseTestSupplier;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LikerTest extends BaseTestSupplier {

  @Test
  public void testLiker() {
    Pine.init(getListeningAddress());

    PineLiker liker = Pine.newLiker();
    liker.getTopic("nihao").likesFrom("zhangsan");
    liker.getTopic("nihao").likesFrom("zhangsan");
    Assert.assertEquals(liker.getTopic("nihao").count(), 1);
    liker.getTopic("nihao").likesFrom("lisi");
    Assert.assertEquals(liker.getTopic("nihao").count(), 2);

    Assert.assertTrue(liker.getTopic("nihao").unlikesFrom("zhangsan"));
    Assert.assertFalse(liker.getTopic("nihao2").unlikesFrom("zhangsan"));
    Assert.assertEquals(liker.getTopic("nihao").count(), 1);

    Pine.shutdown();
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testLikeeNotFoundException() {
    Pine.init(getListeningAddress());

    PineLiker liker = Pine.newLiker();
    liker.getTopic("nihao").likesFrom("zhangsan");
    liker.getTopic("nihao").unlikesFrom("lisi");

    Pine.shutdown();
  }

}
