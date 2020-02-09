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

  private DistkvService service;

  public DistkvListProxy(DistkvService service) {
    this.service = service;
  }

  public void put(String key, List<String> values) throws DistkvException {
    ListProtocol.ListPutRequest listPutRequest = ListProtocol.ListPutRequest.newBuilder()
        .addAllValues(values)
        .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_PUT)
        .setRequest(Any.pack(listPutRequest))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public List<String> get(String key) {
    ListProtocol.ListGetRequest listGetRequest = ListProtocol.ListGetRequest.newBuilder()
        .setType(ListProtocol.GetType.GET_ALL)
        .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_GET)
        .setRequest(Any.pack(listGetRequest))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    try {
      return response.getResponse().unpack(ListGetResponse.class).getValuesList();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  public List<String> get(String key, Integer index) {
    ListProtocol.ListGetRequest listGetRequest = ListProtocol.ListGetRequest.newBuilder()
        .setType(ListProtocol.GetType.GET_ONE)
        .setIndex(index)
        .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_GET)
        .setRequest(Any.pack(listGetRequest))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    try {
      return response.getResponse().unpack(ListGetResponse.class).getValuesList();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }


  public List<String> get(String key, Integer from, Integer end) {
    ListProtocol.ListGetRequest listGetRequest = ListProtocol.ListGetRequest.newBuilder()
        .setType(ListProtocol.GetType.GET_RANGE)
        .setFrom(from)
        .setEnd(end)
        .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_GET)
        .setRequest(Any.pack(listGetRequest))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    try {
      return response.getResponse().unpack(ListGetResponse.class).getValuesList();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  public void drop(String key) {
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_DROP)
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void lput(String key, List<String> values) {
    ListProtocol.ListLPutRequest listLPutRequest = ListProtocol.ListLPutRequest.newBuilder()
        .addAllValues(values)
        .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_LPUT)
        .setRequest(Any.pack(listLPutRequest))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void rput(String key, List<String> values) {
    ListProtocol.ListRPutRequest listRPutRequest = ListProtocol.ListRPutRequest.newBuilder()
        .addAllValues(values)
        .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_RPUT)
        .setRequest(Any.pack(listRPutRequest))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void remove(String key, Integer index) {
    ListProtocol.ListRemoveRequest listRemoveRequest = ListProtocol.ListRemoveRequest.newBuilder()
        .setType(ListProtocol.RemoveType.RemoveOne)
        .setIndex(index)
        .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_REMOVE)
        .setRequest(Any.pack(listRemoveRequest))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void remove(String key, Integer from, Integer end) {
    ListProtocol.ListRemoveRequest listRemoveRequest = ListProtocol.ListRemoveRequest.newBuilder()
        .setType(ListProtocol.RemoveType.RemoveRange)
        .setFrom(from)
        .setEnd(end)
        .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_REMOVE)
        .setRequest(Any.pack(listRemoveRequest))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void mremove(String key, List<Integer> indexes) {
    ListProtocol.ListMRemoveRequest listMRemoveRequest = ListProtocol.ListMRemoveRequest
        .newBuilder()
        .addAllIndexes(indexes)
        .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_MREMOVE)
        .setRequest(Any.pack(listMRemoveRequest))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }
}
