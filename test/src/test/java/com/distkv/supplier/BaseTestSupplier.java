package com.distkv.supplier;

import com.distkv.asyncclient.RaftAsyncClient;
import com.distkv.client.RaftDefaultDistkvClient;
import com.distkv.common.utils.RuntimeUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class BaseTestSupplier {

  public static final int KVSTORE_PORT = 8082;

  private static final Logger LOG = LoggerFactory.getLogger(BaseTestSupplier.class);
  public static final String GROUP_ID = "test";
  public static final String TEST_CONFIG = "127.0.0.1:18080";
  public static final String TEST_DATA_PATH = "\tmp";

  @BeforeMethod
  public void setupBase(Method method) throws InterruptedException {
    LOG.info(String.format("\n==================== Running the test method: %s.%s",
        method.getDeclaringClass(), method.getName()));
    System.out.println(String.format("\n==================== Running the test method: %s.%s",
        method.getDeclaringClass(), method.getName()));

    StaticServer.newInstance();
    TimeUnit.SECONDS.sleep(1);
  }

  protected RaftDefaultDistkvClient newDistkvClient() {
    final RaftDefaultDistkvClient[] client = {null};
    RuntimeUtil.waitForCondition(() -> {
      try {
        client[0] = new RaftDefaultDistkvClient(GROUP_ID, TEST_CONFIG);
        final String randomStr = RandomStringUtils.random(10);
        // A dummy put to ping the server is serving.
        client[0].strs().put(randomStr, randomStr);
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }, 2 * 60 * 1000);
    return client[0];
  }

  protected RaftAsyncClient newAsyncDistkvClient() {
    final RaftAsyncClient[] client = {null};
    RuntimeUtil.waitForCondition(() -> {
      try {
        client[0] = new RaftAsyncClient(GROUP_ID, TEST_CONFIG);
        final String randomStr = RandomStringUtils.random(10);
        // A dummy put to ping the server is serving.
        client[0].strs().put(randomStr, randomStr).get();
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }, 2 * 60 * 1000);
    return client[0];
  }

  public String getListeningAddress() {
    return "distkv://127.0.0.1:" + 8082;
  }

}

