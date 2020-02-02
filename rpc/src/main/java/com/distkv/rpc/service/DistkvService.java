package com.distkv.rpc.service;

import com.distkv.rpc.protobuf.generated.CommonProtocol;

import java.util.concurrent.CompletableFuture;

public interface DistkvService {
  CompletableFuture<CommonProtocol.DistkvResponse> call(CommonProtocol.DistkvRequest request);
}
