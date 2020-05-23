package com.distkv.storageengine;

import java.util.HashMap;

public class StorageEngineDefaultHashImpl implements StorageEngine {

  private HashMap<String, String> store = new HashMap<>();


  @Override
  public void put(String key, String value) {
    store.put(key, value);
  }

  @Override
  public String get(String key) {
    return store.get(key);
  }

}
