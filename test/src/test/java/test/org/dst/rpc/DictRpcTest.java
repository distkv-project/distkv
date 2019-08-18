package test.org.dst.rpc;

import junit.framework.Assert;
import org.dst.server.generated.DictProtocol;
import org.dst.server.service.DstDictService;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class DictRpcTest {

    @Test
    public void testDictRpcCall() {
        // Run the server
        TestUtil.startRpcServer();
        try(ProxyOnClient<DstDictService> setProxy = new ProxyOnClient<>(DstDictService.class)) {
            DstDictService dictService = setProxy.getService();
            // Test dict put.
            DictProtocol.DictPutRequest.Builder dictPutRequestBuilder =
                    DictProtocol.DictPutRequest.newBuilder();
            dictPutRequestBuilder.setKey("m1");
            final Map<String,String> myMap = new HashMap<>();
            myMap.put("k1","v1");
            myMap.put("k2","v2");
            myMap.put("k3","v3");
            DictProtocol.DstMap.Builder dstMapBuilder = DictProtocol.DstMap.newBuilder();
            for (Map.Entry<String,String> entry : myMap.entrySet()) {
                dstMapBuilder.addKeys(entry.getKey());
                dstMapBuilder.addValues(entry.getValue());
            }
            dictPutRequestBuilder.setValues(dstMapBuilder.build());
            DictProtocol.DictPutResponse setPutResponse =
                    dictService.dictPut(dictPutRequestBuilder.build());
            Assert.assertEquals("ok", setPutResponse.getStatus());

            // Test dict get.
            DictProtocol.DictGetRequest.Builder dictGetRequestBuilder =
                    DictProtocol.DictGetRequest.newBuilder();
            dictGetRequestBuilder.setKey("m1");
            DictProtocol.DictGetResponse dictGetResponse =
                    dictService.dictGet(dictGetRequestBuilder.build());

            final Map<String,String> myMap1 = new HashMap<>();
            myMap1.put("k1","v1");
            myMap1.put("k2","v2");
            myMap1.put("k3","v3");
            DictProtocol.DstMap values = dictGetResponse.getValues();
            Map<String,String> results = new HashMap<>();
            for (int i = 0;i < values.getKeysCount();i++) {
                results.put(values.getKeys(i),values.getValues(i));
            }

            Assert.assertEquals("ok", dictGetResponse.getStatus());
            Assert.assertEquals(results, myMap1);
        }
        // Stop the server
        TestUtil.stopRpcServer();
    }
}
