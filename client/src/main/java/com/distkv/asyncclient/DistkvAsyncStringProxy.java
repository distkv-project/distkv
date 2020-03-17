package com.distkv.asyncclient;

import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.ExpireProtocol.ExpireRequest;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.rpc.service.DistkvService;

import com.google.protobuf.Any;
import java.util.concurrent.CompletableFuture;

public class DistkvAsyncStringProxy extends DistkvAbstractAsyncProxy {

  public DistkvAsyncStringProxy(DistkvAsyncClient client, DistkvService service) {
    super(client, service);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> put(
      String key, String value) {
    StringProtocol.StrPutRequest strPutRequest = StringProtocol.StrPutRequest.newBuilder()
        .setValue(value)
        .build();
    return put(key, RequestType.STR_PUT, Any.pack(strPutRequest));
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> get(String key) {
    return get(key, RequestType.STR_GET);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> expire(String key, long expireTime) {
    ExpireRequest expireRequest = ExpireRequest
        .newBuilder()
        .setExpireTime(expireTime)
        .build();
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.EXPIRED_STR)
        .setRequest(Any.pack(expireRequest))
        .build();
    return call(request);
  }
}
