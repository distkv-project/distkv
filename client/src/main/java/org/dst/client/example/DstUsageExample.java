package org.dst.client.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.dst.client.DefaultDstClient;

public class DstUsageExample {
  public static void main(String[] args) {
    DefaultDstClient dstClient = new DefaultDstClient("list://127.0.0.1:8082");
    if (dstClient.isConnected()) {
      dstClient.strs().put("k1", "v1");
      dstClient.sets().put("k1", new HashSet<>(Arrays.asList("v1", "v2", "v3", "v3")));
      dstClient.lists().put("k1", new ArrayList<>(Arrays.asList("v1", "v2", "v3")));
      Map<String, String> map = new HashMap<>();
      map.put("k1", "v1");
      map.put("k2", "v2");
      map.put("k3", "v3");
      map.put("k4", "v4");
      dstClient.dicts().put("dict1", map);

      String strResult = dstClient.strs().get("k1");
      Set<String> setResult = dstClient.sets().get("k1");
      List<String> listResult = dstClient.lists().get("k1");
      Map<String, String> mapResult = dstClient.dicts().get("dict1");

      //print String result
      System.out.println("The result of dstClient.strs().get(\"k1\") is: " + strResult);

      //print set result
      System.out.println("The result of dstClient.sets().get(\"k1\") is: " + setResult);

      //print list result
      System.out.println("The result of dstClient.lists().get(\"k1\") is: " + listResult);

      //print dictionary result
      System.out.println("The result of dstClient.dicts().get(\"dict1\") is: " + mapResult);

    }
  }
}
