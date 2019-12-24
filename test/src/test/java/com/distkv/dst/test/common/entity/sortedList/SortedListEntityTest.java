package com.distkv.dst.test.common.entity.sortedList;

import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class SortedListEntityTest {

  @Test
  public void testMain() {
    SortedListEntity sortedListEntity = new SortedListEntity("123", 123);
    SortedListEntity sortedListEntity1 = new SortedListEntity("345", 456);
    Set<SortedListEntity> set = new HashSet<>();
    set.add(sortedListEntity);
    set.add(sortedListEntity1);
    System.out.println(set.size());
    System.out.println(set.contains(new SortedListEntity("123", 456)));
  }

}
