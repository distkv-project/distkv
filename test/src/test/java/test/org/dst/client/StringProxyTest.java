package test.org.dst.client;


import junit.framework.Assert;
import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import org.dst.exception.KeyNotFoundException;
import org.testng.annotations.Test;
import test.org.dst.rpc.TestUtil;

public class StringProxyTest {

    private final static String serverAddress = "list://127.0.0.1:8082";


    @Test
    public void testPutAndGet() {
        TestUtil.startRpcServer();
        DstClient client = new DefaultDstClient(serverAddress);
        client.strs().put("k1", "v1");

        Assert.assertEquals("v1", client.strs().get("k1"));
        TestUtil.stopRpcServer();
    }

    @Test
    public void testKeyNotFoundWhenGetting() {
        TestUtil.startRpcServer();
        DstClient client = new DefaultDstClient(serverAddress);

        try {
            client.strs().get("k1");
            Assert.fail("It shouldn't reach here.");
        } catch (KeyNotFoundException e) {
            Assert.assertTrue(true);
        }
        TestUtil.stopRpcServer();
    }
}
