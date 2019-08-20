package test.org.dst.client;


import junit.framework.Assert;
import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import org.dst.exception.KeyNotFoundException;
import org.testng.annotations.Test;
import test.org.dst.rpc.supplier.RPCTestSupplier;

public class StringProxyTest extends RPCTestSupplier{

    private final static String serverAddress = "list://127.0.0.1:8082";


    @Test
    public void testPutAndGet() {
        DstClient client = new DefaultDstClient(serverAddress);
        client.str().put("k1", "v1");

        Assert.assertEquals("v1", client.str().get("k1"));
    }

    @Test
    public void testKeyNotFoundWhenGetting() {
        DstClient client = new DefaultDstClient(serverAddress);

        try {
            client.str().get("k1");
            Assert.fail("It shouldn't reach here.");
        } catch (KeyNotFoundException e) {
            Assert.assertTrue(true);
        }
    }
}
