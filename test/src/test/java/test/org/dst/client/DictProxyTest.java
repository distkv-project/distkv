package test.org.dst.client;

import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import org.dst.exception.DstException;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.org.dst.rpc.TestUtil;
import java.util.HashMap;
import java.util.Map;

public class DictProxyTest {

    private final static String serverAddress = "list://127.0.0.1:8082";

    @Test
    public void testDictPutGet() {
        TestUtil.startRpcServer();
        DstClient client = new DefaultDstClient(serverAddress);
        Map<String, String> dict = new HashMap<>();
        dict.put("k1","v1");
        client.dicts().put("m1",dict);
        Map<String, String> dict1 = client.dicts().get("m1");
        Assert.assertEquals(dict,dict1);
        TestUtil.stopRpcServer();
    }

    @Test
    public void testDictPutItem() {
        TestUtil.startRpcServer();
        DstClient client = new DefaultDstClient(serverAddress);
        Map<String, String> dict = new HashMap<>();
        dict.put("k1","v1");
        client.dicts().put("m1",dict);
        client.dicts().putItem("m1","k2","v2");
        final Map<String, String> m2 = client.dicts().get("m1");
        dict.put("k2", "v2");
        Assert.assertEquals(dict,m2);
        TestUtil.stopRpcServer();
    }

    @Test
    public void testDictGetItemValue() {
        TestUtil.startRpcServer();
        DstClient client = new DefaultDstClient(serverAddress);
        Map<String, String> dict = new HashMap<>();
        dict.put("k1","v1");
        client.dicts().put("m1",dict);
        String s1 = client.dicts().getItemValue("m1", "k1");
        Assert.assertEquals("v1",s1);
        TestUtil.stopRpcServer();
    }

    @Test
    public void testDictPopItem() {
        TestUtil.startRpcServer();
        DstClient client = new DefaultDstClient(serverAddress);
        Map<String, String> dict = new HashMap<>();
        dict.put("k1", "v1");
        dict.put("k2", "v2");
        client.dicts().put("m1",dict);
        String s1 = client.dicts().popItem("m1", "k1");
        Assert.assertEquals("v1",s1);
        dict.remove("k1");
        Assert.assertEquals(dict,client.dicts().get("m1"));
        TestUtil.stopRpcServer();
    }

    @Test
    public void testDictDel() {
        TestUtil.startRpcServer();
        DstClient client = new DefaultDstClient(serverAddress);
        Map<String, String> dict = new HashMap<>();
        dict.put("k1", "v1");
        dict.put("k2", "v2");
        client.dicts().put("m1",dict);
        client.dicts().del("m1");
        TestUtil.stopRpcServer();
    }

    @Test
    public void testException() {
        TestUtil.startRpcServer();
        DstClient client = new DefaultDstClient(serverAddress);
        try {
            client.dicts().del("m1");
            TestUtil.stopRpcServer();
        }catch (DstException e) {
            TestUtil.stopRpcServer();
        }
    }
}
