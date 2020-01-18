package com.distkv.client;

import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.service.DistKVDictService;

import java.util.HashMap;
import java.util.Map;

public class DistKVDictProxy {

  private DistKVDictService service;

  private String typeCode = "D";

  public DistKVDictProxy(DistKVDictService service) {
    this.service = service;
  }

  // Put a new dict.
  public void put(String key, Map<String, String> dict) {
    DictProtocol.PutRequest.Builder request = DictProtocol.PutRequest.newBuilder();
    request.setKey(key);
    DictProtocol.DistKVDict distKVDict = DictUtil.buildDistKVDict(dict);
    request.setDict(distKVDict);
    DictProtocol.PutResponse response = FutureUtils.get(service.put(request.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  // Get a dict.
  public Map<String, String> get(String key) {
    Map<String, String> dict = new HashMap();
    DictProtocol.GetRequest.Builder request = DictProtocol.GetRequest.newBuilder();
    request.setKey(key);
    DictProtocol.GetResponse response = FutureUtils.get(service.get(request.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    DictProtocol.DistKVDict distKVDict = response.getDict();
    for (int i = 0; i < distKVDict.getKeysCount(); i++) {
      dict.put(distKVDict.getKeys(i), distKVDict.getValues(i));
    }
    return dict;
  }

  // Get the value in the dict corresponding to the key.
  public String getItem(String key, String itemKey) {
    DictProtocol.GetItemRequest.Builder request =
        DictProtocol.GetItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    DictProtocol.GetItemResponse response = FutureUtils.get(
        service.getItemValue(request.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    return response.getItemValue();
  }

  // Pop the item in the dict corresponding to the key.
  public String popItem(String key, String itemKey) {
    DictProtocol.PopItemRequest.Builder request = DictProtocol.PopItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    DictProtocol.PopItemResponse response = FutureUtils.get(
        service.popItem(request.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    return response.getItemValue();
  }

  // Put the item in the dict corresponding to the key.
  public void putItem(String key, String itemKey, String itemValue) {
    DictProtocol.PutItemRequest.Builder request = DictProtocol.PutItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    request.setItemValue(itemValue);
    DictProtocol.PutItemResponse response = FutureUtils.get(service.putItem(request.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  /**
   * Drop the k-v pair.
   *
   * @param key The key to be dropped.
   */
  public void drop(String key) {
    CommonProtocol.DropRequest.Builder request = CommonProtocol.DropRequest.newBuilder();
    request.setKey(key);
    CommonProtocol.DropResponse response = FutureUtils.get(service.drop(request.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  // Remove the item in the dict corresponding to the key.
  public void removeItem(String key, String itemKey) {
    DictProtocol.RemoveItemRequest.Builder request = DictProtocol.RemoveItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    DictProtocol.RemoveItemResponse response = FutureUtils.get(service.removeItem(request.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }
}
