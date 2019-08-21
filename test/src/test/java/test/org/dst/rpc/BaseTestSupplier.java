package test.org.dst.rpc;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import test.org.dst.rpc.TestUtil;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
