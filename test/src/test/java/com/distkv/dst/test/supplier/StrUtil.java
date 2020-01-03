package com.distkv.dst.test.supplier;

import java.util.Random;

public class StrUtil {

  /**
   * Get a random string pass by a length
   *
   * @param x Length of Random String
   * @return Random String
   */
  public static String randomString(int x) {
    String str = "1234567890abcdefghijklmnopqrstuvwxyz";
    StringBuilder stringBuilder = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < x; i++) {
      stringBuilder.append(str.charAt(random.nextInt(str.length())));
    }
    return stringBuilder.toString();
  }

}
