package com.distkv.client.example;

import com.distkv.common.entity.sortedList.SlistEntity;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import com.distkv.client.DefaultDistkvClient;

public class DistkvUsageExample {
  public static void main(String[] args) throws InvalidProtocolBufferException {
    DefaultDistkvClient distkvClient = new DefaultDistkvClient("distkv://127.0.0.1:8082");
    if (distkvClient.isConnected()) {
      distkvClient.strs().put("k1", "v1");
      distkvClient.sets().put("k1", new HashSet<>(Arrays.asList("v1", "v2", "v3", "v3")));
      distkvClient.lists().put("k1", new ArrayList<>(Arrays.asList("v1", "v2", "v3")));
      distkvClient.ints().put("k1", 1);
      Map<String, String> map = new HashMap<>();
      map.put("k1", "v1");
      map.put("k2", "v2");
      map.put("k3", "v3");
      map.put("k4", "v4");
      distkvClient.dicts().put("dict1", map);
      LinkedList<SlistEntity> slist = new LinkedList<>();
      slist.add(new SlistEntity("a", 10));
      slist.add(new SlistEntity("b", 8));
      slist.add(new SlistEntity("c", 6));
      slist.add(new SlistEntity("d", 4));
      distkvClient.slists().put("k1", slist);
      distkvClient.slists().putMember("k1", new SlistEntity("s",100));

      String strResult = distkvClient.strs().get("k1");
      Set<String> setResult = distkvClient.sets().get("k1");
      List<String> listResult = distkvClient.lists().get("k1");
      Map<String, String> mapResult = distkvClient.dicts().get("dict1");
      LinkedList<SlistEntity> slistResult = distkvClient.slists().top("k1", 3);
      int intResult = distkvClient.ints().get("k1");
      distkvClient.ints().incr("k1", -2);
      int intResultAfterIncr = distkvClient.ints().get("k1");

      //print String result
      System.out.println("The result of distkvClient.strs().get(\"k1\") is: " + strResult);

      //print set result
      System.out.println("The result of distkvClient.sets().get(\"k1\") is: " + setResult);

      //print list result
      System.out.println("The result of distkvClient.lists().get(\"k1\") is: " + listResult);

      //print dictionary result
      System.out.println("The result of distkvClient.dicts().get(\"dict1\") is: " + mapResult);

      //print sortedList result
      System.out.println("The top3 entities in the \"k1\" of distkvClient.sortedLists() is: " +
          "{ First: " + slistResult.get(0).getMember() +
          "; Second: " + slistResult.get(1).getMember() +
          "; Third: " + slistResult.get(2).getMember() + "; }");
      System.out.println("In the key \"k1\" of distkvClient.sortedLists(), the member name is "
          + "\"a\", its rank is " + distkvClient.slists().getMember("k1", "a").getSecond()
          + " and its score is " + distkvClient.slists().getMember("k1", "a").getFirst());

      // print ints result
      System.out.println("The result of distkvClient.ints().get(\"k1\") is: " + intResult);
      System.out.println("The result of distkvClient.ints().get(\"k1\") "
          + "after increasing the value -2 is: " + intResultAfterIncr);

      distkvClient.disconnect();
    }
  }
}
