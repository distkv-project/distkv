package test.org.dst.benchmark;

import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import test.org.dst.benchmark.core.Benchmark;
import test.org.dst.supplier.TestUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DSTBenchmark extends Benchmark {
  public static final String serverAddress = "list://127.0.0.1:8082";

  public DSTBenchmark(int threadNum) {
    super.setThreadNum(threadNum);
  }

  public DSTBenchmark() {}

  public void run(Consumer<DstClient> myTest) {
    try {
      List<DstClient> clients = new ArrayList<>();
      for (int i = 0; i < super.getThreadNum(); i++) {
        clients.add(new DefaultDstClient(serverAddress));
      }
      List<Thread> threads = new ArrayList<>();
      for (int i = 0; i < clients.size(); i++) {
        int finalI = i;
        Thread thread = new Thread(() -> myTest.accept(clients.get(finalI)));
        thread.start();
        threads.add(thread);
      }
      for (int i = 0; i < super.getThreadNum(); i++) {
        threads.get(i).join();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

}
