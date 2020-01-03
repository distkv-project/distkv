package com.distkv.dst.test.benchmark;

import com.distkv.dst.asyncclient.DefaultAsyncClient;
import com.distkv.dst.asyncclient.DstAsyncClient;
import com.distkv.dst.client.DefaultDstClient;
import com.distkv.dst.client.DstClient;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.core.KVStoreImpl;
import com.distkv.dst.test.supplier.StrUtil;
import com.distkv.dst.test.supplier.TestUtil;
import org.dst.server.service.DstRpcServer;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.infra.Blackhole;
import redis.clients.jedis.Jedis;

@State(Scope.Thread)
public class JmhBenchmark {

  private Jedis jedis;
  private DstAsyncClient asyncClient;
  private DstClient client;
  private String value = StrUtil.randomString(5);
  private String key = StrUtil.randomString(5);

  @Setup
  public void init() {
    TestUtil.startRpcServer(8082);
    /*
     * When running, please make sure that the local redis
     * server is opened normally on port 6379.
     */
    jedis = new Jedis("127.0.0.1", 6379);
    asyncClient = new DefaultAsyncClient("list://127.0.0.1:8082");
    client = new DefaultDstClient("list://127.0.0.1:8082");

    jedis.set("k-redis","v-redis");
    client.strs().put("k-sync","v-sync");
    asyncClient.strs().put("k-async","v-async");
  }

  @TearDown
  public void close() {
    jedis.close();
    asyncClient.disconnect();
    client.disconnect();
    TestUtil.stopRpcServer();
  }

  @Benchmark
  public void testStrGetRedis(Blackhole bh) {
    bh.consume(jedis.get("k-redis"));
  }

  @Benchmark
  public void testSyncStrGetDrpc(Blackhole bh) {
    bh.consume(client.strs().get("k-sync"));
  }

  @Benchmark
  public void testAsyncStrGetDrpc(Blackhole bh) {
    bh.consume(asyncClient.strs().get("k-async"));
  }

  @Benchmark
  public void testStrPutRedis(Blackhole bh) {
    bh.consume(jedis.set(key, value));
  }

  /*@Benchmark
  public void testStrPutDrpc() {
    client.strs().put(StrUtil.randomString(5),value);
  }*/

  /*@Benchmark
  public void testStrPutBrpc() {
  }

  @Benchmark
  public void testStrGetRedis() {
  }

  @Benchmark
  public void testStrGetDrpc() {
  }

  @Benchmark
  public void testStrGetBrpc() {
  }*/

}