package com.distkv.jmhbenchmark;

import com.distkv.asyncclient.DefaultAsyncClient;
import com.distkv.asyncclient.DstAsyncClient;
import com.distkv.client.DefaultDstClient;
import com.distkv.client.DstClient;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.core.struct.slist.SortedList;
import com.distkv.supplier.TestUtil;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;
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
public class DstSListBenchmark {

  private static final String PROTOCOL = "distkv://127.0.0.1:8082";
  private static final String KEY_SLIST_SYNC = "k-slist-sync";
  private static final String KEY_SLIST_ASYNC = "k-slist-async";
  private DstAsyncClient dstAsyncClient;
  private DstClient dstClient;
  private SortedList dummyData;

  @Setup
  public void init() {
    TestUtil.startRpcServer(8082);


    dummyData = (SortedList) ImmutableList.of(
        RandomStringUtils.random(5),
        RandomStringUtils.random(5),
        RandomStringUtils.random(5));

    dstAsyncClient = new DefaultAsyncClient(PROTOCOL);
    dstClient = new DefaultDstClient(PROTOCOL);
    dstAsyncClient.sortedLists().put(KEY_SLIST_ASYNC,(LinkedList<SortedListEntity>)dummyData);
    dstClient.sortedLists().put(KEY_SLIST_SYNC, (LinkedList<SortedListEntity>) dummyData);

  }

  @TearDown
  public void close() {
    dstAsyncClient.disconnect();
    dstClient.disconnect();
    TestUtil.stopProcess(TestUtil.getProcess());
  }

  @Benchmark
  public void testSyncGet(Blackhole blackhole) {
    blackhole.consume(dstClient.sortedLists().getMember("g","f"));
  }

  @Benchmark
  public void testAsyncGet(Blackhole blackhole) {
    blackhole.consume(dstAsyncClient.sortedLists().getMember("g","f"));
  }

  @Benchmark
  public void testPut(Blackhole blackhole) {
    String randomStr = RandomStringUtils.random(5);
    dstClient.sortedLists().put(randomStr, (LinkedList<SortedListEntity>) dummyData);
  }

}
