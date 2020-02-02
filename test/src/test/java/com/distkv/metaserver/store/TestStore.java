package com.distkv.metaserver.store;

import com.distkv.server.metaserver.server.statemachine.DataSpace;
import com.distkv.server.metaserver.server.statemachine.NameSpace;
import com.distkv.server.metaserver.server.statemachine.SpaceInterface;
import org.testng.annotations.Test;

public class TestStore {

  @Test
  public void testStore() {
    NameSpace store = new NameSpace();
    store.getCurrentMap().put("first", new DataSpace("asdas"));
    store.getCurrentMap().put("second", new DataSpace("kkkkk"));
    store.getCurrentMap().put("Third", new DataSpace("ggg"));

    NameSpace space = new NameSpace();
    space.getCurrentMap().put("dasd", new DataSpace("nhnnn"));
    store.getCurrentMap().put("nmsl", space);

    SpaceInterface spi = store.getCurrentMap().get("nmsl");
    createPath("dasd&ssss", store);
    putKVByPath("dasd&ssss", "uuu", "xxx", store);
    System.out.println(getValueByPath("dasd&ssss&uuu", store));
  }

  public static String getValueByPath(String path, NameSpace store) {
    try {
      String[] names = path.split("&");
      SpaceInterface spi = store;
      for (int i = 0; i < names.length; i++) {
        spi = ((NameSpace) spi).getCurrentMap().get(names[i]);
      }
      return ((DataSpace) spi).getValue();
    } catch (Exception e) {
      return "Error";
    }
  }

  public static void createPath(String path, NameSpace store) {
    String[] names = path.split("&");
    SpaceInterface spi = store;
    for (int i = 0; i < names.length; i++) {
      SpaceInterface tmpSpi = ((NameSpace) spi).getCurrentMap().get(names[i]);
      if (tmpSpi == null) {
        SpaceInterface ttSpi = new NameSpace();
        ((NameSpace) spi).getCurrentMap().put(names[i], ttSpi);
        spi = ttSpi;
      } else {
        spi = tmpSpi;
      }
    }
  }

  public static void putKVByPath(String path, String key, String value, NameSpace store) {
    String[] names = path.split("&");
    SpaceInterface spi = store;
    for (int i = 0; i < names.length; i++) {
      spi = ((NameSpace) spi).getCurrentMap().get(names[i]);
    }
    ((NameSpace) spi).getCurrentMap().put(key, new DataSpace(value));
  }
}
