package test.org.dst.benchmark.core;

import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import test.org.dst.supplier.TestUtil;
import java.util.ArrayList;
import java.util.List;

import static test.org.dst.benchmark.DstBenchmarkTest.strPutStressTest;

public class DSTBenchmark {
  public static final String serverAddress = "list://127.0.0.1:8082";

  private int threadNum = 1;

  private Runnable myTest = null;

  public void setTestModule(Runnable myTest) {
    this.myTest = myTest;
  }

  public void setThreadNum(int threadNum) {
    this.threadNum = threadNum;
  }

  public void dstRun() {
    try {
      TestUtil.startRpcServer();
      List<DstClient> clients = new ArrayList<>();
      for (int i = 0; i < threadNum; i++) {
        clients.add(new DefaultDstClient(serverAddress));
      }
      List<Thread> threads = new ArrayList<>();
      for (int i = 0; i < clients.size(); i++) {
        int finalI = i;
        Thread thread =  new Thread(() -> strPutStressTest(clients.get(finalI)));
        thread.start();
        threads.add(thread);
      }
      for(int i = 0;i<threadNum;i++) {
        threads.get(i).join();
      }
      TestUtil.stopRpcServer();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void run() {
    try {
      TestUtil.startRpcServer();
      if (myTest == null) {
        System.out.println("You must set test module");
      } else {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
          Thread thread = new Thread(myTest);
          threads.add(thread);
          thread.start();
        }
        for(int i = 0;i<threadNum;i++) {
          threads.get(i).join();
        }
      }
      TestUtil.stopRpcServer();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    DSTBenchmark benchmark = new DSTBenchmark();
    benchmark.setThreadNum(10);
    benchmark.dstRun();
  }

}
