package com.distkv.dst.supplier;

import com.distkv.dst.asyncclient.DefaultAsyncClient;
import com.distkv.dst.asyncclient.DstAsyncClient;
import com.distkv.dst.client.DefaultDstClient;
import com.distkv.dst.client.DstClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BaseTestSupplier {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseTestSupplier.class);

  protected int rpcServerPort = -1;

  @BeforeMethod
  public void setupBase(Method method) throws InterruptedException {
    LOGGER.info(String.format("\n==================== Running the test method: %s.%s",
        method.getDeclaringClass(), method.getName()));
    System.out.println(String.format("\n==================== Running the test method: %s.%s",
        method.getDeclaringClass(), method.getName()));
    rpcServerPort = (Math.abs(new Random().nextInt() % 10000)) + 10000;
    TestUtil.startRpcServer(rpcServerPort);
    TimeUnit.SECONDS.sleep(1);
  }

  @AfterMethod
  public void teardownBase() {
    TestUtil.stopRpcServer();
  }

  protected DstClient newDstClient() {
    DefaultDstClient client = null;
    int reTryTime = 10;
    while (reTryTime > 0) {
      try {
        client = new DefaultDstClient(String.format("list://127.0.0.1:%d", rpcServerPort));
        client.strs().put("ping", "ping");
      } catch (Exception e) {
        reTryTime--;
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
        continue;
      }
      break;
    }
    return client;
  }

  protected DstAsyncClient newAsyncDstClient() {
    DefaultAsyncClient client = null;
    int reTryTime = 10;
    while (reTryTime > 0) {
      try {
        client = new DefaultAsyncClient(String.format("list://127.0.0.1:%d", rpcServerPort));
        client.strs().put("ping", "ping").get();
      } catch (Exception e) {
        reTryTime--;
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
        continue;
      }
      break;
    }
    return client;
  }
}
