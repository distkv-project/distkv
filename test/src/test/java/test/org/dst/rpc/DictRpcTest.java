package test.org.dst.rpc;

import junit.framework.Assert;
import org.dst.server.generated.DictProtocol;
import org.dst.server.service.DstDictService;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

/**
 * if you want to put a dict,you need new a map. Like this:
 * Map<String,String> localDict = new HashMap<>();
 * and add the kv to the request Builder
 * for (Map.Entry<String,String> entry : localDict.entrySet()) {
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
            DictProtocol.PutRequest.Builder dictPutRequestBuilder =
                    DictProtocol.PutRequest.newBuilder();
            dictPutRequestBuilder.setKey("m1");
            final Map<String,String> localDict = new HashMap<>();
            localDict.put("k1", "v1");
            localDict.put("k2", "v2");
            localDict.put("k3", "v3");
            DictProtocol.DstDict.Builder dstDictBuilder = DictProtocol.DstDict.newBuilder();
            for (Map.Entry<String,String> entry : localDict.entrySet()) {
                dstDictBuilder.addKeys(entry.getKey());
                dstDictBuilder.addValues(entry.getValue());
            }
            dictPutRequestBuilder.setDict(dstDictBuilder.build());
            DictProtocol.PutResponse setPutResponse =
                    dictService.put(dictPutRequestBuilder.build());
            Assert.assertEquals("ok", setPutResponse.getStatus());
            // Test dict get.
            DictProtocol.GetRequest.Builder dictGetRequestBuilder =
                    DictProtocol.GetRequest.newBuilder();
            dictGetRequestBuilder.setKey("m1");
            DictProtocol.GetResponse dictGetResponse =
                    dictService.get(dictGetRequestBuilder.build());

            final Map<String,String> judgeDict = new HashMap<>();
            judgeDict.put("k1", "v1");
            judgeDict.put("k2", "v2");
            judgeDict.put("k3", "v3");
            DictProtocol.DstDict values = dictGetResponse.getDict();
            Map<String,String> results = new HashMap<>();
            for (int i = 0;i < values.getKeysCount();i++) {
                results.put(values.getKeys(i), values.getValues(i));
            }

            Assert.assertEquals("ok", dictGetResponse.getStatus());
            Assert.assertEquals(results, judgeDict);
        }
        // Stop the server
        TestUtil.stopRpcServer();
    }
}
