package com.distkv.client;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
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

  public void put(DistkvRequest request) {
    CompletableFuture<DistkvProtocol.DistkvResponse> responseFuture = service.call(request);
    DistkvProtocol.DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public String get(DistkvRequest request) throws DistkvException {
    String value;
    CompletableFuture<DistkvProtocol.DistkvResponse> responseFuture = service.call(request);
    DistkvProtocol.DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    try {
      value = response.getResponse().unpack(StringProtocol.StrGetResponse.class).getValue();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return value;
  }

  public boolean drop(DistkvRequest request) {
    CompletableFuture<DistkvProtocol.DistkvResponse> responseFuture = service.call(request);
    DistkvProtocol.DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    return true;
  }
}
