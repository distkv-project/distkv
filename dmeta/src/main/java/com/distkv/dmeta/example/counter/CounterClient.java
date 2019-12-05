package com.distkv.dmeta.example.counter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;

import com.alipay.remoting.InvokeCallback;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.sofa.jraft.RouteTable;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.CliOptions;
import com.alipay.sofa.jraft.rpc.impl.cli.BoltCliClientService;
import com.distkv.dmeta.example.counter.rpc.IncrementAndGetRequest;
import com.distkv.dmeta.example.counter.rpc.ValueResponse;

public class CounterClient {

  private BoltCliClientService cliClientService = null;

  public final static String groupId = "counter";

  public final static String confStr = "127.0.0.1:8081,127.0.0.1:8082,127.0.0.1:8083";

  public static void main(final String[] args) throws Exception {
    CounterClient client = new CounterClient();
    long result = client.inc(10);
  }

  public CounterClient() {

    final Configuration conf = new Configuration();
    if (!conf.parse(confStr)) {
      throw new IllegalArgumentException("Fail to parse conf:" + confStr);
    }

    RouteTable.getInstance().updateConfiguration(groupId, conf);

    //init RPC client and update Routing table
    cliClientService = new BoltCliClientService();
    cliClientService.init(new CliOptions());
  }

  public void inc1to1000() {
    try {
      if (!RouteTable.getInstance().refreshLeader(cliClientService, groupId, 1000).isOk()) {
        throw new IllegalStateException("Refresh leader failed");
      }
      //get leader term
      final PeerId leader = RouteTable.getInstance().selectLeader(groupId);
      System.out.println("Leader is " + leader);
      final int n = 1000;
      final CountDownLatch latch = new CountDownLatch(n);
      final long start = System.currentTimeMillis();
      for (int i = 0; i < n; i++) {
        incrementAndGet(cliClientService, leader, i, latch);
      }
      latch.await();
      System.out.println(n + " ops, cost : " + (System.currentTimeMillis() - start) + " ms.");
      System.exit(0);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    } catch (RemotingException e) {
      e.printStackTrace();
    }
  }

  public long inc(long delta) {
    try {
      if (!RouteTable.getInstance().refreshLeader(cliClientService, groupId, 1000).isOk()) {
        throw new IllegalStateException("Refresh leader failed");
      }
      //get leader term
      final PeerId leader = RouteTable.getInstance().selectLeader(groupId);
      System.out.println("Leader is " + leader);

      final CountDownLatch latch = new CountDownLatch(1);
      final IncrementAndGetRequest request = new IncrementAndGetRequest();
      request.setDelta(delta);
      final long[] arr = new long[1];
      cliClientService.getRpcClient().invokeWithCallback(leader.getEndpoint().toString(), request,
          new InvokeCallback() {

            @Override
            public void onResponse(Object result) {
              arr[0] = ((ValueResponse) result).getValue();
              latch.countDown();
              System.out.println("incrementAndGet result:" + result);
            }

            @Override
            public void onException(Throwable e) {
              e.printStackTrace();
              latch.countDown();
            }

            @Override
            public Executor getExecutor() {
              return null;
            }
          }, 5000);
      latch.await();
      return arr[0];
    } catch (InterruptedException e) {
      e.printStackTrace();
      return 0;
    } catch (TimeoutException e) {
      e.printStackTrace();
      return 0;
    } catch (RemotingException e) {
      e.printStackTrace();
      return 0;
    }
  }

  private static void incrementAndGet(final BoltCliClientService cliClientService, final PeerId leader,
                                      final long delta, CountDownLatch latch) throws RemotingException,
      InterruptedException {
    final IncrementAndGetRequest request = new IncrementAndGetRequest();
    request.setDelta(delta);
    cliClientService.getRpcClient().invokeWithCallback(leader.getEndpoint().toString(), request,
        new InvokeCallback() {

          @Override
          public void onResponse(Object result) {
            latch.countDown();
            System.out.println("incrementAndGet result:" + result);
          }

          @Override
          public void onException(Throwable e) {
            e.printStackTrace();
            latch.countDown();

          }

          @Override
          public Executor getExecutor() {
            return null;
          }
        }, 5000);
  }

}
