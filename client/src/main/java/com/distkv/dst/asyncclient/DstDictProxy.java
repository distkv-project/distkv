package com.distkv.dst.asyncclient;

import com.distkv.dst.client.DictUtil;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;
import com.distkv.dst.rpc.service.DstDictService;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DstDictProxy {

  private DstDictService service;

  public DstDictProxy(DstDictService service) {this.service = service; }

  public CompletableFuture<DictProtocol.PutResponse> asyncPut(
          String key, Map<String, String> dict) {
    DictProtocol.PutRequest.Builder request = DictProtocol.PutRequest.newBuilder();
    request.setKey(key);
    DictProtocol.DstDict dstDict = DictUtil.buildDstDict(dict);
    request.setDict(dstDict);
    CompletableFuture<DictProtocol.PutResponse> future = service.put(request.build());
    return future;
  }

  public CompletableFuture<DictProtocol.GetResponse> asyncGet(
          String key) {
    DictProtocol.GetRequest.Builder request = DictProtocol.GetRequest.newBuilder();
    request.setKey(key);
    CompletableFuture<DictProtocol.GetResponse> future = service.get(request.build());
    return future;
  }

  public CompletableFuture<DictProtocol.GetItemResponse> asyncGetItem(
          String key, String itemKey) {
    DictProtocol.GetItemRequest.Builder request =
          DictProtocol.GetItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    CompletableFuture<DictProtocol.GetItemResponse> future = service.getItemValue(request.build());
    return future;
  }

  public CompletableFuture<DictProtocol.PopItemResponse> asyncPopItem(String key, String itemKey) {
    DictProtocol.PopItemRequest.Builder request = DictProtocol.PopItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    CompletableFuture<DictProtocol.PopItemResponse> future = service.popItem(request.build());
    return future;
  }

  public CompletableFuture<DictProtocol.PutItemResponse> asyncPutItem(
          String key, String itemKey, String itemValue) {
    DictProtocol.PutItemRequest.Builder request = DictProtocol.PutItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    request.setItemValue(itemValue);
    CompletableFuture<DictProtocol.PutItemResponse> future = service.putItem(request.build());
    return future;
  }

  public CompletableFuture<CommonProtocol.DropResponse> asyncDrop(String key) {
    CommonProtocol.DropRequest.Builder request = CommonProtocol.DropRequest.newBuilder();
    request.setKey(key);
    CompletableFuture<CommonProtocol.DropResponse> future = service.drop(request.build());
    return future;
  }

  public CompletableFuture<DictProtocol.RemoveItemResponse> asyncRemoveItem(
          String key, String itemKey) {
    DictProtocol.RemoveItemRequest.Builder request = DictProtocol.RemoveItemRequest.newBuilder();
    request.setKey(key);
    request.setItemKey(itemKey);
    CompletableFuture<DictProtocol.RemoveItemResponse> future = service.removeItem(request.build());
    return future;
  }
}
