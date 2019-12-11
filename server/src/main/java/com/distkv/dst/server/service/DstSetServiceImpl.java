package com.distkv.dst.server.service;

import java.util.concurrent.CompletableFuture;
import com.distkv.dst.common.RequestTypeEnum;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;
import com.distkv.dst.rpc.service.DstSetService;
import com.distkv.dst.server.runtime.DstRuntime;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class DstSetServiceImpl implements DstSetService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstSetServiceImpl.class);

  private DstRuntime runtime;

  public DstSetServiceImpl(DstRuntime runtime) {
    this.runtime = runtime;
  }

  @Override
  public CompletableFuture<SetProtocol.PutResponse> put(SetProtocol.PutRequest request) {
    CompletableFuture<SetProtocol.PutResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(request.getKey(), RequestTypeEnum.SET_PUT, request, future);
    return future;
  }

  @Override
  public CompletableFuture<SetProtocol.GetResponse> get(SetProtocol.GetRequest request) {
    CompletableFuture<SetProtocol.GetResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(request.getKey(), RequestTypeEnum.SET_GET, request, future);
    return future;
  }

  @Override
  public CompletableFuture<SetProtocol.PutItemResponse> putItem(
      SetProtocol.PutItemRequest request) {
    CompletableFuture<SetProtocol.PutItemResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(
        request.getKey(), RequestTypeEnum.SET_PUT_ITEM, request, future);
    return future;
  }

  @Override
  public CompletableFuture<SetProtocol.RemoveItemResponse> removeItem(
      SetProtocol.RemoveItemRequest request) {
    CompletableFuture<SetProtocol.RemoveItemResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(
        request.getKey(), RequestTypeEnum.SET_REMOVE_ITEM, request, future);
    return future;
  }


  @Override
  public CompletableFuture<SetProtocol.ExistsResponse> exists(SetProtocol.ExistsRequest request) {
    CompletableFuture<SetProtocol.ExistsResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(
        request.getKey(), RequestTypeEnum.SET_EXIST, request, future);
    return future;
  }

  @Override
  public CompletableFuture<CommonProtocol.DropResponse> drop(CommonProtocol.DropRequest request) {
    CompletableFuture<CommonProtocol.DropResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(
        request.getKey(), RequestTypeEnum.SET_DROP, request, future);
    return future;
  }

}
