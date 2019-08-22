package test.org.dst.server.service;

import com.google.common.collect.ImmutableList;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.SetProtocol;
import org.dst.server.service.DstSetService;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.org.dst.supplier.BaseTestSupplier;
import test.org.dst.supplier.ProxyOnClient;
import java.util.List;

public class SetRpcTest extends BaseTestSupplier {

  @Test
  public void testSet() {
    // The following methods should be called as ordered
    // because some methods depends on other methods.
    testPut();
    testGet();
    testDelete();
    testDropByKey();
    testExists();
  }

  private static void testPut() {
    try (ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
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

  private static void testGet() {
    try (ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
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

  private static void testDelete() {
    try (ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
      DstSetService setService = setProxy.getService();
      SetProtocol.DeleteRequest.Builder setDeleteRequestBuilder =
              SetProtocol.DeleteRequest.newBuilder();
      setDeleteRequestBuilder.setKey("k1");
      setDeleteRequestBuilder.setEntity("v1");

      SetProtocol.DeleteResponse setDeleteResponse =
              setService.delete(setDeleteRequestBuilder.build());

      Assert.assertEquals(CommonProtocol.Status.OK, setDeleteResponse.getStatus());
    }
  }

  private static void testDropByKey() {
    try (ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
      DstSetService setService = setProxy.getService();

      SetProtocol.DropByKeyRequest.Builder setDropByKeyRequestBuilder =
              SetProtocol.DropByKeyRequest.newBuilder();
      setDropByKeyRequestBuilder.setKey("k1");

      SetProtocol.DropByKeyResponse setDropByKeyResponse =
              setService.dropByKey(setDropByKeyRequestBuilder.build());

      Assert.assertEquals(CommonProtocol.Status.OK, setDropByKeyResponse.getStatus());
    }
  }

  private static void testExists() {
    try (ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
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
