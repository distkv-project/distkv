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

  public static void testGet() {
    try(ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
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


  public static void testDelete() {
    try(ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
      DstSetService setService = setProxy.getService();
      SetProtocol.DeleteRequest.Builder setDeleteRequestBuilder =
              SetProtocol.DeleteRequest.newBuilder();
      setDeleteRequestBuilder.setKey("k1");
      setDeleteRequestBuilder.setEntity("v1");

      SetProtocol.DeleteResponse setDeleteResponse =
              setService.delete(setDeleteRequestBuilder.build());

      Assert.assertEquals("OK", setDeleteResponse.getStatus().toString());
    }
  }


  public static void testDropByKey() {
    try(ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
      DstSetService setService = setProxy.getService();

      SetProtocol.DropByKeyRequest.Builder setDropByKeyRequestBuilder =
              SetProtocol.DropByKeyRequest.newBuilder();
      setDropByKeyRequestBuilder.setKey("k1");

      SetProtocol.DropByKeyResponse setDropByKeyResponse =
              setService.dropByKey(setDropByKeyRequestBuilder.build());

      Assert.assertEquals("OK", setDropByKeyResponse.getStatus().toString());
    }
  }

  public static void testExists() {
    try(ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
      DstSetService setService = setProxy.getService();
      SetProtocol.ExistsRequest.Builder setExistRequestBuilder =
              SetProtocol.ExistsRequest.newBuilder();
      setExistRequestBuilder.setKey("k1");
      setExistRequestBuilder.setEntity("v1");

      SetProtocol.ExistsResponse setExistResponse =
              setService.exists(setExistRequestBuilder.build());

      Assert.assertFalse(setExistResponse.getResult());
    }
  }
}
