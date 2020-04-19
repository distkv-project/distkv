package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncListProxy;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListGetResponse;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DistkvListProxy {

  private static final String typeCode = "B";

  private DistkvAsyncListProxy asyncListProxy;

  public DistkvListProxy(DistkvAsyncListProxy asyncListProxy) {
    this.asyncListProxy = asyncListProxy;
  }

  public void put(String key, List<String> values) throws DistkvException {
    CompletableFuture<DistkvResponse> future = asyncListProxy.put(key, values);
    DistkvProtocol.DistkvResponse response = FutureUtils.get(future);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public List<String> get(String key) {
    CompletableFuture<DistkvProtocol.DistkvResponse> future = asyncListProxy.get(key);
    DistkvResponse response = FutureUtils.get(future);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    try {
      return response.getResponse().unpack(ListGetResponse.class).getValuesList();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  public List<String> get(String key, Integer index) {
    DistkvResponse response = FutureUtils.get(asyncListProxy.get(key, index));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    try {
      return response.getResponse().unpack(ListGetResponse.class).getValuesList();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }


  public List<String> get(String key, Integer from, Integer end) {
    DistkvResponse response = FutureUtils.get(asyncListProxy.get(key, from, end));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    try {
      return response.getResponse().unpack(ListGetResponse.class).getValuesList();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  public void lput(String key, List<String> values) {
    DistkvResponse response = FutureUtils.get(asyncListProxy.lput(key, values));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void rput(String key, List<String> values) {
    DistkvResponse response = FutureUtils.get(asyncListProxy.rput(key, values));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void remove(String key, Integer index) {
    DistkvResponse response = FutureUtils.get(asyncListProxy.remove(key, index));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void remove(String key, Integer from, Integer end) {
    DistkvResponse response = FutureUtils.get(asyncListProxy.remove(key, from, end));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void mremove(String key, List<Integer> indexes) {
    DistkvResponse response = FutureUtils.get(asyncListProxy.mremove(key, indexes));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

}
