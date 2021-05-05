package com.distkv.pine;

import com.distkv.common.exception.DistkvException;
import com.distkv.pine.api.Pine;
import com.distkv.pine.components.liker.PineLiker;
import com.distkv.supplier.BaseTestSupplier;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(singleThreaded = true)
public class LikerTest extends BaseTestSupplier {

  @Test
  public void testLiker() {
    Pine.init(getListeningAddress());

    PineLiker liker = Pine.newLiker();
    liker.topic("nihao").likesFrom("zhangsan");
    liker.topic("nihao").likesFrom("zhangsan");
    Assert.assertEquals(liker.topic("nihao").count(), 1);
    liker.topic("nihao").likesFrom("lisi");
    Assert.assertEquals(liker.topic("nihao").count(), 2);

    Assert.assertTrue(liker.topic("nihao").unlikesFrom("zhangsan"));
    Assert.assertFalse(liker.topic("nihao2").unlikesFrom("zhangsan"));
    Assert.assertEquals(liker.topic("nihao").count(), 1);

    Pine.shutdown();
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testLikeeNotFoundException() {
    Pine.init(getListeningAddress());

    PineLiker liker = Pine.newLiker();
    liker.topic("nihao").likesFrom("zhangsan");
    liker.topic("nihao").unlikesFrom("lisi");

    Pine.shutdown();
  }

}
