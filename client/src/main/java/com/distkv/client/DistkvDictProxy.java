package com.distkv.client;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetItemResponse;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetResponse;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPopItemResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.service.DistkvService;
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
  public void put(DistkvRequest request ) {
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  // Get a dict.
  public Map<String, String> get(DistkvRequest request ) {
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    DictProtocol.DistKVDict distKVDict;
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
  public String getItem(DistkvRequest request ) {
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    try {
      return response.getResponse().unpack(DictGetItemResponse.class).getItemValue();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  // Pop the item in the dict corresponding to the key.
  public String popItem(DistkvRequest request ){
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    try {
      return response.getResponse().unpack(DictPopItemResponse.class).getItemValue();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  // Put the item in the dict corresponding to the key.
  public void putItem(DistkvRequest request ) {
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  // Drop the k-v pair.
  public void drop(DistkvRequest request ) {
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  // Remove the item in the dict corresponding to the key.
  public void removeItem(DistkvRequest request) {
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }
}
