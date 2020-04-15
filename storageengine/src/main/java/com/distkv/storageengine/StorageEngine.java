package com.distkv.storageengine;

public class StorageEngine {

  private long nativePointer = 0;

  static {
    System.loadLibrary("java_storage_engine");
  }

  public StorageEngine() {
    nativePointer = nativeInit();
  }

  public void put(String key, String value) {
    nativePut(nativePointer,key, value);
  }

  public String get(String key) {
    return nativeGet(nativePointer, key);
  }

  private native long nativeInit();

  private native long nativeDestory(long nativePointer);

  private native void nativePut(long nativePointer, String key, String value);

  private native String nativeGet(long nativePointer, String key);

}
