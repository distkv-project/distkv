package com.distkv.jmhbenchmark;

import com.distkv.asyncclient.DefaultAsyncClient;
import com.distkv.asyncclient.DstAsyncClient;
import com.distkv.client.DefaultDstClient;
import com.distkv.client.DstClient;

import com.distkv.supplier.TestUtil;
import java.util.HashMap;
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
public class DstDictBenchmark<T> {

  private static final String PROTOCOL = "distkv://127.0.0.1:8082";
  private static final String KEY_DICT_SYNC = "K-dict-sync";
  private static final String KEY_DICT_ASYNC = "k-dict-async";
  private DstAsyncClient asyncClient;
  private DstClient client;
  private HashMap<String,String> map;

  @Setup
  public void init() {
    TestUtil.startRpcServer(8082);

    //DstMapInterface<String,T> distKVKeyValueMap=new DstHashMapImpl<>();
    map = new HashMap<>();

    asyncClient = new DefaultAsyncClient(PROTOCOL);
    client = new DefaultDstClient(PROTOCOL);
    client.dicts().put(KEY_DICT_SYNC,map);
    asyncClient.dicts().put(KEY_DICT_ASYNC,map);
  }

  @TearDown
  public void close() {
    asyncClient.disconnect();
    client.disconnect();
    TestUtil.stopProcess(TestUtil.getProcess());
  }

  @Benchmark
  public void testAsyncGet(Blackhole blackhole) {
    blackhole.consume(asyncClient.dicts().get(KEY_DICT_ASYNC));
  }

  @Benchmark
  public void testSyncGet(Blackhole blackhole) {
    blackhole.consume(client.dicts().get(KEY_DICT_SYNC));
  }

  @Benchmark
  public void testPut() {
    String randomStr = RandomStringUtils.random(5);
    client.dicts().put(randomStr,map);
  }

  @Benchmark
  public void testAsyncPut() {
    String randomStr = RandomStringUtils.random(5);
    asyncClient.dicts().put(randomStr,map);
  }
}
