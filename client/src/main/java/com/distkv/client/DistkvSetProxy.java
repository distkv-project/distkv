package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncSetProxy;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetExistsResponse;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetGetResponse;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.HashSet;
import java.util.Set;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.utils.FutureUtils;


public class DistkvSetProxy {

  private static final String typeCode = "C";

  private DistkvAsyncSetProxy asyncSetProxy;

  public DistkvSetProxy(DistkvAsyncSetProxy asyncSetProxy) {
    this.asyncSetProxy = asyncSetProxy;
  }

  public void put(String key, Set<String> values) {
    DistkvResponse response = FutureUtils.get(asyncSetProxy.put(key, values));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public Set<String> get(String key) throws DistkvException {
    DistkvResponse response = FutureUtils.get(asyncSetProxy.get(key));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    try {
      return new HashSet<>(response.getResponse().unpack(SetGetResponse.class).getValuesList());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  public void putItem(String key, String entity) {
    DistkvResponse response = FutureUtils.get(asyncSetProxy.putItem(key, entity));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void removeItem(String key, String entity) {
    DistkvResponse response = FutureUtils.get(asyncSetProxy.removeItem(key, entity));
    CheckStatusUtil.checkStatus(response.getStatus(), key, entity, typeCode);
  }

  public boolean exists(String key, String entity) {
    DistkvResponse response = FutureUtils.get(asyncSetProxy.exists(key, entity));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    try {
      return response.getResponse().unpack(SetExistsResponse.class).getResult();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }
}
