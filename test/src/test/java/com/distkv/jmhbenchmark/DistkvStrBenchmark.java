package com.distkv.jmhbenchmark;

import com.distkv.asyncclient.DefaultAsyncClient;
import com.distkv.asyncclient.DistkvAsyncClient;
import com.distkv.client.DefaultDistkvClient;
import com.distkv.client.DistkvClient;
import com.distkv.supplier.TestUtil;
import com.google.protobuf.InvalidProtocolBufferException;
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
public class DistkvStrBenchmark {

  private static final String PROTOCOL = "distkv://127.0.0.1:8082";
  private static final String KEY_STR_SYNC = "k-str-sync";
  private static final String KEY_STR_ASYNC = "k-str-async";
  private static final String VALUE_STR_SYNC = "v-sync";
  private static final String VALUE_STR_ASYNC = "v-async";
  private DistkvAsyncClient asyncClient;
  private DistkvClient client;

  @Setup
  public void init() {
    TestUtil.startRpcServer(8082);

    asyncClient = new DefaultAsyncClient(PROTOCOL);
    client = new DefaultDistkvClient(PROTOCOL);

    client.strs().put(KEY_STR_SYNC, VALUE_STR_SYNC);
    asyncClient.strs().put(KEY_STR_ASYNC, VALUE_STR_ASYNC);
  }

  @TearDown
  public void close() {
    asyncClient.disconnect();
    client.disconnect();
    TestUtil.stopProcess(TestUtil.getProcess());
  }

  @Benchmark
  public void testSyncGet(Blackhole blackhole) throws InvalidProtocolBufferException {
    blackhole.consume(client.strs().get(KEY_STR_SYNC));
  }

  @Benchmark
  public void testAsyncGet(Blackhole blackhole) {
    blackhole.consume(asyncClient.strs().get(KEY_STR_ASYNC));
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
