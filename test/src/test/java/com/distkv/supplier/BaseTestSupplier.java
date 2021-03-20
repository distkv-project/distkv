package com.distkv.supplier;

import com.distkv.asyncclient.DefaultAsyncClient;
import com.distkv.asyncclient.DistkvAsyncClient;
import com.distkv.client.DefaultDistkvClient;
import com.distkv.client.DistkvClient;
import com.distkv.common.utils.RuntimeUtil;
import com.distkv.server.storeserver.StoreConfig;
import com.distkv.server.storeserver.StoreServer;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BaseTestSupplier {

  private static final Logger LOG = LoggerFactory.getLogger(BaseTestSupplier.class);

  private static final AtomicInteger PORT = new AtomicInteger(30000);
  protected final ThreadLocal<Integer> rpcServerPort =
      ThreadLocal.withInitial(() -> PORT.getAndIncrement());
  protected final ThreadLocal<StoreServer> storeServer = new ThreadLocal<>();

  @BeforeMethod
  public void setupBase(Method method) throws InterruptedException {
    LOG.info(String.format("\n==================== Running the test method: %s.%s",
        method.getDeclaringClass(), method.getName()));
    System.out.println(String.format("\n==================== Running the test method: %s.%s",
        method.getDeclaringClass(), method.getName()));

    StoreConfig config = StoreConfig.create();
    config.setPort(rpcServerPort.get());
    storeServer.set(new StoreServer(config));
    storeServer.get().run();
    TimeUnit.SECONDS.sleep(1);
  }

  public String getListeningAddress() {
    return "distkv://127.0.0.1:" + rpcServerPort.get();
  }

  @AfterMethod
  public void teardownBase() {
    if (storeServer.get() != null) {
      storeServer.get().shutdown();
    }
  }

  protected DistkvClient newDistkvClient() {
    final DefaultDistkvClient[] client = {null};
    RuntimeUtil.waitForCondition(() -> {
      try {
        client[0] = new DefaultDistkvClient(
            String.format("distkv://127.0.0.1:%d", rpcServerPort.get()));
        final String randomStr = RandomStringUtils.random(10);
        // A dummy put to ping the server is serving.
        client[0].strs().put(randomStr, randomStr);
        return true;
      } catch (Exception e) {
        return false;
      }
    }, 2 * 60 * 1000);
    return client[0];
  }

  protected DistkvAsyncClient newAsyncDistkvClient() {
    final DefaultAsyncClient[] client = {null};
    RuntimeUtil.waitForCondition(() -> {
      try {
        client[0] = new DefaultAsyncClient(
            String.format("distkv://127.0.0.1:%d", rpcServerPort.get()));
        final String randomStr = RandomStringUtils.random(10);
        // A dummy put to ping the server is serving.
        client[0].strs().put(randomStr, randomStr);
        return true;
      } catch (Exception e) {
        return false;
      }
    }, 2 * 60 * 1000);
    return client[0];
  }

}
