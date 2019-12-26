package com.distkv.dst.test.common.entity.sortedList;

import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class SortedListEntityTest {

  @Test
  public void testMain() {
    List<SortedListEntity> sortedListEntityList = new ArrayList<>();
    for (int i = 100; i < 200; i++) {
      SortedListEntity sortedListEntity = new SortedListEntity(
          String.valueOf(i), i);
      sortedListEntityList.add(sortedListEntity);
    }
    Set<SortedListEntity> set = new HashSet<>(sortedListEntityList);
    Assert.assertEquals(set.size(), 100);
    Assert.assertEquals(set.contains(new SortedListEntity("123", 123)), true);
    Assert.assertEquals(set.contains(new SortedListEntity("123", 345)), true);
    Assert.assertEquals(set.contains(new SortedListEntity("200", 200)), false);

    Collections.sort(sortedListEntityList);
    for (int i = 1; i < sortedListEntityList.size(); i++) {
      Assert.assertEquals(sortedListEntityList.get(i).getScore() <=
          sortedListEntityList.get(i - 1).getScore(), true);
    }
  }
}
