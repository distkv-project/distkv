package com.distkv.dst.test.server.service;

import com.distkv.dst.common.utils.Utils;
import com.google.common.collect.ImmutableList;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;
import com.distkv.dst.rpc.service.DstSetService;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.distkv.dst.test.supplier.BaseTestSupplier;
import com.distkv.dst.test.supplier.ProxyOnClient;
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
    try (ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(
        DstSetService.class, rpcServerPort)) {
      DstSetService setService = setProxy.getService();
      SetProtocol.PutRequest.Builder setPutRequestBuilder =
              SetProtocol.PutRequest.newBuilder();
      setPutRequestBuilder.setKey("k1");
      final List<String> values = ImmutableList.of("v1", "v2", "v3", "v1");
      values.forEach(value -> setPutRequestBuilder.addValues(value));

      SetProtocol.PutResponse setPutResponse = Utils.getFromFuture(
          setService.put(setPutRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, setPutResponse.getStatus());
    }
  }

  private static void testGet(int rpcServerPort) {
    try (ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(
        DstSetService.class, rpcServerPort)) {
      DstSetService setService = setProxy.getService();
      SetProtocol.GetRequest.Builder setGetRequestBuilder =
              SetProtocol.GetRequest.newBuilder();
      setGetRequestBuilder.setKey("k1");

      SetProtocol.GetResponse setGetResponse = Utils.getFromFuture(
          setService.get(setGetRequestBuilder.build()));
      final List<String> results = ImmutableList.of("v1", "v2", "v3");
      Assert.assertEquals(CommonProtocol.Status.OK, setGetResponse.getStatus());
      Assert.assertEquals(results, setGetResponse.getValuesList());
    }

  }

  private static void testRemoveItem(int rpcServerPort) {
    try (ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(
        DstSetService.class, rpcServerPort)) {
      DstSetService setService = setProxy.getService();
      SetProtocol.RemoveItemRequest.Builder setRemoveRequestBuilder =
              SetProtocol.RemoveItemRequest.newBuilder();
      setRemoveRequestBuilder.setKey("k1");
      setRemoveRequestBuilder.setItemValue("v1");

      SetProtocol.RemoveItemResponse setDeleteResponse = Utils.getFromFuture(
          setService.removeItem(setRemoveRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, setDeleteResponse.getStatus());
    }
  }

  private static void testDropByKey(int rpcServerPort) {
    try (ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(
        DstSetService.class, rpcServerPort)) {
      DstSetService setService = setProxy.getService();

      CommonProtocol.DropRequest.Builder setDropByKeyRequestBuilder =
              CommonProtocol.DropRequest.newBuilder();
      setDropByKeyRequestBuilder.setKey("k1");

      CommonProtocol.DropResponse setDropByKeyResponse = Utils.getFromFuture(
          setService.drop(setDropByKeyRequestBuilder.build()));

      Assert.assertEquals(CommonProtocol.Status.OK, setDropByKeyResponse.getStatus());
    }
  }

  private static void testExists(int rpcServerPort) {
    try (ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(
        DstSetService.class, rpcServerPort)) {
      DstSetService setService = setProxy.getService();
      SetProtocol.ExistsRequest.Builder setExistRequestBuilder =
              SetProtocol.ExistsRequest.newBuilder();
      setExistRequestBuilder.setKey("k1");
      setExistRequestBuilder.setEntity("v1");

      SetProtocol.ExistsResponse setExistResponse = Utils.getFromFuture(
          setService.exists(setExistRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, setExistResponse.getStatus());
    }
  }
}
