package com.distkv.server.persistence;

import com.distkv.core.concepts.DistkvValue;
import com.distkv.server.storeserver.persistence.DistkvDB;
import com.distkv.server.storeserver.persistence.ValueType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DistkvDBTest {

  //For test
  private static final Map<String, DistkvValue> kvStore = new HashMap<>();

  //For test
  static {
    //String
    kvStore.put("k1", new DistkvValue(ValueType.STRING, "v1111"));
    kvStore.put("k2", new DistkvValue(ValueType.STRING, "v2222"));
    kvStore.put("k3", new DistkvValue(ValueType.STRING, "v3333"));
    kvStore.put("k4", new DistkvValue(ValueType.STRING, "v4444"));
    kvStore.put("k5", new DistkvValue(ValueType.STRING, "v5555"));
    //List
    kvStore.put("k6", new DistkvValue(ValueType.LIST, Arrays.asList("list1","list2","list3")));
    kvStore.put("k7", new DistkvValue(ValueType.LIST, Arrays.asList("list4","list5","list6")));
  }

  public static void main(String[] args) throws Exception {
    DistkvDB distkvDB = new DistkvDB();
    distkvDB.save(kvStore);
  }


}
