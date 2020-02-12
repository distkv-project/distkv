package com.distkv.jmhbenchmark;

import com.distkv.asyncclient.DefaultAsyncClient;
import com.distkv.asyncclient.DistkvAsyncClient;
import com.distkv.client.DefaultDistkvClient;
import com.distkv.client.DistkvClient;
import com.distkv.supplier.TestUtil;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import org.apache.commons.lang.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
@BenchmarkMode({Mode.Throughput,Mode.AverageTime})
public class DistkvSetBenchmark {

  private static final String PROTOCOL = "distkv://127.0.0.1:8082";
  private static final String KEY_SET_SYNC = "k-set-sync";
  private static final String KEY_SET_ASYNC = "k-set-async";
  private DistkvAsyncClient asyncClient;
  private DistkvClient client;
  private Set<String> dummyData;

  @Setup
  public void init() {
    TestUtil.startRpcServer(8082);
    dummyData = ImmutableSet.of(
        RandomStringUtils.random(5),
        RandomStringUtils.random(5),
        RandomStringUtils.random(5));

    asyncClient = new DefaultAsyncClient(PROTOCOL);
    client = new DefaultDistkvClient(PROTOCOL);
    client.sets().put(KEY_SET_SYNC,dummyData);
    asyncClient.sets().put(KEY_SET_ASYNC,dummyData);
  }

  @TearDown
  public void close() {
    client.disconnect();
    asyncClient.disconnect();
    TestUtil.stopProcess(TestUtil.getProcess());
  }

  @Benchmark
  public void testAsyncGet(Blackhole blackhole) {
    blackhole.consume(asyncClient.sets().get(KEY_SET_ASYNC));
  }

  @Benchmark
  public void testSyncGet(Blackhole blackhole) {
    blackhole.consume(client.sets().get(KEY_SET_SYNC));
  }

  @Benchmark
  public void testSyncPut(Blackhole blackhole) {
    String randomStr = RandomStringUtils.random(5);
    client.sets().put(randomStr,dummyData);
  }

  @Benchmark
  public void testAsyncPut() {
    String randomStr = RandomStringUtils.random(5);
    asyncClient.sets().put(randomStr,dummyData);
  }

  @Benchmark
  public void testPutItem() {
    String randomStr = RandomStringUtils.random(5);
    client.sets().putItem(KEY_SET_SYNC,randomStr);
  }

  @Benchmark
  public void testAsyncPutItem(Blackhole blackhole) {
    String randomStr = RandomStringUtils.random(5);
    blackhole.consume(asyncClient.sets().putItem(KEY_SET_SYNC,randomStr));
  }
}
