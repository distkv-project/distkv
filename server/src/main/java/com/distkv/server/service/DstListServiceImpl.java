package com.distkv.server.service;

import java.util.concurrent.CompletableFuture;
import com.distkv.common.RequestTypeEnum;
import com.distkv.rpc.service.DstListService;
import com.distkv.server.runtime.DstRuntime;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol;

public class DstListServiceImpl implements DstListService {

  private DstRuntime runtime;

  public DstListServiceImpl(DstRuntime runtime) {
    this.runtime = runtime;
  }

  @Override
  public CompletableFuture<ListProtocol.PutResponse> put(ListProtocol.PutRequest request) {
    CompletableFuture<ListProtocol.PutResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(request.getKey(),
            RequestTypeEnum.LIST_PUT, request, future);
    return future;
  }

  @Override
  public CompletableFuture<ListProtocol.GetResponse> get(ListProtocol.GetRequest request) {
    CompletableFuture<ListProtocol.GetResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(request.getKey(),
            RequestTypeEnum.LIST_GET, request, future);
    return future;
  }

  @Override
  public CompletableFuture<CommonProtocol.DropResponse> drop(CommonProtocol.DropRequest request) {
    CompletableFuture<CommonProtocol.DropResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(request.getKey(),
            RequestTypeEnum.LIST_DROP, request, future);
    return future;
  }

  @Override
  public CompletableFuture<ListProtocol.LPutResponse> lput(ListProtocol.LPutRequest request) {
    CompletableFuture<ListProtocol.LPutResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(request.getKey(),
            RequestTypeEnum.LIST_LPUT, request, future);
    return future;
  }

  @Override
  public CompletableFuture<ListProtocol.RPutResponse> rput(ListProtocol.RPutRequest request) {
    CompletableFuture<ListProtocol.RPutResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(request.getKey(),
            RequestTypeEnum.LIST_RPUT, request, future);
    return future;
  }

  @Override
  public CompletableFuture<ListProtocol.RemoveResponse> remove(ListProtocol.RemoveRequest request) {
    CompletableFuture<ListProtocol.RemoveResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(request.getKey(),
            RequestTypeEnum.LIST_REMOVE, request, future);
    return future;
  }

  @Override
  public CompletableFuture<ListProtocol.MRemoveResponse> mremove(
          ListProtocol.MRemoveRequest request) {
    CompletableFuture<ListProtocol.MRemoveResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(request.getKey(),
            RequestTypeEnum.LIST_M_REMOVE, request, future);
    return future;
  }
}
