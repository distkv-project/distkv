package com.distkv.client;

import com.distkv.common.exception.DstException;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.service.DstListService;

import java.util.List;

public class DstListProxy {

  private String typeCode = "B";

  private DstListService service;

  public DstListProxy(DstListService service) {
    this.service = service;
  }

  public void put(String key, List<String> values) throws DstException {
    ListProtocol.PutRequest request = ListProtocol.PutRequest.newBuilder()
          .setKey(key)
          .addAllValues(values)
          .build();
    ListProtocol.PutResponse response = FutureUtils.get(service.put(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public List<String> get(String key) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
        .setType(ListProtocol.GetType.GET_ALL)
        .setKey(key)
        .build();
    ListProtocol.GetResponse response = FutureUtils.get(service.get(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    return response.getValuesList();
  }

  public List<String> get(String key, Integer index) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
            .setType(ListProtocol.GetType.GET_ONE)
            .setKey(key)
            .setIndex(index)
            .build();
    ListProtocol.GetResponse response = FutureUtils.get(service.get(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    return response.getValuesList();
  }


  public List<String> get(String key, Integer from, Integer end) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
            .setType(ListProtocol.GetType.GET_RANGE)
            .setKey(key)
            .setFrom(from)
            .setEnd(end)
            .build();
    ListProtocol.GetResponse response = FutureUtils.get(service.get(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    return response.getValuesList();
  }

  public void drop(String key) {
    CommonProtocol.DropRequest request = CommonProtocol.DropRequest.newBuilder()
          .setKey(key)
          .build();
    CommonProtocol.DropResponse response = FutureUtils.get(service.drop(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void lput(String key, List<String> values) {
    ListProtocol.LPutRequest request = ListProtocol.LPutRequest.newBuilder()
          .setKey(key)
          .addAllValues(values)
          .build();
    ListProtocol.LPutResponse response = FutureUtils.get(service.lput(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void rput(String key, List<String> values) {
    ListProtocol.RPutRequest request = ListProtocol.RPutRequest.newBuilder()
          .setKey(key)
          .addAllValues(values)
          .build();
    ListProtocol.RPutResponse response = FutureUtils.get(service.rput(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void remove(String key, Integer index) {
    ListProtocol.RemoveRequest request = ListProtocol.RemoveRequest.newBuilder()
            .setType(ListProtocol.RemoveType.RemoveOne)
            .setKey(key)
            .setIndex(index)
            .build();
    ListProtocol.RemoveResponse response = FutureUtils.get(service.remove(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void remove(String key, Integer from, Integer end) {
    ListProtocol.RemoveRequest request = ListProtocol.RemoveRequest.newBuilder()
            .setType(ListProtocol.RemoveType.RemoveRange)
            .setKey(key)
            .setFrom(from)
            .setEnd(end)
            .build();
    ListProtocol.RemoveResponse response = FutureUtils.get(service.remove(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void mremove(String key, List<Integer> indexes) {
    ListProtocol.MRemoveRequest request = ListProtocol.MRemoveRequest.newBuilder()
          .setKey(key)
          .addAllIndexes(indexes)
          .build();
    ListProtocol.MRemoveResponse response = FutureUtils.get(service.mremove(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }
}
