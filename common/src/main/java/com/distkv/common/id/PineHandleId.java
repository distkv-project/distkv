package com.distkv.common.id;

import com.distkv.common.utils.HexadecimalUtil;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class PineHandleId implements Serializable {

  private static final long serialVersionUID = -7937063292434146181L;

  public static final int LENGTH = 16;

  private int hashCodeCache = 0;

  private byte[] data;

  private PineHandleId(byte[] data) {
    this.data = data;
  }

  public static PineHandleId fromRandom() {
    byte[] data = new byte[LENGTH];
    new Random().nextBytes(data);
    return new PineHandleId(data);
  }

  public String hex() {
    return HexadecimalUtil.parseByte2Hexstr(data).toLowerCase();
  }

  @Override
  public int hashCode() {
    // Lazy evaluation.
    if (hashCodeCache == 0) {
      hashCodeCache = Arrays.hashCode(data);
    }
    return hashCodeCache;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (!this.getClass().equals(obj.getClass())) {
      return false;
    }

    PineHandleId r = (PineHandleId) obj;
    return Arrays.equals(data, r.data);
  }

  @Override
  public String toString() {
    return hex();
  }

}
