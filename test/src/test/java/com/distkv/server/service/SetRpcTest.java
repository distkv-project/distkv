package com.distkv.server.service;

import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetGetResponse;
import com.distkv.rpc.service.DistkvService;
import com.distkv.supplier.BaseTestSupplier;
import com.distkv.supplier.ProxyOnClient;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SetRpcTest extends BaseTestSupplier {

  @Test
  public void testSet() throws InvalidProtocolBufferException {
    // The following methods should be called as ordered
    // because some methods depend on other methods.
    testPut(KVSTORE_PORT);
    testGet(KVSTORE_PORT);
    testRemoveItem(KVSTORE_PORT);
    testDropByKey(KVSTORE_PORT);
    testExists(KVSTORE_PORT);
  }

  private static void testPut(int rpcServerPort) {
    try (ProxyOnClient<DistkvService> setProxy = new ProxyOnClient<>(
        DistkvService.class, rpcServerPort)) {
      DistkvService setService = setProxy.getService();
      SetProtocol.SetPutRequest.Builder setPutRequestBuilder =
          SetProtocol.SetPutRequest.newBuilder();
      final List<String> values = ImmutableList.of("v1", "v2", "v3", "v1");
      values.forEach(setPutRequestBuilder::addValues);

      DistkvRequest putRequest = DistkvRequest.newBuilder()
          .setKey("set_r_k1")
          .setRequestType(RequestType.SET_PUT)
          .setRequest(Any.pack(setPutRequestBuilder.build()))
          .build();
      DistkvResponse setPutResponse = FutureUtils.get(
          setService.call(putRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, setPutResponse.getStatus());
    }
  }

  private static void testGet(int rpcServerPort) throws InvalidProtocolBufferException {
    try (ProxyOnClient<DistkvService> setProxy = new ProxyOnClient<>(
        DistkvService.class, rpcServerPort)) {
      DistkvService setService = setProxy.getService();
      DistkvRequest request = DistkvRequest.newBuilder()
          .setKey("set_r_k1")
          .setRequestType(RequestType.SET_GET)
          .build();
      DistkvResponse setGetResponse = FutureUtils.get(
          setService.call(request));
      final List<String> results = ImmutableList.of("v1", "v2", "v3");
      Assert.assertEquals(CommonProtocol.Status.OK, setGetResponse.getStatus());
      Assert.assertEquals(results, setGetResponse.getResponse()
          .unpack(SetGetResponse.class).getValuesList());
    }

  }

  private static void testRemoveItem(int rpcServerPort) {
    try (ProxyOnClient<DistkvService> setProxy = new ProxyOnClient<>(
        DistkvService.class, rpcServerPort)) {
      DistkvService setService = setProxy.getService();
      SetProtocol.SetRemoveItemRequest.Builder setRemoveRequestBuilder =
          SetProtocol.SetRemoveItemRequest.newBuilder();
      setRemoveRequestBuilder.setItemValue("v1");
      DistkvRequest request = DistkvRequest.newBuilder()
          .setKey("set_r_k1")
          .setRequestType(RequestType.SET_REMOVE_ITEM)
          .setRequest(Any.pack(setRemoveRequestBuilder.build()))
          .build();
      DistkvResponse setDeleteResponse = FutureUtils.get(
          setService.call(request));
      Assert.assertEquals(CommonProtocol.Status.OK, setDeleteResponse.getStatus());
    }
  }

  private static void testDropByKey(int rpcServerPort) {
    try (ProxyOnClient<DistkvService> setProxy = new ProxyOnClient<>(
        DistkvService.class, rpcServerPort)) {
      DistkvService setService = setProxy.getService();

      DistkvRequest request = DistkvRequest.newBuilder()
          .setKey("set_r_k1")
          .setRequestType(RequestType.DROP)
          .build();
      DistkvResponse setDropByKeyResponse = FutureUtils.get(
          setService.call(request));

      Assert.assertEquals(CommonProtocol.Status.OK, setDropByKeyResponse.getStatus());
    }
  }

  private static void testExists(int rpcServerPort) {
    try (ProxyOnClient<DistkvService> setProxy = new ProxyOnClient<>(
        DistkvService.class, rpcServerPort)) {
      DistkvService setService = setProxy.getService();
      SetProtocol.SetExistsRequest.Builder setExistRequestBuilder =
          SetProtocol.SetExistsRequest.newBuilder();
      setExistRequestBuilder.setEntity("v1");

      DistkvRequest request = DistkvRequest.newBuilder()
          .setKey("set_r_k1")
          .setRequestType(RequestType.SET_EXISTS)
          .setRequest(Any.pack(setExistRequestBuilder.build()))
          .build();
      DistkvResponse setExistResponse = FutureUtils.get(
          setService.call(request));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, setExistResponse.getStatus());
    }
  }
}
