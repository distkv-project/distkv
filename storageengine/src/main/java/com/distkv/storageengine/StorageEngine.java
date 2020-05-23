package com.distkv.storageengine;

public interface StorageEngine {

  void put(String key, String value);

  String get(String key);

}
