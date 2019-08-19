package test.org.dst.rpc;

import com.google.common.collect.ImmutableList;
import junit.framework.Assert;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.SetProtocol;
import org.dst.server.service.DstSetService;
import org.junit.jupiter.api.Test;
import java.util.List;

public class SetRpcTest {

  @Test
  public void testSet() {
    TestUtil.startRpcServer();
    testPut();
    testGet();
    testDelete();
    testDropByKey();
    testExists();
    TestUtil.stopRpcServer();
  }

  public static void testPut() {
    try(ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
      DstSetService setService = setProxy.getService();
      SetProtocol.putRequest.Builder setPutRequestBuilder =
              SetProtocol.putRequest.newBuilder();
      setPutRequestBuilder.setKey("k1");
      final List<String> values = ImmutableList.of("v1", "v2", "v3", "v1");
      values.forEach(value -> setPutRequestBuilder.addValues(value));

      SetProtocol.putResponse setPutResponse =
              setService.put(setPutRequestBuilder.build());
      Assert.assertEquals(CommonProtocol.Status.OK, setPutResponse.getStatus());
    }
  }

  public static void testGet() {
    try(ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
      DstSetService setService = setProxy.getService();
      SetProtocol.getRequest.Builder setGetRequestBuilder =
              SetProtocol.getRequest.newBuilder();
      setGetRequestBuilder.setKey("k1");

      SetProtocol.getResponse setGetResponse =
              setService.get(setGetRequestBuilder.build());

      final List<String> results = ImmutableList.of("v1", "v2", "v3");
      Assert.assertEquals(CommonProtocol.Status.OK, setGetResponse.getStatus());
      Assert.assertEquals(results, setGetResponse.getValuesList());
    }

  }


  public static void testDelete() {
    try(ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
      DstSetService setService = setProxy.getService();
      SetProtocol.deleteRequest.Builder setDeleteRequestBuilder =
              SetProtocol.deleteRequest.newBuilder();
      setDeleteRequestBuilder.setKey("k1");
      setDeleteRequestBuilder.setEntity("v1");

      SetProtocol.deleteResponse setDeleteResponse =
              setService.delete(setDeleteRequestBuilder.build());

      Assert.assertEquals("OK", setDeleteResponse.getStatus().toString());
    }
  }


  public static void testDropByKey() {
    try(ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
      DstSetService setService = setProxy.getService();

      SetProtocol.dropByKeyRequest.Builder setDropByKeyRequestBuilder =
              SetProtocol.dropByKeyRequest.newBuilder();
      setDropByKeyRequestBuilder.setKey("k1");

      SetProtocol.dropByKeyResponse setDropByKeyResponse =
              setService.dropByKey(setDropByKeyRequestBuilder.build());

      Assert.assertEquals("OK", setDropByKeyResponse.getStatus().toString());
    }
  }

  public static void testExists() {
    try(ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
      DstSetService setService = setProxy.getService();
      SetProtocol.existsRequest.Builder setExistRequestBuilder =
              SetProtocol.existsRequest.newBuilder();
      setExistRequestBuilder.setKey("k1");
      setExistRequestBuilder.setEntity("v1");

      SetProtocol.existsResponse setExistResponse =
              setService.exists(setExistRequestBuilder.build());

      Assert.assertEquals("KEY_NOT_FOUND", setExistResponse.getStatus().toString());
    }
  }
}
