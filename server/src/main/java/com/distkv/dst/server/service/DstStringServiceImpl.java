package com.distkv.dst.server.service;

import com.distkv.dst.common.RequestTypeEnum;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import com.distkv.dst.rpc.service.DstStringService;
import com.distkv.dst.server.runtime.DstRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.concurrent.CompletableFuture;

public class DstStringServiceImpl implements DstStringService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstStringServiceImpl.class);

  private DstRuntime runtime;

  public DstStringServiceImpl(DstRuntime runtime) {
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
    throw new NotImplementedException();
  }

}
