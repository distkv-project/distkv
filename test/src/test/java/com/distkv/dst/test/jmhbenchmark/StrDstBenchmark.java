package com.distkv.dst.test.jmhbenchmark;

import com.distkv.dst.asyncclient.DefaultAsyncClient;
import com.distkv.dst.asyncclient.DstAsyncClient;
import com.distkv.dst.client.DefaultDstClient;
import com.distkv.dst.client.DstClient;
import com.distkv.dst.test.supplier.TestUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
public class StrDstBenchmark {

  private DstAsyncClient asyncClient;
  private DstClient client;

  @Setup
  public void init() {
    TestUtil.startRpcServer(8082);

    asyncClient = new DefaultAsyncClient("list://127.0.0.1:8082");
    client = new DefaultDstClient("list://127.0.0.1:8082");

    client.strs().put("k-str-sync", "v-sync");
    asyncClient.strs().put("k-str-async", "v-async");
  }

  @TearDown
  public void close() {
    asyncClient.disconnect();
    client.disconnect();
    TestUtil.stopRpcServer();
  }

  @Benchmark
  public void testSyncGet(Blackhole bh) {
    bh.consume(client.strs().get("k-str-sync"));
  }

  @Benchmark
  public void testAsyncGet(Blackhole bh) {
    bh.consume(asyncClient.strs().get("k-str-async"));
  }

  @Benchmark
  public void testPut() {
    String randomStr = RandomStringUtils.random(5);
    client.strs().put(randomStr, randomStr);
  }

  @Benchmark
  public void testAsyncPut() {
    String randomStr = RandomStringUtils.random(5);
    asyncClient.strs().put(randomStr, randomStr);
  }

}
