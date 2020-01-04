package com.distkv.dst.test.jmhbenchmark;

import com.distkv.dst.asyncclient.DefaultAsyncClient;
import com.distkv.dst.asyncclient.DstAsyncClient;
import com.distkv.dst.client.DefaultDstClient;
import com.distkv.dst.client.DstClient;
import com.distkv.dst.test.supplier.TestUtil;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.RandomStringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;

@State(Scope.Thread)
@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
public class ListDstBenchmark {

  private DstAsyncClient asyncClient;
  private DstClient client;
  private List<String> dummyData;

  @Setup
  public void init() {
    TestUtil.startRpcServer(8082);

    dummyData = ImmutableList.of(
        RandomStringUtils.random(5),
        RandomStringUtils.random(5),
        RandomStringUtils.random(5));

    asyncClient = new DefaultAsyncClient("list://127.0.0.1:8082");
    client = new DefaultDstClient("list://127.0.0.1:8082");
    client.lists().put("k-list-sync", dummyData);
    asyncClient.lists().put("k-list-async", dummyData);
  }

  @TearDown
  public void close() {
    asyncClient.disconnect();
    client.disconnect();
    TestUtil.stopRpcServer();
  }

  @Benchmark
  public void testSyncGet(Blackhole bh) {
    bh.consume(client.lists().get("k-list-sync"));
  }

  @Benchmark
  public void testAsyncGet(Blackhole bh) {
    bh.consume(asyncClient.lists().get("k-list-async"));
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
    String randomStr = RandomStringUtils.random(5);
    client.lists().lput("k-list-sync", dummyData);
  }

  @Benchmark
  public void testAsyncLPut() {
    String randomStr = RandomStringUtils.random(5);
    asyncClient.lists().lput("k-list-sync", dummyData);
  }


}