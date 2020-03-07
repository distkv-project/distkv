package com.distkv.pine;

import com.distkv.pine.api.Pine;
import com.distkv.pine.components.liker.PineLiker;
import com.distkv.supplier.BaseTestSupplier;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LikerTest extends BaseTestSupplier {

  @Test
  public void testLiker() {
    Pine.init(listeningAddress);

    PineLiker liker = Pine.newLiker();
    liker.getTopic("nihao").likesFrom("zhangsan");
    liker.getTopic("nihao").likesFrom("zhangsan");
    Assert.assertEquals(liker.getTopic("nihao").count(), 1);
    liker.getTopic("nihao").likesFrom("lisi");
    Assert.assertEquals(liker.getTopic("nihao").count(), 2);

    Assert.assertTrue(liker.getTopic("nihao").unLikesFrom("zhangsan"));
    Assert.assertFalse(liker.getTopic("nihao").unLikesFrom("lisi4"));
    Assert.assertEquals(liker.getTopic("nihao").count(), 1);

    Pine.shutdown();
  }
}
