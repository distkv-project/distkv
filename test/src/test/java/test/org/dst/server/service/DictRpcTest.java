package test.org.dst.server.service;

import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.DictProtocol;
import org.dst.server.service.DstDictService;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.org.dst.supplier.BaseTestSupplier;
import test.org.dst.supplier.ProxyOnClient;
import java.util.HashMap;
import java.util.Map;

/*
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
public class DictRpcTest extends BaseTestSupplier {
  @Test
  public void testDictRpcCall() {
    try (ProxyOnClient<DstDictService> setProxy = new ProxyOnClient<>(DstDictService.class)) {
      DstDictService dictService = setProxy.getService();
      // Test dict put.
      DictProtocol.PutRequest.Builder dictPutRequestBuilder =
              DictProtocol.PutRequest.newBuilder();
      dictPutRequestBuilder.setKey("m1");
      final Map<String, String> localDict = new HashMap<>();
      localDict.put("k1", "v1");
      localDict.put("k2", "v2");
      localDict.put("k3", "v3");
      DictProtocol.DstDict.Builder dstDictBuilder = DictProtocol.DstDict.newBuilder();
      for (Map.Entry<String, String> entry : localDict.entrySet()) {
        dstDictBuilder.addKeys(entry.getKey());
        dstDictBuilder.addValues(entry.getValue());
      }
      dictPutRequestBuilder.setDict(dstDictBuilder.build());
      DictProtocol.PutResponse setPutResponse =
              dictService.put(dictPutRequestBuilder.build());
      Assert.assertEquals(CommonProtocol.Status.OK, setPutResponse.getStatus());
      // Test putItem
      DictProtocol.PutItemRequest.Builder putBuilder =
              DictProtocol.PutItemRequest.newBuilder();
      putBuilder.setKey("m1");
      putBuilder.setItemKey("k4");
      putBuilder.setItemValue("v4");
      DictProtocol.PutItemResponse putItemResponse =
              dictService.putItem(putBuilder.build());
      Assert.assertEquals(CommonProtocol.Status.OK, putItemResponse.getStatus());
      // Test getItemValue
      DictProtocol.GetItemValueRequest.Builder getItemValueBuilder =
              DictProtocol.GetItemValueRequest.newBuilder();
      getItemValueBuilder.setKey("m1");
      getItemValueBuilder.setItemKey("k3");
      DictProtocol.GetItemValueResponse getItemValueResponse =
              dictService.getItemValue(getItemValueBuilder.build());
      Assert.assertEquals(CommonProtocol.Status.OK, getItemValueResponse.getStatus());
      Assert.assertEquals(getItemValueResponse.getItemValue(), "v3");
      // Test popItem
      DictProtocol.PopItemRequest.Builder popItemBuilder =
              DictProtocol.PopItemRequest.newBuilder();
      popItemBuilder.setKey("m1");
      popItemBuilder.setItemKey("k3");
      DictProtocol.PopItemResponse popItemResponse =
              dictService.popItem(popItemBuilder.build());
      Assert.assertEquals(CommonProtocol.Status.OK, popItemResponse.getStatus());
      Assert.assertEquals(popItemResponse.getItemValue(), "v3");
      // Test delItem
      DictProtocol.DelItemRequest.Builder delItemBuilder =
              DictProtocol.DelItemRequest.newBuilder();
      delItemBuilder.setKey("m1");
      delItemBuilder.setItemKey("k2");
      DictProtocol.DelItemResponse delItemResponse =
              dictService.delItem(delItemBuilder.build());
      Assert.assertEquals(CommonProtocol.Status.OK, delItemResponse.getStatus());
      // Test dict get.
      DictProtocol.GetRequest.Builder dictGetRequestBuilder =
              DictProtocol.GetRequest.newBuilder();
      dictGetRequestBuilder.setKey("m1");
      DictProtocol.GetResponse dictGetResponse =
              dictService.get(dictGetRequestBuilder.build());
      final Map<String, String> judgeDict = new HashMap<>();
      judgeDict.put("k1", "v1");
      judgeDict.put("k4", "v4");
      DictProtocol.DstDict values = dictGetResponse.getDict();
      Map<String, String> results = new HashMap<>();
      for (int i = 0; i < values.getKeysCount(); i++) {
        results.put(values.getKeys(i), values.getValues(i));
      }
      Assert.assertEquals(CommonProtocol.Status.OK, dictGetResponse.getStatus());
      Assert.assertEquals(results, judgeDict);

    }
  }
}
