package com.distkv.dst.rpc.service;

import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;

import java.util.concurrent.CompletableFuture;

public interface DstStringService {

  CompletableFuture<StringProtocol.PutResponse> put(StringProtocol.PutRequest request);

  CompletableFuture<StringProtocol.GetResponse> get(StringProtocol.GetRequest request);

  CompletableFuture<CommonProtocol.DropResponse> drop(CommonProtocol.DropRequest request);
}
