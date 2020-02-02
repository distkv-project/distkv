package com.distkv.asyncclient;

import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.concurrent.CompletableFuture;

// TODO(qwang): Rename to Distkv
public class DstAsyncStringProxy {
  private DistkvService service;

  public DstAsyncStringProxy(DistkvService service) {
    this.service = service;
  }

  public CompletableFuture<StringProtocol.StrPutResponse> put(
          String key, String value) {
    StringProtocol.StrPutRequest strPutRequest = StringProtocol.StrPutRequest.newBuilder()
            .setKey(key)
            .setValue(value)
            .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setRequestType(DistkvProtocol.RequestType.STR_PUT)
        .setRequest(Any.pack(strPutRequest))
        .build();
    CompletableFuture<DistkvProtocol.DistkvResponse> future = service.call(request);
    CompletableFuture<StringProtocol.StrPutResponse> strPutResponseFuture = new CompletableFuture<>();
    future.whenComplete((r, t) -> {
      if (t != null) {
        // TODO(qwang):
      }
      try {
        r.getResponse().unpack(StringProtocol.StrPutResponse.class);
      } catch (InvalidProtocolBufferException e) {
        // TODO(qwang):
      }
      strPutResponseFuture.complete();
    });
    return strPutResponseFuture;
  }

  public CompletableFuture<StringProtocol.GetResponse> get(String key) {
    StringProtocol.GetRequest request = StringProtocol.GetRequest.newBuilder()
            .setKey(key)
            .build();
    CompletableFuture<StringProtocol.GetResponse> future = service.get(request);
    return future;
  }

  public CompletableFuture<CommonProtocol.DropResponse> drop(String key) {
    CommonProtocol.DropRequest request = CommonProtocol.DropRequest.newBuilder()
            .setKey(key)
            .build();
    CompletableFuture<CommonProtocol.DropResponse> future = service.drop(request);
    return future;
  }
}
