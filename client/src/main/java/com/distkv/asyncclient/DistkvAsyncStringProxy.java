package com.distkv.asyncclient;

import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.concurrent.CompletableFuture;

// TODO(qwang): Rename to Distkv
public class DistkvAsyncStringProxy {

  private DistkvService service;

  public DistkvAsyncStringProxy(DistkvService service) {
    this.service = service;
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> put(
      String key, String value) {
    StringProtocol.StrPutRequest strPutRequest = StringProtocol.StrPutRequest.newBuilder()
        .setValue(value)
        .build();
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.STR_PUT)
        .setRequest(Any.pack(strPutRequest))
        .build();
    return service.call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> get(String key) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.STR_GET)
        .build();
    return service.call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> drop(String key) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.STR_DROP)
        .build();
    return service.call(request);
  }
}
