package com.distkv.client;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.concurrent.CompletableFuture;

public class DistkvStringProxy {

  private String typeCode = "A";

  private DistkvService service;

  public DistkvStringProxy(DistkvService service) {
    this.service = service;
  }

  public void put(String key, String value) {
    StringProtocol.StrPutRequest strPutRequest = StringProtocol.StrPutRequest.newBuilder()
            .setValue(value)
            .build();
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequest(Any.pack(strPutRequest))
        .setRequestType(RequestType.STR_PUT)
        .build();
    CompletableFuture<DistkvProtocol.DistkvResponse> responseFuture = service.call(request);
    DistkvProtocol.DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public String get(String key) throws DistkvException, InvalidProtocolBufferException {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.STR_GET)
        .build();
    CompletableFuture<DistkvProtocol.DistkvResponse> responseFuture = service.call(request);
    DistkvProtocol.DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    return response.getResponse().unpack(StringProtocol.StrGetResponse.class).getValue();
  }

  public boolean drop(String key) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.STR_DROP)
        .build();
    CompletableFuture<DistkvProtocol.DistkvResponse> responseFuture = service.call(request);
    DistkvProtocol.DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    return true;
  }
}
