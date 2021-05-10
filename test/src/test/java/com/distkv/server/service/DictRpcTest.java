package com.distkv.server.service;

import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetItemResponse;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetResponse;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPopItemResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.service.DistkvService;
import com.distkv.supplier.BaseTestSupplier;
import com.distkv.supplier.ProxyOnClient;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/*
 * If you want to put a dict,you need new a map. Like this:
 * Map<String,String> localDict = new HashMap<>();
 * and the kv to the request Builder
 * for (Map.Entry<String,String> entry : localDict.entrySet()) {
 *     DistKVDictBuilder.addKeys(entry.getKey());
 *     DistKVDictBuilder.addDict(entry.getValue());
 * }
 * and build the Builder
 *
 * if you want to get a dict, you just need dictGetResponse.getDict() method.
 */
public class DictRpcTest extends BaseTestSupplier {

  @Test
  public void testDictRpcCall() throws InvalidProtocolBufferException {
    try (ProxyOnClient<DistkvService> setProxy = new ProxyOnClient<>(
        DistkvService.class, KVSTORE_PORT)) {
      DistkvService dictService = setProxy.getService();
      // Test dict put.
      DictProtocol.DictPutRequest.Builder dictPutRequestBuilder =
          DictProtocol.DictPutRequest.newBuilder();
      final Map<String, String> localDict = new HashMap<>();
      localDict.put("k1", "v1");
      localDict.put("k2", "v2");
      localDict.put("k3", "v3");
      DictProtocol.DistKVDict.Builder distKVDictBuilder = DictProtocol.DistKVDict.newBuilder();
      for (Map.Entry<String, String> entry : localDict.entrySet()) {
        distKVDictBuilder.addKeys(entry.getKey());
        distKVDictBuilder.addValues(entry.getValue());
      }
      dictPutRequestBuilder.setDict(distKVDictBuilder.build());
      DistkvRequest putRequest = DistkvRequest.newBuilder()
          .setKey("m1")
          .setRequestType(RequestType.DICT_PUT)
          .setRequest(Any.pack(dictPutRequestBuilder.build()))
          .build();
      DistkvResponse setPutResponse = FutureUtils.get(
          dictService.call(putRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, setPutResponse.getStatus());

      // Test putItem
      DictProtocol.DictPutItemRequest.Builder putBuilder =
          DictProtocol.DictPutItemRequest.newBuilder();
      putBuilder.setItemKey("k4");
      putBuilder.setItemValue("v4");
      DistkvRequest putItemRequest = DistkvRequest.newBuilder()
          .setKey("m1")
          .setRequestType(RequestType.DICT_PUT_ITEM)
          .setRequest(Any.pack(putBuilder.build()))
          .build();
      DistkvResponse putItemResponse = FutureUtils.get(
          dictService.call(putItemRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, putItemResponse.getStatus());

      // Test getItemValue
      DictProtocol.DictGetItemRequest.Builder getItemValueBuilder =
          DictProtocol.DictGetItemRequest.newBuilder();
      getItemValueBuilder.setItemKey("k3");
      DistkvRequest getItemRequest = DistkvRequest.newBuilder()
          .setKey("m1")
          .setRequestType(RequestType.DICT_GET_ITEM)
          .setRequest(Any.pack(getItemValueBuilder.build()))
          .build();
      DistkvResponse getItemValueResponse = FutureUtils.get(
          dictService.call(getItemRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, getItemValueResponse.getStatus());
      Assert.assertEquals(getItemValueResponse.getResponse()
          .unpack(DictGetItemResponse.class).getItemValue(), "v3");
      // Test popItem
      DictProtocol.DictPopItemRequest.Builder popItemBuilder =
          DictProtocol.DictPopItemRequest.newBuilder();
      popItemBuilder.setItemKey("k3");
      DistkvRequest popItemRequest = DistkvRequest.newBuilder()
          .setKey("m1")
          .setRequestType(RequestType.DICT_POP_ITEM)
          .setRequest(Any.pack(popItemBuilder.build()))
          .build();
      DistkvResponse popItemResponse = FutureUtils.get(
          dictService.call(popItemRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, popItemResponse.getStatus());
      Assert.assertEquals(popItemResponse.getResponse()
          .unpack(DictPopItemResponse.class).getItemValue(), "v3");
      // Test delItem
      DictProtocol.DictRemoveItemRequest.Builder delItemBuilder =
          DictProtocol.DictRemoveItemRequest.newBuilder();
      delItemBuilder.setItemKey("k2");
      DistkvRequest delItemRequest = DistkvRequest.newBuilder()
          .setKey("m1")
          .setRequestType(RequestType.DICT_REMOVE_ITEM)
          .setRequest(Any.pack(delItemBuilder.build()))
          .build();
      DistkvResponse delItemResponse = FutureUtils.get(
          dictService.call(delItemRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, delItemResponse.getStatus());
      // Test dict get.
      DistkvRequest getRequest = DistkvRequest.newBuilder()
          .setKey("m1")
          .setRequestType(RequestType.DICT_GET)
          .build();
      DistkvResponse dictGetResponse = FutureUtils.get(
          dictService.call(getRequest));
      final Map<String, String> judgeDict = new HashMap<>();
      judgeDict.put("k1", "v1");
      judgeDict.put("k4", "v4");
      DictProtocol.DistKVDict values = dictGetResponse.getResponse()
          .unpack(DictGetResponse.class).getDict();
      Map<String, String> results = new HashMap<>();
      for (int i = 0; i < values.getKeysCount(); i++) {
        results.put(values.getKeys(i), values.getValues(i));
      }
      Assert.assertEquals(CommonProtocol.Status.OK, dictGetResponse.getStatus());
      Assert.assertEquals(results, judgeDict);

    }
  }
}
