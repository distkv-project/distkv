package test.org.dst.supplier;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTestSupplier {

    @BeforeMethod
    public void setupBase() {
        TestUtil.startRpcServer();
    }

    @AfterMethod
    public  void teardownBase() {
        TestUtil.stopRpcServer();
    }

}
