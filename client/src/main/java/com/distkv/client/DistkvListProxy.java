package com.distkv.client;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListGetResponse;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.List;

public class DistkvListProxy {

  private String typeCode = "B";


  public DistkvListProxy(DistkvService service) {
    this.service = service;
  }

  public void put(String key, List<String> values) throws DistkvException {
  }

  public List<String> get(String key) {
  }

  public List<String> get(String key, Integer index) {

  }


  public List<String> get(String key, Integer from, Integer end) {
  }

  public void drop(String key) {
  }

  public void lput(String key, List<String> values) {
  }

  public void rput(String key, List<String> values) {
  }

  public void remove(String key, Integer index) {
  }

  public void remove(String key, Integer from, Integer end) {
  }

  public void mremove(String key, List<Integer> indexes) {

  }
}
