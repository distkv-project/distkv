package com.distkv.pine;

import com.distkv.common.DistkvTuple;
import com.distkv.pine.api.Pine;
import com.distkv.pine.components.topper.PineTopper;
import com.distkv.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Test(singleThreaded = true)
public class TopperTest extends BaseTestSupplier {

  @Test
  public void testTopper() {
    Pine.init(getListeningAddress());
    PineTopper topper = Pine.newTopper();
    topper.addMember("Bob", 80);
    topper.addMember("Allen", 100);
    topper.addMember("Lisa", 90);

    // Assert top1.
    {
      List<DistkvTuple<String, Integer>> items = topper.top(1);
      Assert.assertEquals(items.size(), 1);
      Assert.assertEquals(items.get(0).getFirst(), "Allen");
      Assert.assertEquals((int) items.get(0).getSecond(), 100);
    }

    {
      // Assert top2.
      List<DistkvTuple<String, Integer>> items = topper.top(2);
      Assert.assertEquals(items.size(), 2);
      Assert.assertEquals(items.get(0).getFirst(), "Allen");
      Assert.assertEquals((int) items.get(0).getSecond(), 100);
      Assert.assertEquals(items.get(1).getFirst(), "Lisa");
      Assert.assertEquals((int) items.get(1).getSecond(), 90);
    }

    {
      // Assert top3.
      List<DistkvTuple<String, Integer>> items = topper.top(3);
      Assert.assertEquals(items.size(), 3);
      Assert.assertEquals(items.get(0).getFirst(), "Allen");
      Assert.assertEquals((int) items.get(0).getSecond(), 100);
      Assert.assertEquals(items.get(1).getFirst(), "Lisa");
      Assert.assertEquals((int) items.get(1).getSecond(), 90);
      Assert.assertEquals(items.get(2).getFirst(), "Bob");
      Assert.assertEquals((int) items.get(2).getSecond(), 80);
    }

    {
      // Assert get member.
      Assert.assertEquals(topper.getMember("Allen").getScore(), 100);
      Assert.assertEquals(topper.getMember("Lisa").getScore(), 90);
      Assert.assertEquals(topper.getMember("Bob").getScore(), 80);

      Assert.assertEquals(topper.getMember("Allen").getRankingNum(), 1);
      Assert.assertEquals(topper.getMember("Lisa").getRankingNum(), 2);
      Assert.assertEquals(topper.getMember("Bob").getRankingNum(), 3);
    }
    Pine.shutdown();
  }

}
