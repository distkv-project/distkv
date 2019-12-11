package com.distkv.dst.test.supplier;

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

public class BaseTestSupplier {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseTestSupplier.class);

  protected int rpcServerPort = -1;

  @BeforeMethod
  public void setupBase(Method method) {
    LOGGER.info(String.format("\n==================== Running the test method: %s.%s",
            method.getDeclaringClass(), method.getName()));
    rpcServerPort = (Math.abs(new Random().nextInt() % 10000)) + 10000;
    TestUtil.startRpcServer(rpcServerPort);
  }

  @AfterMethod
  public void teardownBase() {
    TestUtil.stopRpcServer();
  }

  protected DstClient newDstClient() {
    return new DefaultDstClient(String.format("list://127.0.0.1:%d", rpcServerPort));
  }

  protected DstAsyncClient newAsyncDstClient() {
    return new DefaultAsyncClient(String.format("list://127.0.0.1:%d", rpcServerPort));
  }
}
