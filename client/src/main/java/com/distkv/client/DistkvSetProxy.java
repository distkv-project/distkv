package com.distkv.client;

import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetExistsResponse;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetGetResponse;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.HashSet;
import java.util.Set;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import java.util.concurrent.CompletableFuture;

public class DistkvSetProxy {

  private String typeCode = "C";

  private DistkvService service;

  public DistkvSetProxy(DistkvService service) {
    this.service = service;
  }

  public void put(DistkvRequest request) {
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public Set<String> get(DistkvRequest request) throws DistkvException {
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    try {
      return new HashSet<>(response.getResponse().unpack(SetGetResponse.class).getValuesList());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  public void putItem(DistkvRequest request) {
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void removeItem(DistkvRequest request) {
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public boolean drop(DistkvRequest request) {
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    return true;
  }

  public boolean exists(DistkvRequest request) {
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    try {
      return response.getResponse().unpack(SetExistsResponse.class).getResult();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }
}
