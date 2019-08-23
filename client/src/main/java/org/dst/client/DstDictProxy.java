package org.dst.client;

import org.dst.exception.DictKeyNotFoundException;
import org.dst.exception.DstException;
import org.dst.exception.KeyNotFoundException;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.DictProtocol;
import org.dst.server.service.DstDictService;
import java.util.HashMap;
import java.util.Map;

public class DstDictProxy {

  private DstDictService service;

  public DstDictProxy(DstDictService service) {
    this.service = service;
  }

  // Put a new dict
  public void put(String key, Map<String, String> dict) {
    DictProtocol.PutRequest.Builder request = DictProtocol.PutRequest.newBuilder();
    request.setKey(key);
    DictProtocol.DstDict dstDict = DictUtil.buildDstDict(dict);
    request.setDict(dstDict);
    DictProtocol.PutResponse response = service.put(request.build());
    if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  // Get a dict
  public Map<String, String> get(String key) {
    Map<String, String> dict = new HashMap();
    DictProtocol.GetRequest.Builder request = DictProtocol.GetRequest.newBuilder();
    request.setKey(key);
    DictProtocol.GetResponse response = service.get(request.build());
    switch (response.getStatus()) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      case DICT_KEY_NOT_FOUND:
        throw new DictKeyNotFoundException(key);
      default:
        throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
    DictProtocol.DstDict dstDict = response.getDict();
    for (int i = 0; i < dstDict.getKeysCount(); i++) {
      dict.put(dstDict.getKeys(i), dstDict.getValues(i));
    }
    return dict;
  }

  // Get the value in the dict corresponding to the key
  public String getItemValue(String key, String itemKey) {
    DictProtocol.GetItemValueRequest.Builder request = 
        DictProtocol.GetItemValueRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    DictProtocol.GetItemValueResponse response = service.getItemValue(request.build());
    switch (response.getStatus()) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      case DICT_KEY_NOT_FOUND:
        throw new DictKeyNotFoundException(key);
      default:
        throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
    return response.getItemValue();
  }

  // Pop the item in the dict corresponding to the key
  public String popItem(String key, String itemKey) {
    DictProtocol.PopItemRequest.Builder request = DictProtocol.PopItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    DictProtocol.PopItemResponse response = service.popItem(request.build());
    switch (response.getStatus()) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      case DICT_KEY_NOT_FOUND:
        throw new DictKeyNotFoundException(key);
      default:
        throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
    return response.getItemValue();
  }

  // Put the item in the dict corresponding to the key
  public void putItem(String key, String itemKey, String itemValue) {
    DictProtocol.PutItemRequest.Builder request = DictProtocol.PutItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    request.setItemValue(itemValue);
    DictProtocol.PutItemResponse response = service.putItem(request.build());
    switch (response.getStatus()) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      case DICT_KEY_NOT_FOUND:
        throw new DictKeyNotFoundException(key);
      default:
        throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  // Delete a dict
  public void del(String key) {
    DictProtocol.DelRequest.Builder request = DictProtocol.DelRequest.newBuilder();
    request.setKey(key);
    DictProtocol.DelResponse response = service.del(request.build());
    switch (response.getStatus()) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      case DICT_KEY_NOT_FOUND:
        throw new DictKeyNotFoundException(key);
      default:
        throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  // Delete the item in the dict corresponding to the key
  public void delItem(String key, String itemKey) {
    DictProtocol.DelItemRequest.Builder request = DictProtocol.DelItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    DictProtocol.DelItemResponse response = service.delItem(request.build());
    switch (response.getStatus()) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      case DICT_KEY_NOT_FOUND:
        throw new DictKeyNotFoundException(key);
      default:
        throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }
}
