package org.dst.rpc.core.utils;

import java.util.concurrent.atomic.LongAdder;

/**
 * 创建一个全局唯一的requestId
 */
public class RequestIdGenerator {

  private static LongAdder curId = new LongAdder();
  private static long MAX_PER_ROUND = 1 << 24;

  static {
    curId.increment(); // 从1开始
  }

  public static long next() {
    if (curId.longValue() >= MAX_PER_ROUND) {
      synchronized (RequestIdGenerator.class) {
        if (curId.longValue() >= MAX_PER_ROUND) {
          curId.reset();
        }
      }
    }
    curId.increment();
    return curId.longValue();
  }

  public static void main(String[] args) {
    System.out.println(MAX_PER_ROUND);
  }

}
