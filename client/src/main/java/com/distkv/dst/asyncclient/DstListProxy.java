package com.distkv.dst.asyncclient;

import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.ListProtocol;
import com.distkv.dst.rpc.service.DstListService;
import com.distkv.dst.rpc.service.DstSetService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.distkv.dst.client.CheckStatusUtil.checkStatus;

public class DstListProxy {

  private DstListService service;

  public DstListProxy(DstListService service) {this.service = service; }

  public CompletableFuture<ListProtocol.PutResponse> asyncPut(
          String key, List<String> values) {
    ListProtocol.PutRequest request = ListProtocol.PutRequest.newBuilder()
            .setKey(key)
            .addAllValues(values)
            .build();
    CompletableFuture<ListProtocol.PutResponse> future = service.put(request);
    return future;
  }

  public CompletableFuture<ListProtocol.GetResponse> asyncGet(String key) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
            .setType(ListProtocol.GetType.GET_ALL)
            .setKey(key)
            .build();
    CompletableFuture<ListProtocol.GetResponse> future = service.get(request);
    return future;
  }

  public CompletableFuture<ListProtocol.GetResponse> asyncGet(
          String key, Integer index) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
            .setType(ListProtocol.GetType.GET_ONE)
            .setKey(key)
            .setIndex(index)
            .build();
    CompletableFuture<ListProtocol.GetResponse> future = service.get(request);
    return future;
  }

  public CompletableFuture<ListProtocol.GetResponse> asyncGet(
          String key, Integer from, Integer end) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
            .setType(ListProtocol.GetType.GET_RANGE)
            .setKey(key)
            .setFrom(from)
            .setEnd(end)
            .build();
    CompletableFuture<ListProtocol.GetResponse> future = service.get(request);
    return future;
  }

  public CompletableFuture<CommonProtocol.DropResponse> asyncDrop(String key) {
    CommonProtocol.DropRequest request = CommonProtocol.DropRequest.newBuilder()
            .setKey(key)
            .build();
    CompletableFuture<CommonProtocol.DropResponse> future = service.drop(request);
    return future;
  }

  public CompletableFuture<ListProtocol.LPutResponse> asyncLput(
          String key, List<String> values) {
    ListProtocol.LPutRequest request = ListProtocol.LPutRequest.newBuilder()
            .setKey(key)
            .addAllValues(values)
            .build();
    CompletableFuture<ListProtocol.LPutResponse> future = service.lput(request);
    return future;
  }

  public CompletableFuture<ListProtocol.RPutResponse> asyncRput(
          String key, List<String> values) {
    ListProtocol.RPutRequest request = ListProtocol.RPutRequest.newBuilder()
            .setKey(key)
            .addAllValues(values)
            .build();
    CompletableFuture<ListProtocol.RPutResponse> future = service.rput(request);
    return future;
  }

  public CompletableFuture<ListProtocol.RemoveResponse> asyncRemove(
          String key, Integer index) {
    ListProtocol.RemoveRequest request = ListProtocol.RemoveRequest.newBuilder()
            .setType(ListProtocol.RemoveType.RemoveOne)
            .setKey(key)
            .setIndex(index)
            .build();
    CompletableFuture<ListProtocol.RemoveResponse> future = service.remove(request);
    return future;
  }

  public CompletableFuture<ListProtocol.RemoveResponse> asyncRemove(
          String key, Integer from, Integer end) {
    ListProtocol.RemoveRequest request = ListProtocol.RemoveRequest.newBuilder()
            .setType(ListProtocol.RemoveType.RemoveRange)
            .setKey(key)
            .setFrom(from)
            .setEnd(end)
            .build();
    CompletableFuture<ListProtocol.RemoveResponse> future = service.remove(request);
    return future;
  }

  public CompletableFuture<ListProtocol.MRemoveResponse> asyncMutiRemove(
          String key, List<Integer> indexes) {
    ListProtocol.MRemoveRequest request = ListProtocol.MRemoveRequest.newBuilder()
            .setKey(key)
            .addAllIndexes(indexes)
            .build();
    CompletableFuture<ListProtocol.MRemoveResponse> future = service.multipleRemove(request);
    return future;
  }
}
