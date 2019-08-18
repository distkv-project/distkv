package test.org.dst.client;


import junit.framework.Assert;
import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import org.dst.client.exception.KeyNotFoundException;
import org.junit.jupiter.api.Test;
import test.org.dst.rpc.TestUtil;

public class StringProxyTest {

    private final static String serverAddress = "list://127.0.0.1:8082";


    @Test
    public void testPutAndGet() {
        TestUtil.startRpcServer();
        DstClient client = new DefaultDstClient(serverAddress);
        client.str().put("k1", "v1");

        Assert.assertEquals("v1", client.str().get("k1"));
        TestUtil.stopRpcServer();
    }

    @Test
    public void testKeyNotFoundWhenGetting() {
        TestUtil.startRpcServer();
        DstClient client = new DefaultDstClient(serverAddress);

        try {
            client.str().get("k1");
            Assert.assertTrue("It shouldn't reach here.", false);
        } catch (KeyNotFoundException e) {
            Assert.assertTrue(true);
        }
        TestUtil.stopRpcServer();
    }
}
