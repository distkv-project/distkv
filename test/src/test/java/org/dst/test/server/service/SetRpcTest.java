package org.dst.test.server.service;

import com.google.common.collect.ImmutableList;
import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.SetProtocol;
import org.dst.rpc.service.DstSetService;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.dst.test.supplier.BaseTestSupplier;
import org.dst.test.supplier.ProxyOnClient;
import java.util.List;

public class SetRpcTest extends BaseTestSupplier {

  @Test
  public void testSet() {
    // The following methods should be called as ordered
    // because some methods depends on other methods.
    testPut(rpcServerPort);
    testGet(rpcServerPort);
    testDelete(rpcServerPort);
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

      SetProtocol.PutResponse setPutResponse =
              setService.put(setPutRequestBuilder.build());
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

      SetProtocol.GetResponse setGetResponse =
              setService.get(setGetRequestBuilder.build());

      final List<String> results = ImmutableList.of("v1", "v2", "v3");
      Assert.assertEquals(CommonProtocol.Status.OK, setGetResponse.getStatus());
      Assert.assertEquals(results, setGetResponse.getValuesList());
    }

  }

  private static void testDelete(int rpcServerPort) {
    try (ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(
        DstSetService.class, rpcServerPort)) {
      DstSetService setService = setProxy.getService();
      SetProtocol.RemoveRequest.Builder setRemoveRequestBuilder =
              SetProtocol.RemoveRequest.newBuilder();
      setRemoveRequestBuilder.setKey("k1");
      setRemoveRequestBuilder.setEntity("v1");

      SetProtocol.RemoveResponse setDeleteResponse =
              setService.remove(setRemoveRequestBuilder.build());

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

      CommonProtocol.DropResponse setDropByKeyResponse =
              setService.drop(setDropByKeyRequestBuilder.build());

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

      SetProtocol.ExistsResponse setExistResponse =
              setService.exists(setExistRequestBuilder.build());
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, setExistResponse.getStatus());
    }
  }
}
