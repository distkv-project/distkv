package com.distkv.dst.client;

import com.distkv.dst.common.utils.FutureUtils;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;
import com.distkv.dst.rpc.service.DstDictService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.distkv.dst.client.CheckStatusUtil.checkStatus;

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

  public CompletableFuture<DictProtocol.PutResponse> asyncPut(
          String key, Map<String, String> dict) {
    DictProtocol.PutRequest.Builder request = DictProtocol.PutRequest.newBuilder();
    request.setKey(key);
    DictProtocol.DstDict dstDict = DictUtil.buildDstDict(dict);
    request.setDict(dstDict);
    CompletableFuture<DictProtocol.PutResponse> future = service.put(request.build());
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
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

  public CompletableFuture<DictProtocol.GetResponse> asyncGet(
          String key) {
    DictProtocol.GetRequest.Builder request = DictProtocol.GetRequest.newBuilder();
    request.setKey(key);
    CompletableFuture<DictProtocol.GetResponse> future = service.get(request.build());
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
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

  public CompletableFuture<DictProtocol.GetItemResponse> asyncGetItem(
          String key, String itemKey) {
    DictProtocol.GetItemRequest.Builder request =
            DictProtocol.GetItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    CompletableFuture<DictProtocol.GetItemResponse> future = service.getItemValue(request.build());
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
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

  public CompletableFuture<DictProtocol.PopItemResponse> asyncPopItem(String key, String itemKey) {
    DictProtocol.PopItemRequest.Builder request = DictProtocol.PopItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    CompletableFuture<DictProtocol.PopItemResponse> future = service.popItem(request.build());
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
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

  public CompletableFuture<DictProtocol.PutItemResponse> asyncPutItem(
          String key, String itemKey, String itemValue) {
    DictProtocol.PutItemRequest.Builder request = DictProtocol.PutItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    request.setItemValue(itemValue);
    CompletableFuture<DictProtocol.PutItemResponse> future = service.putItem(request.build());
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
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

  public CompletableFuture<CommonProtocol.DropResponse> asyncDrop(String key) {
    CommonProtocol.DropRequest.Builder request = CommonProtocol.DropRequest.newBuilder();
    request.setKey(key);
    CompletableFuture<CommonProtocol.DropResponse> future = service.drop(request.build());
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  // Remove the item in the dict corresponding to the key.
  public void removeItem(String key, String itemKey) {
    DictProtocol.RemoveItemRequest.Builder request = DictProtocol.RemoveItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    DictProtocol.RemoveItemResponse response = FutureUtils.get(service.removeItem(request.build()));
    checkStatus(response.getStatus(),request.getKey());
  }

  public CompletableFuture<DictProtocol.RemoveItemResponse> asyncRemoveItem(
          String key, String itemKey) {
    DictProtocol.RemoveItemRequest.Builder request = DictProtocol.RemoveItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    CompletableFuture<DictProtocol.RemoveItemResponse> future = service.removeItem(request.build());
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }
}
