package com.distkv.rpc.service;

import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import java.util.concurrent.CompletableFuture;

public interface DstSetService {

  CompletableFuture<SetProtocol.PutResponse> put(SetProtocol.PutRequest request);

  CompletableFuture<SetProtocol.GetResponse> get(SetProtocol.GetRequest request);

  CompletableFuture<SetProtocol.PutItemResponse> putItem(SetProtocol.PutItemRequest request);

  CompletableFuture<SetProtocol.RemoveItemResponse> removeItem(
      SetProtocol.RemoveItemRequest request);

  CompletableFuture<CommonProtocol.DropResponse> drop(CommonProtocol.DropRequest request);

  CompletableFuture<SetProtocol.ExistsResponse> exists(SetProtocol.ExistsRequest request);

}
