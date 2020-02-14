package com.distkv.client;

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
import com.distkv.rpc.protobuf.generated.SetProtocol;

public class DistkvSetProxy {

  private String typeCode = "C";

  public DistkvSetProxy(DistkvService service) {
    this.service = service;
  }

  public void put(String key, Set<String> values) {

  }

  public Set<String> get(String key) throws DistkvException {

  }

  public void putItem(String key, String entity) {

  }

  public void removeItem(String key, String entity) {
  }

  public boolean drop(String key) {
  }

  public boolean exists(String key, String entity) {

  }
}
