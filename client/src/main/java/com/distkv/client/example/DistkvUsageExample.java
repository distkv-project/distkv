package com.distkv.client.example;

import java.util.LinkedList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import com.distkv.client.DefaultDistkvClient;
import com.distkv.common.entity.sortedList.SortedListEntity;

public class DistkvUsageExample {
  public static void main(String[] args) {
    DefaultDistkvClient distkvClient = new DefaultDistkvClient("distkv://127.0.0.1:8082");
    if (distkvClient.isConnected()) {
      distkvClient.strs().put("k1", "v1");
      distkvClient.sets().put("k1", new HashSet<>(Arrays.asList("v1", "v2", "v3", "v3")));
      distkvClient.lists().put("k1", new ArrayList<>(Arrays.asList("v1", "v2", "v3")));
      Map<String, String> map = new HashMap<>();
      map.put("k1", "v1");
      map.put("k2", "v2");
      map.put("k3", "v3");
      map.put("k4", "v4");
      distkvClient.dicts().put("dict1", map);
      LinkedList<SortedListEntity> slist = new LinkedList<>();
      slist.add(new SortedListEntity("a", 10));
      slist.add(new SortedListEntity("b", 8));
      slist.add(new SortedListEntity("c", 6));
      slist.add(new SortedListEntity("d", 4));
      distkvClient.sortedLists().put("k1", slist);
      distkvClient.sortedLists().putMember("k1", new SortedListEntity("s",100));

      String strResult = distkvClient.strs().get("k1");
      Set<String> setResult = distkvClient.sets().get("k1");
      List<String> listResult = distkvClient.lists().get("k1");
      Map<String, String> mapResult = distkvClient.dicts().get("dict1");
      LinkedList<SortedListEntity> slistResult = distkvClient.sortedLists().top("k1", 3);

      //print String result
      System.out.println("The result of distkvClient.strs().get(\"k1\") is: " + strResult);

      //print set result
      System.out.println("The result of distkvClient.sets().get(\"k1\") is: " + setResult);

      //print list result
      System.out.println("The result of distkvClient.lists().get(\"k1\") is: " + listResult);

      //print dictionary result
      System.out.println("The result of distkvClient.dicts().get(\"dict1\") is: " + mapResult);

      //print sortedList result
      System.out.println("The result of distkvClient.sortedLists().top(\"k1\") is: " +
            "{ First: " + slistResult.get(0).getMember() +
            "; Second: " + slistResult.get(1).getMember() +
            "; Third: " + slistResult.get(2).getMember() + "; }");

      distkvClient.disconnect();
    }
  }
}
