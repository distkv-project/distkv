package com.distkv.asyncclient;

import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DistkvAsyncListProxy extends DistkvAbstractAsyncProxy {

  public DistkvAsyncListProxy(DistkvAsyncClient client, DistkvService service) {
    super(client, service);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> put(String key, List<String> values) {
    ListProtocol.ListPutRequest listPutRequest = ListProtocol.ListPutRequest.newBuilder()
        .addAllValues(values)
        .build();

    return put(key, RequestType.LIST_PUT, Any.pack(listPutRequest));
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> get(String key) {
    ListProtocol.ListGetRequest listGetRequest = ListProtocol.ListGetRequest.newBuilder()
        .setType(ListProtocol.GetType.GET_ALL)
        .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_GET)
        .setRequest(Any.pack(listGetRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> get(String key, Integer index) {
    ListProtocol.ListGetRequest listGetRequest = ListProtocol.ListGetRequest.newBuilder()
        .setType(ListProtocol.GetType.GET_ONE)
        .setIndex(index)
        .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_GET)
        .setRequest(Any.pack(listGetRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> get(
      String key, Integer from, Integer end) {
    ListProtocol.ListGetRequest listGetRequest = ListProtocol.ListGetRequest.newBuilder()
        .setType(ListProtocol.GetType.GET_RANGE)
        .setFrom(from)
        .setEnd(end)
        .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_GET)
        .setRequest(Any.pack(listGetRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> lput(String key, List<String> values) {
    ListProtocol.ListLPutRequest listLPutRequest = ListProtocol.ListLPutRequest.newBuilder()
        .addAllValues(values)
        .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_LPUT)
        .setRequest(Any.pack(listLPutRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> rput(String key, List<String> values) {
    ListProtocol.ListRPutRequest listRPutRequest = ListProtocol.ListRPutRequest.newBuilder()
        .addAllValues(values)
        .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_RPUT)
        .setRequest(Any.pack(listRPutRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> remove(String key, Integer index) {
    ListProtocol.ListRemoveRequest listRemoveRequest = ListProtocol.ListRemoveRequest.newBuilder()
        .setType(ListProtocol.RemoveType.RemoveOne)
        .setIndex(index)
        .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_REMOVE)
        .setRequest(Any.pack(listRemoveRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> remove(
      String key, Integer from, Integer end) {
    ListProtocol.ListRemoveRequest listRemoveRequest = ListProtocol.ListRemoveRequest.newBuilder()
        .setType(ListProtocol.RemoveType.RemoveRange)
        .setFrom(from)
        .setEnd(end)
        .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_REMOVE)
        .setRequest(Any.pack(listRemoveRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> mremove(
      String key, List<Integer> indexes) {
    ListProtocol.ListMRemoveRequest listMRemoveRequest = ListProtocol.ListMRemoveRequest
        .newBuilder()
        .addAllIndexes(indexes)
        .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_MREMOVE)
        .setRequest(Any.pack(listMRemoveRequest))
        .build();
    return call(request);
  }

}
