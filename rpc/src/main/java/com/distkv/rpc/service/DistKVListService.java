package com.distkv.rpc.service;

import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import java.util.concurrent.CompletableFuture;

public interface DistKVListService {

  CompletableFuture<ListProtocol.PutResponse> put(ListProtocol.PutRequest request);

  CompletableFuture<ListProtocol.GetResponse> get(ListProtocol.GetRequest request);

  CompletableFuture<CommonProtocol.DropResponse> drop(CommonProtocol.DropRequest request);

  CompletableFuture<ListProtocol.LPutResponse> lput(ListProtocol.LPutRequest request);

  CompletableFuture<ListProtocol.RPutResponse> rput(ListProtocol.RPutRequest request);

  CompletableFuture<ListProtocol.RemoveResponse> remove(ListProtocol.RemoveRequest request);

  CompletableFuture<ListProtocol.MRemoveResponse> mremove(
      ListProtocol.MRemoveRequest request);

}
