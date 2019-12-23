package com.distkv.dst.test.benchmark;

import com.distkv.dst.asyncclient.DefaultAsyncClient;
import com.distkv.dst.asyncclient.DstAsyncClient;
import com.distkv.dst.test.supplier.TestUtil;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class SimpleBenchmark {
  private static final String ADDRESS = "list://127.0.0.1:8082";
  private static final String value = randomString(5);
  private static final Integer THREAD_COUNT = 2;
  private static final Integer LOOP_COUNT = 50000;

  public static void main(String[] args) throws Exception {
    // DST start server
    TestUtil.startRpcServer(8082);
    System.out.println("============Benchmark Starting ......=============");
    final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
    List<Long> costTimes = new ArrayList<>();
    for (int i = 0; i < THREAD_COUNT; i++) {
      DstAsyncClient client = new DefaultAsyncClient(ADDRESS);
      new Thread(() -> {
        long begin = System.currentTimeMillis();
        for (int j = 0; j < LOOP_COUNT; j++) {
          client.strs().put("k1" + j, value);
        }
        long costTime = System.currentTimeMillis() - begin;
        costTimes.add(costTime);
        latch.countDown();
        client.disconnect();
      }).start();
    }
    latch.await();

    double avgTime = getAvgTime(costTimes);
    long minTime = Collections.min(costTimes);
    long maxTime = Collections.max(costTimes);
    DecimalFormat df = new DecimalFormat("#.00");
    String tps = df.format(LOOP_COUNT / avgTime);

    System.out.println(
        "=========================Execute Result==============================\n" +
            "API                          str.put()\n" +
            "t_count                      " + THREAD_COUNT + "\n" +
            "c_request                    " + LOOP_COUNT + "\n" +
            "cost_avg                     " + avgTime + " ms\n" +
            "cost_min                     " + minTime + " ms\n" +
            "cost_max                     " + maxTime + " ms\n" +
            "tps                          " + tps + " k/s\n" +
            "=======================================================================");
    //DST stop server
    TestUtil.stopRpcServer();
  }

  /**
   * Get a random string pass by a length
   *
   * @param x Length of Random String
   * @return Random String
   */
  public static String randomString(int x) {
    String str = "1234567890abcdefghijklmnopqrstuvwxyz";
    StringBuffer stringBuffer = new StringBuffer();
    Random random = new Random();
    for (int i = 0; i < x; i++) {
      stringBuffer.append(str.charAt(random.nextInt(str.length())));
    }
    return stringBuffer.toString();
  }

  /**
   * Get average time
   *
   * @param costTimes List of cost time
   * @return Average time
   */
  private static double getAvgTime(List<Long> costTimes) {
    OptionalDouble optionalDouble = costTimes.stream().mapToLong(Long::longValue).average();
    return optionalDouble.getAsDouble();
  }

}
