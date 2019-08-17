package test.org.dst.client;


import junit.framework.Assert;
import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import org.dst.client.exception.KeyNotFoundException;
import org.junit.jupiter.api.Test;


public class StringProxyTest {

    private final static String serverAddress = "";

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
        } catch (KeyNotFoundException e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue("It shouldn't reach here.", false);
    }
}
