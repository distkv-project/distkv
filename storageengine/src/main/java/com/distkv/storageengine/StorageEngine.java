package com.distkv.storageengine;

public class StorageEngine {

  static {
    System.loadLibrary("TestJNI");
  }

  public void put(String key, String value) {
    nativePut(key, value);
  }

  public String get(String key) {
    return nativeGet(key);
  }

  private native void nativePut(String key, String value);

  private native String nativeGet(String key);

}
