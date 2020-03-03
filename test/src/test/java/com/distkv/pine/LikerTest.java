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
    PineLiker liker = Pine.newLiker("zx", "nihao");

    Assert.assertEquals(liker.count(), 0);

    liker.likesFrom("zx1");
    liker.likesFrom("zx2");
    liker.likesFrom("zx3");
    Assert.assertEquals(liker.count(), 3);
    liker.likesFrom("zx3");
    Assert.assertEquals(liker.count(), 2);

    liker.unLikesFrom("zx1");
    Assert.assertEquals(liker.count(), 1);
    liker.unLikesFrom("zx1");
    Assert.assertEquals(liker.count(), 2);

    Pine.shutdown();
  }
}
