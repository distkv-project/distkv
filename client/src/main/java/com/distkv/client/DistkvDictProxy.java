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
  }

  // Get a dict.
  public Map<String, String> get(String key) {
  }

  // Get the value in the dict corresponding to the key.
  public String getItem(String key, String itemKey) {
  }

  // Pop the item in the dict corresponding to the key.
  public String popItem(String key, String itemKey) {
  }

  // Put the item in the dict corresponding to the key.
  public void putItem(String key, String itemKey, String itemValue) {
  }

  /**
   * Drop the k-v pair.
   *
   * @param key The key to be dropped.
   */
  public void drop(String key) {
  }

  // Remove the item in the dict corresponding to the key.
  public void removeItem(String key, String itemKey) {

  }
}
