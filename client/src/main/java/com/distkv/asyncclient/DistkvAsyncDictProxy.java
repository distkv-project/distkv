package com.distkv.asyncclient;

import com.distkv.client.DictUtil;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol;

import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DistkvAsyncDictProxy {

  private DistkvService service;

  public DistkvAsyncDictProxy(DistkvService service) {
    this.service = service;
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> put(
      String key, Map<String, String> dict) {
    DictProtocol.DictPutRequest dictPutRequest = DictProtocol.DictPutRequest.newBuilder()
        .setDict(DictUtil.buildDistKVDict(dict))
        .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_PUT)
        .setRequest(Any.pack(dictPutRequest))
        .build();
    return service.call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> get(String key) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_GET)
        .build();
    return service.call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> getItem(
      String key, String itemKey) {
    DictProtocol.DictGetItemRequest dictGetItemRequest = DictProtocol.DictGetItemRequest
        .newBuilder()
        .setItemKey(itemKey)
        .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_GET_ITEM)
        .setRequest(Any.pack(dictGetItemRequest))
        .build();
    return service.call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> popItem(String key, String itemKey) {
    DictProtocol.DictPopItemRequest dictPopItemRequest = DictProtocol.DictPopItemRequest
        .newBuilder()
        .setItemKey(itemKey)
        .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_POP_ITEM)
        .setRequest(Any.pack(dictPopItemRequest))
        .build();
    return service.call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> putItem(
      String key, String itemKey, String itemValue) {
    DictProtocol.DictPutItemRequest dictPutItemRequest = DictProtocol.DictPutItemRequest
        .newBuilder()
        .setItemKey(itemKey)
        .setItemValue(itemValue)
        .build();
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_PUT_ITEM)
        .setRequest(Any.pack(dictPutItemRequest))
        .build();
    return service.call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> drop(String key) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_DROP)
        .build();
    return service.call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> removeItem(
      String key, String itemKey) {
    DictProtocol.DictRemoveItemRequest dictRemoveItemRequest = DictProtocol.DictRemoveItemRequest
        .newBuilder()
        .setItemKey(itemKey)
        .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_REMOVE_ITEM)
        .setRequest(Any.pack(dictRemoveItemRequest))
        .build();
    return service.call(request);
  }
}
