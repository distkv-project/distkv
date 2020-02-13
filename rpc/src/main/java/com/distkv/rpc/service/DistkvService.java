package com.distkv.rpc.service;

import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import java.util.concurrent.CompletableFuture;

public interface DistkvService {

  CompletableFuture<DistkvProtocol.DistkvResponse> call(DistkvProtocol.DistkvRequest request);

}
