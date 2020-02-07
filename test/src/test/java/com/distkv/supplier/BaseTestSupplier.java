package com.distkv.supplier;

import com.distkv.asyncclient.DefaultAsyncClient;
import com.distkv.asyncclient.DistkvAsyncClient;
import com.distkv.client.DefaultDistkvClient;
import com.distkv.client.DistkvClient;
import com.distkv.common.utils.RuntimeUtil;
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
    TestUtil.stopProcess(TestUtil.getProcess());
  }

  protected DistkvClient newDstClient() {
    final DefaultDistkvClient[] client = {null};
    RuntimeUtil.waitForCondition(() -> {
      try {
        client[0] = new DefaultDistkvClient(String.format("distkv://127.0.0.1:%d", rpcServerPort));
        client[0].strs().put("ping", "ping");
        return true;
      } catch (Exception e) {
        return false;
      }
    }, 2 * 60 * 1000);
    return client[0];
  }

  protected DistkvAsyncClient newAsyncDstClient() {
    final DefaultAsyncClient[] client = {null};
    RuntimeUtil.waitForCondition(() -> {
      try {
        client[0] = new DefaultAsyncClient(String.format("distkv://127.0.0.1:%d", rpcServerPort));
        client[0].strs().put("ping", "ping");
        return true;
      } catch (Exception e) {
        return false;
      }
    }, 2 * 60 * 1000);
    return client[0];
  }

}
