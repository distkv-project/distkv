package com.distkv.common.entity.sortedList;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class SlistEntityTest {

  @Test
  public void testMain() {
    List<SlistEntity> slist = new ArrayList<>();
    for (int i = 100; i < 200; i++) {
      SlistEntity slistEntity = new SlistEntity(
          String.valueOf(i), i);
      slist.add(slistEntity);
    }
    Set<SlistEntity> set = new HashSet<>(slist);
    Assert.assertEquals(set.size(), 100);
    Assert.assertEquals(set.contains(new SlistEntity("123", 123)), true);
    Assert.assertEquals(set.contains(new SlistEntity("123", 345)), true);
    Assert.assertEquals(set.contains(new SlistEntity("200", 200)), false);

    Collections.sort(slist);
    for (int i = 1; i < slist.size(); i++) {
      Assert.assertEquals(slist.get(i).getScore() <=
          slist.get(i - 1).getScore(), true);
    }

    slist.add(new SlistEntity("19", 199));
    slist.add(new SlistEntity("23", 199));
    Collections.sort(slist);
    Assert.assertEquals(slist.get(0).getMember().equals("19"), true);
    Assert.assertEquals(slist.get(1).getMember().equals("199"), true);
    Assert.assertEquals(slist.get(2).getMember().equals("23"), true);
  }
}
