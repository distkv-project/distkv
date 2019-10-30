package org.dst.rpc.core.common;


public class Void {

  private Void() {
  }

  public static Void getInstance() {
    return InstanceHolder.aVoid;
  }

  private static class InstanceHolder {

    private static Void aVoid = new Void();
  }

}
