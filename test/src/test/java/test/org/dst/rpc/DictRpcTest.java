package test.org.dst.rpc;

import junit.framework.Assert;
import org.dst.server.generated.DictProtocol;
import org.dst.server.service.DstDictService;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
/**
 * if you want to put a dict,you need new a map. Like this:
 * Map<String,String> myMap = new HashMap<>();
 * use a Request Builder
 * DictPutRequest.Builder dictPutRequestBuilder = DictPutRequest.newBuilder();
 * add the key
 * dictPutRequestBuilder.setKey("m1");
 * and add the kv to the request Builder
 * for (Map.Entry<String,String> entry : myMap.entrySet()) {
 *     dstDictBuilder.addKeys(entry.getKey());
 *     dstDictBuilder.addDict(entry.getValue());
 * }
 * and build the Builder
 *
 * if you want to get a dict, you just need dictGetResponse.getDict() method.
 */
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
            myMap.put("k1", "v1");
            myMap.put("k2", "v2");
            myMap.put("k3", "v3");
            DictProtocol.DstDict.Builder dstDictBuilder = DictProtocol.DstDict.newBuilder();
            for (Map.Entry<String,String> entry : myMap.entrySet()) {
                dstDictBuilder.addKeys(entry.getKey());
                dstDictBuilder.addDict(entry.getValue());
            }
            dictPutRequestBuilder.setValues(dstDictBuilder.build());
            DictProtocol.DictPutResponse setPutResponse =
                    dictService.put(dictPutRequestBuilder.build());
            Assert.assertEquals("ok", setPutResponse.getStatus());
            // Test dict get.
            DictProtocol.DictGetRequest.Builder dictGetRequestBuilder =
                    DictProtocol.DictGetRequest.newBuilder();
            dictGetRequestBuilder.setKey("m1");
            DictProtocol.DictGetResponse dictGetResponse =
                    dictService.get(dictGetRequestBuilder.build());

            final Map<String,String> myMap1 = new HashMap<>();
            myMap1.put("k1", "v1");
            myMap1.put("k2", "v2");
            myMap1.put("k3", "v3");
            DictProtocol.DstDict values = dictGetResponse.getDict();
            Map<String,String> results = new HashMap<>();
            for (int i = 0;i < values.getKeysCount();i++) {
                results.put(values.getKeys(i), values.getDict(i));
            }

            Assert.assertEquals("ok", dictGetResponse.getStatus());
            Assert.assertEquals(results, myMap1);
        }
        // Stop the server
        TestUtil.stopRpcServer();
    }
}
