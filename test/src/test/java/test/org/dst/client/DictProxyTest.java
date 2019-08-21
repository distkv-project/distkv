package test.org.dst.client;

import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
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

    @Test void testDictOtherOperator() {
        TestUtil.startRpcServer();
        DstClient client = new DefaultDstClient(serverAddress);
        Map<String, String> dict = new HashMap<>();
        dict.put("k1","v1");
        client.dicts().put("m1",dict);
        client.dicts().putItem("m1","k2","v2");
        String v2 = client.dicts().popItem("m1","k2");
        Assert.assertEquals(v2,"v2");
        Map<String, String> dict1 = client.dicts().get("m1");
        Assert.assertEquals(dict,dict1);
        client.dicts().put("m2",dict);
        final Map<String, String> m2 = client.dicts().get("m2");
        Assert.assertEquals(dict,m2);
        client.dicts().del("m2");
        TestUtil.stopRpcServer();
    }
}
