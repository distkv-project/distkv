package com.distkv.server.service;

import com.distkv.common.RequestTypeEnum;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.rpc.service.DistKVStringService;
import com.distkv.server.runtime.DistKVRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class DistKVStringServiceImpl implements DistKVStringService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DistKVStringServiceImpl.class);

  private DistKVRuntime runtime;

  public DistKVStringServiceImpl(DistKVRuntime runtime) {
    this.runtime = runtime;
  }

  @Override
  public CompletableFuture<StringProtocol.PutResponse> put(StringProtocol.PutRequest request) {
    CompletableFuture<StringProtocol.PutResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(request.getKey(), RequestTypeEnum.STR_PUT, request, future);
    return future;
  }

  @Override
  public CompletableFuture<StringProtocol.GetResponse> get(StringProtocol.GetRequest request) {
    CompletableFuture<StringProtocol.GetResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(request.getKey(), RequestTypeEnum.STR_GET, request, future);
    return future;
  }

  @Override
  public CompletableFuture<CommonProtocol.DropResponse> drop(CommonProtocol.DropRequest request) {
    CompletableFuture<CommonProtocol.DropResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(
            request.getKey(), RequestTypeEnum.STR_DROP, request, future);
    return future;
  }

}
