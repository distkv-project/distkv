package test.org.dst.server.service;

import com.google.common.collect.ImmutableList;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.ListProtocol;
import org.dst.server.service.DstListService;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.org.dst.supplier.BaseTestSupplier;
import test.org.dst.supplier.ListRpcTestUtil;
import test.org.dst.supplier.ProxyOnClient;

import java.util.ArrayList;
import java.util.List;

public class ListRpcTest extends BaseTestSupplier {

  private static List<String> dummyListTestData() {
    return ImmutableList.of("v0", "v1", "v2");
  }

  @Test
  public void testPutAndGet() {
    try (ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(DstListService.class)) {
      //put
      ListProtocol.PutRequest.Builder putRequest = ListRpcTestUtil.putRequestBuilder();
      List<String> values = dummyListTestData();
      putRequest.setKey("k1");
      values.forEach(value -> putRequest.addValues(value));
      ListProtocol.PutResponse putResponse =
            ListRpcTestUtil.putResponseBuilder(putRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      //get
      ListProtocol.GetRequest.Builder getRequest = ListRpcTestUtil.getRequestBuilder();
      getRequest.setKey("k1");
      ListProtocol.GetResponse getResponse =
            ListRpcTestUtil.getResponseBuilder(getRequest, proxy);
      Assert.assertEquals(dummyListTestData(), getResponse.getValuesList());
    }
  }

  @Test
  public void testDel() {
    try (ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(DstListService.class)) {
      //put
      ListProtocol.PutRequest.Builder putRequest = ListRpcTestUtil.putRequestBuilder();
      List<String> values = dummyListTestData();
      putRequest.setKey("k1");
      values.forEach(value -> putRequest.addValues(value));
      ListProtocol.PutResponse putResponse =
            ListRpcTestUtil.putResponseBuilder(putRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      //del
      ListProtocol.DelRequest.Builder delRequest = ListRpcTestUtil.delRequestBuilder();
      delRequest.setKey("k1");
      ListProtocol.DelResponse delResponse =
            ListRpcTestUtil.delResponseBuilder(delRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, delResponse.getStatus());

      //KEY_NOT_FOUND
      delRequest.setKey("k2");
      ListProtocol.DelResponse delResponse2 =
            ListRpcTestUtil.delResponseBuilder(delRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, delResponse2.getStatus());
    }
  }

  @Test
  public void testLPut() {
    try (ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(DstListService.class)) {
      //put
      ListProtocol.PutRequest.Builder putRequest = ListRpcTestUtil.putRequestBuilder();
      List<String> values = dummyListTestData();
      putRequest.setKey("k1");
      values.forEach(value -> putRequest.addValues(value));
      ListProtocol.PutResponse putResponse =
            ListRpcTestUtil.putResponseBuilder(putRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      //lput
      ListProtocol.LPutRequest.Builder lputRequest = ListRpcTestUtil.lputRequestBuilder();
      lputRequest.setKey("k1");
      List<String> valuesLput = new ArrayList<>();
      valuesLput.add("v3");
      valuesLput.add("v4");
      lputRequest.addAllValues(valuesLput);
      ListProtocol.LPutResponse lputResponse =
            ListRpcTestUtil.lputResponseBuilder(lputRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, lputResponse.getStatus());

      //get
      ListProtocol.GetRequest.Builder getRequest = ListRpcTestUtil.getRequestBuilder();
      getRequest.setKey("k1");
      ListProtocol.GetResponse getResponse =
            ListRpcTestUtil.getResponseBuilder(getRequest, proxy);
      Assert.assertEquals(ImmutableList.of("v3", "v4", "v0", "v1", "v2"),
            getResponse.getValuesList());

      //KEY_NOT_FOUND
      lputRequest.setKey("k2");
      List<String> valuesLput2 = new ArrayList<>();
      valuesLput2.add("v3");
      valuesLput2.add("v4");
      valuesLput2.forEach(value -> lputRequest.addValues(value));
      ListProtocol.LPutResponse lputResponse2 =
            ListRpcTestUtil.lputResponseBuilder(lputRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND,
            lputResponse2.getStatus());
    }
  }

  @Test
  public void testRPut() {
    try (ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(DstListService.class)) {
      //put
      ListProtocol.PutRequest.Builder putRequest = ListRpcTestUtil.putRequestBuilder();
      List<String> values = dummyListTestData();
      putRequest.setKey("k1");
      values.forEach(value -> putRequest.addValues(value));
      ListProtocol.PutResponse putResponse =
            ListRpcTestUtil.putResponseBuilder(putRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      //rput
      ListProtocol.RPutRequest.Builder rputRequest = ListRpcTestUtil.rputRequestBuilder();
      rputRequest.setKey("k1");
      List<String> valuesRput = new ArrayList<>();
      valuesRput.add("v3");
      valuesRput.add("v4");
      valuesRput.forEach(value -> rputRequest.addValues(value));
      ListProtocol.RPutResponse rputResponse =
            ListRpcTestUtil.rputResponseBuilder(rputRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, rputResponse.getStatus());

      //get
      ListProtocol.GetRequest.Builder getRequest = ListRpcTestUtil.getRequestBuilder();
      getRequest.setKey("k1");
      ListProtocol.GetResponse getResponse =
            ListRpcTestUtil.getResponseBuilder(getRequest, proxy);
      Assert.assertEquals(ImmutableList.of("v0", "v1", "v2", "v3", "v4"),
            getResponse.getValuesList());

      //KEY_NOT_FOUND
      rputRequest.setKey("k2");
      List<String> valuesRput2 = new ArrayList<>();
      valuesRput2.add("v3");
      valuesRput2.add("v4");
      valuesRput2.forEach(value -> rputRequest.addValues(value));
      ListProtocol.RPutResponse rputResponse2 =
            ListRpcTestUtil.rputResponseBuilder(rputRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, rputResponse2.getStatus());
    }
  }

  @Test
  public void testLDel() {
    try (ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(DstListService.class)) {
      //put
      ListProtocol.PutRequest.Builder putRequest = ListRpcTestUtil.putRequestBuilder();
      List<String> values = dummyListTestData();
      putRequest.setKey("k1");
      values.forEach(value -> putRequest.addValues(value));
      ListProtocol.PutResponse putResponse =
            ListRpcTestUtil.putResponseBuilder(putRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      //rdel
      ListProtocol.LDelRequest.Builder ldelRequest = ListRpcTestUtil.ldelRequestBuilder();
      ldelRequest.setKey("k1");
      ldelRequest.setValues(1);
      ListProtocol.LDelResponse ldelResponse =
            ListRpcTestUtil.ldelResponseBuilder(ldelRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, ldelResponse.getStatus());

      //get
      ListProtocol.GetRequest.Builder getRequest = ListRpcTestUtil.getRequestBuilder();
      getRequest.setKey("k1");
      ListProtocol.GetResponse getResponse =
            ListRpcTestUtil.getResponseBuilder(getRequest, proxy);
      Assert.assertEquals(ImmutableList.of("v1", "v2"), getResponse.getValuesList());

      //KEY_NOT_FOUND
      ldelRequest.setKey("k2");
      ldelRequest.setValues(1);
      ListProtocol.LDelResponse ldelResponse2 =
            ListRpcTestUtil.ldelResponseBuilder(ldelRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, ldelResponse2.getStatus());
    }
  }

  @Test
  public void testRDel() {
    try (ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(DstListService.class)) {
      //put
      ListProtocol.PutRequest.Builder putRequest = ListRpcTestUtil.putRequestBuilder();
      List<String> values = dummyListTestData();
      putRequest.setKey("k1");
      values.forEach(value -> putRequest.addValues(value));
      ListProtocol.PutResponse putResponse =
            ListRpcTestUtil.putResponseBuilder(putRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      //rdel
      ListProtocol.RDelRequest.Builder rdelRequest = ListRpcTestUtil.rdelRequestBuilder();
      rdelRequest.setKey("k1");
      rdelRequest.setValues(1);
      ListProtocol.RDelResponse rdelResponse =
            ListRpcTestUtil.rdelResponseBuilder(rdelRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, rdelResponse.getStatus());

      //get
      ListProtocol.GetRequest.Builder getRequest = ListRpcTestUtil.getRequestBuilder();
      getRequest.setKey("k1");
      ListProtocol.GetResponse getResponse =
            ListRpcTestUtil.getResponseBuilder(getRequest, proxy);
      Assert.assertEquals(ImmutableList.of("v0", "v1"), getResponse.getValuesList());

      //KEY_NOT_FOUND
      rdelRequest.setKey("k2");
      rdelRequest.setValues(1);
      ListProtocol.RDelResponse rdelResponse2 =
            ListRpcTestUtil.rdelResponseBuilder(rdelRequest, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, rdelResponse2.getStatus());
    }
  }
}
