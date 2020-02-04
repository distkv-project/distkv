package com.distkv.server.storeserver.services;

import java.util.concurrent.CompletableFuture;
import com.distkv.common.RequestTypeEnum;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import com.distkv.rpc.service.DistKVSetService;
import com.distkv.server.storeserver.runtime.StoreRuntime;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class DistKVSetServiceImpl implements DistKVSetService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DistKVSetServiceImpl.class);

  private StoreRuntime storeRuntime;

  public DistKVSetServiceImpl(StoreRuntime storeRuntime) {
    this.storeRuntime = storeRuntime;
  }

  @Override
  public CompletableFuture<SetProtocol.PutResponse> put(SetProtocol.PutRequest request) {
    CompletableFuture<SetProtocol.PutResponse> future = new CompletableFuture<>();
    storeRuntime.getWorkerPool().postRequest(
        request.getKey(), RequestTypeEnum.SET_PUT, request, future);
    return future;
  }

  @Override
  public CompletableFuture<SetProtocol.GetResponse> get(SetProtocol.GetRequest request) {
    CompletableFuture<SetProtocol.GetResponse> future = new CompletableFuture<>();
    storeRuntime.getWorkerPool().postRequest(
        request.getKey(), RequestTypeEnum.SET_GET, request, future);
    return future;
  }

  @Override
  public CompletableFuture<SetProtocol.PutItemResponse> putItem(
      SetProtocol.PutItemRequest request) {
    CompletableFuture<SetProtocol.PutItemResponse> future = new CompletableFuture<>();
    storeRuntime.getWorkerPool().postRequest(
        request.getKey(), RequestTypeEnum.SET_PUT_ITEM, request, future);
    return future;
  }

  @Override
  public CompletableFuture<SetProtocol.RemoveItemResponse> removeItem(
      SetProtocol.RemoveItemRequest request) {
    CompletableFuture<SetProtocol.RemoveItemResponse> future = new CompletableFuture<>();
    storeRuntime.getWorkerPool().postRequest(
        request.getKey(), RequestTypeEnum.SET_REMOVE_ITEM, request, future);
    return future;
  }

  @Override
  public CompletableFuture<SetProtocol.ExistsResponse> exists(SetProtocol.ExistsRequest request) {
    CompletableFuture<SetProtocol.ExistsResponse> future = new CompletableFuture<>();
    storeRuntime.getWorkerPool().postRequest(
        request.getKey(), RequestTypeEnum.SET_EXIST, request, future);
    return future;
  }

  @Override
  public CompletableFuture<CommonProtocol.DropResponse> drop(CommonProtocol.DropRequest request) {
    CompletableFuture<CommonProtocol.DropResponse> future = new CompletableFuture<>();
    storeRuntime.getWorkerPool().postRequest(
        request.getKey(), RequestTypeEnum.SET_DROP, request, future);
    return future;
  }

}
