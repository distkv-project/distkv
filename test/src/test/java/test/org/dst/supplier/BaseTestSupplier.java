package test.org.dst.supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;

public class BaseTestSupplier {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseTestSupplier.class);

  @BeforeMethod
  public void setupBase(Method method) {
    LOGGER.info(String.format("\n==================== Running the test method: %s.%s",
            method.getDeclaringClass(), method.getName()));
    TestUtil.startRpcServer();
  }

  @AfterMethod
  public void teardownBase() {
    TestUtil.stopRpcServer();
  }

}
