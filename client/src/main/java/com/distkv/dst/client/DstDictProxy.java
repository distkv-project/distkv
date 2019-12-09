package com.distkv.dst.client;

import com.distkv.dst.common.exception.DictKeyNotFoundException;
import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.utils.FutureUtils;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;
import com.distkv.dst.rpc.service.DstDictService;
import java.util.HashMap;
import java.util.Map;

public class DstDictProxy {

  private DstDictService service;

  public DstDictProxy(DstDictService service) {
    this.service = service;
  }

  // Put a new dict.
  public void put(String key, Map<String, String> dict) {
    DictProtocol.PutRequest.Builder request = DictProtocol.PutRequest.newBuilder();
    request.setKey(key);
    DictProtocol.DstDict dstDict = DictUtil.buildDstDict(dict);
    request.setDict(dstDict);
    DictProtocol.PutResponse response = FutureUtils.get(service.put(request.build()));
    checkStatus(response.getStatus(),request.getKey());
  }

  // Get a dict.
  public Map<String, String> get(String key) {
    Map<String, String> dict = new HashMap();
    DictProtocol.GetRequest.Builder request = DictProtocol.GetRequest.newBuilder();
    request.setKey(key);
    DictProtocol.GetResponse response = FutureUtils.get(service.get(request.build()));
    checkStatus(response.getStatus(),request.getKey());
    DictProtocol.DstDict dstDict = response.getDict();
    for (int i = 0; i < dstDict.getKeysCount(); i++) {
      dict.put(dstDict.getKeys(i), dstDict.getValues(i));
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
    checkStatus(response.getStatus(),request.getKey());
    return response.getItemValue();
  }

  // Pop the item in the dict corresponding to the key.
  public String popItem(String key, String itemKey) {
    DictProtocol.PopItemRequest.Builder request = DictProtocol.PopItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    DictProtocol.PopItemResponse response = FutureUtils.get(
        service.popItem(request.build()));
    checkStatus(response.getStatus(),request.getKey());
    return response.getItemValue();
  }

  // Put the item in the dict corresponding to the key.
  public void putItem(String key, String itemKey, String itemValue) {
    DictProtocol.PutItemRequest.Builder request = DictProtocol.PutItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    request.setItemValue(itemValue);
    DictProtocol.PutItemResponse response = FutureUtils.get(service.putItem(request.build()));
    checkStatus(response.getStatus(),request.getKey());
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
    checkStatus(response.getStatus(),request.getKey());
  }

  // Remove the item in the dict corresponding to the key.
  public void removeItem(String key, String itemKey) {
    DictProtocol.RemoveItemRequest.Builder request = DictProtocol.RemoveItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    DictProtocol.RemoveItemResponse response = FutureUtils.get(service.removeItem(request.build()));
    checkStatus(response.getStatus(),request.getKey());
  }

  // Used to check the status and throw the corresponding exception.
  private void checkStatus(CommonProtocol.Status status, String key) {
    switch (status) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      case DICT_KEY_NOT_FOUND:
        throw new DictKeyNotFoundException(key);
      default:
        throw new DstException(String.format("Error code is %d", status.getNumber()));
    }
  }
}
