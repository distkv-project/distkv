package com.distkv.dst.server.service;

import com.distkv.dst.common.RequestTypeEnum;
import com.distkv.dst.common.utils.FutureUtils;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.server.base.DstBaseService;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import com.distkv.dst.rpc.service.DstStringService;
import com.distkv.dst.server.runtime.DstRuntime;
import com.distkv.dst.server.runtime.workerpool.WorkerPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.concurrent.CompletableFuture;

public class DstStringServiceImpl implements DstStringService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstStringServiceImpl.class);

  private WorkerPool workerPool = new WorkerPool(5);

  public DstStringServiceImpl() {
  }

  @Override
  public CompletableFuture<StringProtocol.PutResponse> put(StringProtocol.PutRequest request) {
    CompletableFuture<StringProtocol.PutResponse> future = new CompletableFuture<StringProtocol.PutResponse>();
    workerPool.postRequest(request.getKey(), RequestTypeEnum.STR_PUT, request, future);
    return future;
  }

  @Override
  public CompletableFuture<StringProtocol.GetResponse> get(StringProtocol.GetRequest request) {
    CompletableFuture<StringProtocol.GetResponse> future = new CompletableFuture<StringProtocol.GetResponse>();
    workerPool.postRequest(request.getKey(), RequestTypeEnum.STR_GET, request, future);
    return future;
  }

  @Override
  public CompletableFuture<CommonProtocol.DropResponse> drop(CommonProtocol.DropRequest request) {
    throw new NotImplementedException();
  }

}
