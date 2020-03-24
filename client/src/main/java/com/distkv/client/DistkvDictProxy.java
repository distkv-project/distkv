package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncDictProxy;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetItemResponse;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetResponse;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPopItemResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.HashMap;
import java.util.Map;

public class DistkvDictProxy {

  private static final String typeCode = "D";

  private DistkvAsyncDictProxy asyncDictProxy;

  public DistkvDictProxy(DistkvAsyncDictProxy asyncDictProxy) {
    this.asyncDictProxy = asyncDictProxy;
  }

  // Put a new dict.
  public void put(String key, Map<String, String> dict) {
    DistkvResponse response = FutureUtils.get(asyncDictProxy.put(key, dict));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  // Get a dict.
  public Map<String, String> get(String key) {
    DistkvResponse response = FutureUtils.get(asyncDictProxy.get(key));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
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
    DistkvResponse response = FutureUtils.get(asyncDictProxy.getItem(key, itemKey));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    try {
      return response.getResponse().unpack(DictGetItemResponse.class).getItemValue();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  // Pop the item in the dict corresponding to the key.
  public String popItem(String key, String itemKey) {
    DistkvResponse response = FutureUtils.get(asyncDictProxy.popItem(key, itemKey));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    try {
      return response.getResponse().unpack(DictPopItemResponse.class).getItemValue();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  // Put the item in the dict corresponding to the key.
  public void putItem(String key, String itemKey, String itemValue) {
    DistkvResponse response = FutureUtils.get(asyncDictProxy.putItem(key, itemKey, itemValue));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  // Remove the item in the dict corresponding to the key.
  public void removeItem(String key, String itemKey) {
    DistkvResponse response = FutureUtils.get(asyncDictProxy.removeItem(key, itemKey));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

}
