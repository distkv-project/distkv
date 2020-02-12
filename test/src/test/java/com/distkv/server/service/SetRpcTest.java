package com.distkv.server.service;

import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.service.DistkvSetService;
import com.distkv.supplier.BaseTestSupplier;
import com.distkv.supplier.ProxyOnClient;
import com.google.common.collect.ImmutableList;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SetRpcTest extends BaseTestSupplier {

  @Test
  public void testSet() {
    // The following methods should be called as ordered
    // because some methods depend on other methods.
    testPut(rpcServerPort);
    testGet(rpcServerPort);
    testRemoveItem(rpcServerPort);
    testDropByKey(rpcServerPort);
    testExists(rpcServerPort);
  }

  private static void testPut(int rpcServerPort) {
    try (ProxyOnClient<DistkvSetService> setProxy = new ProxyOnClient<>(
        DistkvSetService.class, rpcServerPort)) {
      DistkvSetService setService = setProxy.getService();
      SetProtocol.PutRequest.Builder setPutRequestBuilder =
              SetProtocol.PutRequest.newBuilder();
      setPutRequestBuilder.setKey("k1");
      final List<String> values = ImmutableList.of("v1", "v2", "v3", "v1");
      values.forEach(value -> setPutRequestBuilder.addValues(value));

      SetProtocol.PutResponse setPutResponse = FutureUtils.get(
          setService.put(setPutRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, setPutResponse.getStatus());
    }
  }

  private static void testGet(int rpcServerPort) {
    try (ProxyOnClient<DistkvSetService> setProxy = new ProxyOnClient<>(
        DistkvSetService.class, rpcServerPort)) {
      DistkvSetService setService = setProxy.getService();
      SetProtocol.GetRequest.Builder setGetRequestBuilder =
              SetProtocol.GetRequest.newBuilder();
      setGetRequestBuilder.setKey("k1");

      SetProtocol.GetResponse setGetResponse = FutureUtils.get(
          setService.get(setGetRequestBuilder.build()));
      final List<String> results = ImmutableList.of("v1", "v2", "v3");
      Assert.assertEquals(CommonProtocol.Status.OK, setGetResponse.getStatus());
      Assert.assertEquals(results, setGetResponse.getValuesList());
    }

  }

  private static void testRemoveItem(int rpcServerPort) {
    try (ProxyOnClient<DistkvSetService> setProxy = new ProxyOnClient<>(
        DistkvSetService.class, rpcServerPort)) {
      DistkvSetService setService = setProxy.getService();
      SetProtocol.RemoveItemRequest.Builder setRemoveRequestBuilder =
              SetProtocol.RemoveItemRequest.newBuilder();
      setRemoveRequestBuilder.setKey("k1");
      setRemoveRequestBuilder.setItemValue("v1");

      SetProtocol.RemoveItemResponse setDeleteResponse = FutureUtils.get(
          setService.removeItem(setRemoveRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, setDeleteResponse.getStatus());
    }
  }

  private static void testDropByKey(int rpcServerPort) {
    try (ProxyOnClient<DistkvSetService> setProxy = new ProxyOnClient<>(
        DistkvSetService.class, rpcServerPort)) {
      DistkvSetService setService = setProxy.getService();

      CommonProtocol.DropRequest.Builder setDropByKeyRequestBuilder =
              CommonProtocol.DropRequest.newBuilder();
      setDropByKeyRequestBuilder.setKey("k1");

      CommonProtocol.DropResponse setDropByKeyResponse = FutureUtils.get(
          setService.drop(setDropByKeyRequestBuilder.build()));

      Assert.assertEquals(CommonProtocol.Status.OK, setDropByKeyResponse.getStatus());
    }
  }

  private static void testExists(int rpcServerPort) {
    try (ProxyOnClient<DistkvSetService> setProxy = new ProxyOnClient<>(
        DistkvSetService.class, rpcServerPort)) {
      DistkvSetService setService = setProxy.getService();
      SetProtocol.ExistsRequest.Builder setExistRequestBuilder =
              SetProtocol.ExistsRequest.newBuilder();
      setExistRequestBuilder.setKey("k1");
      setExistRequestBuilder.setEntity("v1");

      SetProtocol.ExistsResponse setExistResponse = FutureUtils.get(
          setService.exists(setExistRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, setExistResponse.getStatus());
    }
  }
}
