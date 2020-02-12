package com.distkv.rpc.service;

import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;

import java.util.concurrent.CompletableFuture;

public interface DistkvStringService {

  CompletableFuture<StringProtocol.PutResponse> put(StringProtocol.PutRequest request);

  CompletableFuture<StringProtocol.GetResponse> get(StringProtocol.GetRequest request);

  CompletableFuture<CommonProtocol.DropResponse> drop(CommonProtocol.DropRequest request);
}
