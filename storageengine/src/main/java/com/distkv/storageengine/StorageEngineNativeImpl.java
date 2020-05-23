package com.distkv.storageengine;

import com.distkv.storageengine.utils.JniUtils;

public class StorageEngineNativeImpl implements StorageEngine {

  private long nativePointer = 0;

  static {
    JniUtils.loadLibrary("java_storage_engine");
  }

  public StorageEngineNativeImpl() {
    nativePointer = nativeInit();
  }

  @Override
  public void put(String key, String value) {
    nativePut(nativePointer, key, value);
  }

  @Override
  public String get(String key) {
    return nativeGet(nativePointer, key);
  }

  private native long nativeInit();

  private native long nativeDestory(long nativePointer);

  private native void nativePut(long nativePointer, String key, String value);

  private native String nativeGet(long nativePointer, String key);

}
