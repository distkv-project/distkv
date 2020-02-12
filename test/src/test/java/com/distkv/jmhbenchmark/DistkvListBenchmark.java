package com.distkv.jmhbenchmark;

import com.distkv.asyncclient.DefaultAsyncClient;
import com.distkv.asyncclient.DistkvAsyncClient;
import com.distkv.client.DefaultDistkvClient;
import com.distkv.client.DistkvClient;
import com.distkv.supplier.TestUtil;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.RandomStringUtils;
import org.openjdk.jmh.infra.Blackhole;
import java.util.List;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

@State(Scope.Thread)
@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
public class DistkvListBenchmark {

  private static final String PROTOCOL = "distkv://127.0.0.1:8082";
  private static final String KEY_LIST_SYNC = "k-list-sync";
  private static final String KEY_LIST_ASYNC = "k-list-async";
  private DistkvAsyncClient asyncClient;
  private DistkvClient client;
  private List<String> dummyData;

  @Setup
  public void init() {
    TestUtil.startRpcServer(8082);

    dummyData = ImmutableList.of(
            RandomStringUtils.random(5),
            RandomStringUtils.random(5),
            RandomStringUtils.random(5));

    asyncClient = new DefaultAsyncClient(PROTOCOL);
    client = new DefaultDistkvClient(PROTOCOL);
    client.lists().put(KEY_LIST_SYNC, dummyData);
    asyncClient.lists().put(KEY_LIST_ASYNC, dummyData);
  }

  @TearDown
  public void close() {
    asyncClient.disconnect();
    client.disconnect();
    TestUtil.stopProcess(TestUtil.getProcess());
  }

  @Benchmark
  public void testSyncGet(Blackhole blackhole) {
    blackhole.consume(client.lists().get(KEY_LIST_SYNC));
  }

  @Benchmark
  public void testAsyncGet(Blackhole blackhole) {
    blackhole.consume(asyncClient.lists().get(KEY_LIST_ASYNC));
  }

  @Benchmark
  public void testPut() {
    String randomStr = RandomStringUtils.random(5);
    client.lists().put(randomStr, dummyData);
  }

  @Benchmark
  public void testAsyncPut() {
    String randomStr = RandomStringUtils.random(5);
    asyncClient.lists().put(randomStr, dummyData);
  }

  @Benchmark
  public void testLPut() {
    client.lists().lput(KEY_LIST_SYNC, dummyData);
  }

  @Benchmark
  public void testAsyncLPut() {
    asyncClient.lists().lput(KEY_LIST_ASYNC, dummyData);
  }


}
