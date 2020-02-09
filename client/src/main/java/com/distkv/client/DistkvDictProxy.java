package com.distkv.client;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetItemResponse;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetResponse;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPopItemResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.HashMap;
import java.util.Map;

public class DistkvDictProxy {

  private DistkvService service;

  private String typeCode = "D";

  public DistkvDictProxy(DistkvService service) {
    this.service = service;
  }

  // Put a new dict.
  public void put(String key, Map<String, String> dict) {
    DictProtocol.DictPutRequest.Builder dictPutRequest = DictProtocol.DictPutRequest.newBuilder();
    DictProtocol.DistKVDict distKVDict = DictUtil.buildDistKVDict(dict);
    dictPutRequest.setDict(distKVDict);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_PUT)
        .setRequest(Any.pack(dictPutRequest.build()))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  // Get a dict.
  public Map<String, String> get(String key) {
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_GET)
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    DictProtocol.DistKVDict distKVDict = null;
    try {
      distKVDict = response.getResponse().unpack(DictGetResponse.class).getDict();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    Map<String, String> dict = new HashMap<>();
    for (int i = 0; i < distKVDict.getKeysCount(); i++) {
      dict.put(distKVDict.getKeys(i), distKVDict.getValues(i));
    }
    return dict;
  }

  // Get the value in the dict corresponding to the key.
  public String getItem(String key, String itemKey) {
    DictProtocol.DictGetItemRequest.Builder dictGetItemRequest = DictProtocol.DictGetItemRequest
        .newBuilder()
        .setItemKey(itemKey);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_GET_ITEM)
        .setRequest(Any.pack(dictGetItemRequest.build()))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    try {
      return response.getResponse().unpack(DictGetItemResponse.class).getItemValue();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  // Pop the item in the dict corresponding to the key.
  public String popItem(String key, String itemKey) {
    DictProtocol.DictPopItemRequest.Builder dictPopItemRequest = DictProtocol.DictPopItemRequest
        .newBuilder()
        .setItemKey(itemKey);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_POP_ITEM)
        .setRequest(Any.pack(dictPopItemRequest.build()))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    try {
      return response.getResponse().unpack(DictPopItemResponse.class).getItemValue();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  // Put the item in the dict corresponding to the key.
  public void putItem(String key, String itemKey, String itemValue) {
    DictProtocol.DictPutItemRequest.Builder dictPutItemRequest = DictProtocol.DictPutItemRequest
        .newBuilder()
        .setItemKey(itemKey)
        .setItemValue(itemValue);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_PUT_ITEM)
        .setRequest(Any.pack(dictPutItemRequest.build()))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  /**
   * Drop the k-v pair.
   *
   * @param key The key to be dropped.
   */
  public void drop(String key) {
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_DROP)
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  // Remove the item in the dict corresponding to the key.
  public void removeItem(String key, String itemKey) {
    DictProtocol.DictRemoveItemRequest.Builder dictRemoveItemRequest =
        DictProtocol.DictRemoveItemRequest
            .newBuilder()
            .setItemKey(itemKey);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_REMOVE_ITEM)
        .setRequest(Any.pack(dictRemoveItemRequest.build()))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }
}
