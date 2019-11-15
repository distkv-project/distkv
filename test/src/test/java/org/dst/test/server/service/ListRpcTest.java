package org.dst.test.server.service;

import com.google.common.collect.ImmutableList;
import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.ListProtocol;
import org.dst.rpc.service.DstListService;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.dst.test.supplier.BaseTestSupplier;
import org.dst.test.supplier.ListRpcTestUtil;
import org.dst.test.supplier.ProxyOnClient;
import java.util.ArrayList;
import java.util.List;

public class ListRpcTest extends BaseTestSupplier {

  private static List<String> dummyListTestData() {
    return ImmutableList.of("v0", "v1", "v2");
  }

  @Test
  public void testPutAndGet() {
    try (ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(
        DstListService.class, rpcServerPort)) {
      //put
      ListProtocol.PutRequest.Builder putRequestBuilder = ListRpcTestUtil.putRequestBuilder();
      List<String> values = dummyListTestData();
      putRequestBuilder.setKey("k1");
      values.forEach(value -> putRequestBuilder.addValues(value));
      ListProtocol.PutResponse putResponseBuilder =
            ListRpcTestUtil.putResponseBuilder(putRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, putResponseBuilder.getStatus());

      //get
      ListProtocol.GetRequest.Builder getRequestBuilder = ListRpcTestUtil.getRequestBuilder();
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      getRequestBuilder.setKey("k1");
      ListProtocol.GetResponse getResponseBuilder =
            ListRpcTestUtil.getResponseBuilder(getRequestBuilder, proxy);
      Assert.assertEquals(dummyListTestData(), getResponseBuilder.getValuesList());

      //get
      ListProtocol.GetRequest.Builder getRequest2Builder = ListRpcTestUtil.getRequestBuilder();
      getRequest2Builder.setKey("k2");
      getRequest2Builder.setType(ListProtocol.GetType.GET_ALL);
      ListProtocol.GetResponse getResponse2Builder =
              ListRpcTestUtil.getResponseBuilder(getRequest2Builder, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, getResponse2Builder.getStatus());

    }
  }

  @Test
  public void testDel() {
    try (ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(
        DstListService.class, rpcServerPort)) {
      //put
      ListProtocol.PutRequest.Builder putRequestBuilder = ListRpcTestUtil.putRequestBuilder();
      List<String> values = dummyListTestData();
      putRequestBuilder.setKey("k1");
      values.forEach(value -> putRequestBuilder.addValues(value));
      ListProtocol.PutResponse putResponseBuilder =
            ListRpcTestUtil.putResponseBuilder(putRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, putResponseBuilder.getStatus());

      //del
      CommonProtocol.DropRequest.Builder delRequestBuilder = ListRpcTestUtil.delRequestBuilder();
      delRequestBuilder.setKey("k1");
      CommonProtocol.DropResponse delResponseBuilder =
            ListRpcTestUtil.delResponseBuilder(delRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, delResponseBuilder.getStatus());

      //KEY_NOT_FOUND
      delRequestBuilder.setKey("k2");
      CommonProtocol.DropResponse delResponse2Builder =
            ListRpcTestUtil.delResponseBuilder(delRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, delResponse2Builder.getStatus());
    }
  }

  @Test
  public void testLPut() {
    try (ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(
        DstListService.class, rpcServerPort)) {
      //put
      ListProtocol.PutRequest.Builder putRequestBuilder = ListRpcTestUtil.putRequestBuilder();
      List<String> values = dummyListTestData();
      putRequestBuilder.setKey("k1");
      values.forEach(value -> putRequestBuilder.addValues(value));
      ListProtocol.PutResponse putResponseBuilder =
            ListRpcTestUtil.putResponseBuilder(putRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, putResponseBuilder.getStatus());

      //lput
      ListProtocol.LPutRequest.Builder lputRequestBuilder = ListRpcTestUtil.lputRequestBuilder();
      lputRequestBuilder.setKey("k1");
      List<String> valuesLput = new ArrayList<>();
      valuesLput.add("v3");
      valuesLput.add("v4");
      lputRequestBuilder.addAllValues(valuesLput);
      ListProtocol.LPutResponse lputResponseBuilder =
            ListRpcTestUtil.lputResponseBuilder(lputRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, lputResponseBuilder.getStatus());

      //get
      ListProtocol.GetRequest.Builder getRequestBuilder = ListRpcTestUtil.getRequestBuilder();
      getRequestBuilder.setKey("k1");
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      ListProtocol.GetResponse getResponseBuilder =
            ListRpcTestUtil.getResponseBuilder(getRequestBuilder, proxy);
      Assert.assertEquals(ImmutableList.of("v3", "v4", "v0", "v1", "v2"),
            getResponseBuilder.getValuesList());

      //KEY_NOT_FOUND
      lputRequestBuilder.setKey("k2");
      List<String> valuesLput2 = new ArrayList<>();
      valuesLput2.add("v3");
      valuesLput2.add("v4");
      valuesLput2.forEach(value -> lputRequestBuilder.addValues(value));
      ListProtocol.LPutResponse lputResponse2Builder =
            ListRpcTestUtil.lputResponseBuilder(lputRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND,
            lputResponse2Builder.getStatus());
    }
  }

  @Test
  public void testRPut() {
    try (ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(
        DstListService.class, rpcServerPort)) {
      //put
      ListProtocol.PutRequest.Builder putRequestBuilder = ListRpcTestUtil.putRequestBuilder();
      List<String> values = dummyListTestData();
      putRequestBuilder.setKey("k1");
      values.forEach(value -> putRequestBuilder.addValues(value));
      ListProtocol.PutResponse putResponseBuilder =
            ListRpcTestUtil.putResponseBuilder(putRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, putResponseBuilder.getStatus());

      //rput
      ListProtocol.RPutRequest.Builder rputRequestBuilder = ListRpcTestUtil.rputRequestBuilder();
      rputRequestBuilder.setKey("k1");
      List<String> valuesRput = new ArrayList<>();
      valuesRput.add("v3");
      valuesRput.add("v4");
      valuesRput.forEach(value -> rputRequestBuilder.addValues(value));
      ListProtocol.RPutResponse rputResponseBuilder =
            ListRpcTestUtil.rputResponseBuilder(rputRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, rputResponseBuilder.getStatus());

      //get
      ListProtocol.GetRequest.Builder getRequestBuilder = ListRpcTestUtil.getRequestBuilder();
      getRequestBuilder.setKey("k1");
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      ListProtocol.GetResponse getResponseBuilder =
            ListRpcTestUtil.getResponseBuilder(getRequestBuilder, proxy);
      Assert.assertEquals(ImmutableList.of("v0", "v1", "v2", "v3", "v4"),
            getResponseBuilder.getValuesList());

      //KEY_NOT_FOUND
      rputRequestBuilder.setKey("k2");
      List<String> valuesRput2 = new ArrayList<>();
      valuesRput2.add("v3");
      valuesRput2.add("v4");
      valuesRput2.forEach(value -> rputRequestBuilder.addValues(value));
      ListProtocol.RPutResponse rputResponse2Builder =
            ListRpcTestUtil.rputResponseBuilder(rputRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, rputResponse2Builder.getStatus());
    }
  }

  @Test
  public void testDelete() {
    try (ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(
        DstListService.class, rpcServerPort)) {
      //put
      ListProtocol.PutRequest.Builder putRequestBuilder = ListRpcTestUtil.putRequestBuilder();
      List<String> values = dummyListTestData();
      putRequestBuilder.setKey("k1");
      values.forEach(value -> putRequestBuilder.addValues(value));
      ListProtocol.PutResponse putResponseBuilder =
            ListRpcTestUtil.putResponseBuilder(putRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, putResponseBuilder.getStatus());

      //rdel
      ListProtocol.DeleteRequest.Builder ldelRequestBuilder = ListRpcTestUtil.ldelRequestBuilder();
      ldelRequestBuilder.setKey("k1");
      ldelRequestBuilder.setIndex(1);
      ListProtocol.DeleteResponse ldelResponseBuilder =
            ListRpcTestUtil.ldelResponseBuilder(ldelRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, ldelResponseBuilder.getStatus());

      //get
      ListProtocol.GetRequest.Builder getRequestBuilder = ListRpcTestUtil.getRequestBuilder();
      getRequestBuilder.setKey("k1");
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      ListProtocol.GetResponse getResponseBuilder =
            ListRpcTestUtil.getResponseBuilder(getRequestBuilder, proxy);
      Assert.assertEquals(ImmutableList.of("v1", "v2"), getResponseBuilder.getValuesList());

      //KEY_NOT_FOUND
      ldelRequestBuilder.setKey("k2");
      ldelRequestBuilder.setIndex(1);
      ListProtocol.DeleteResponse ldelResponse2Builder =
            ListRpcTestUtil.ldelResponseBuilder(ldelRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, ldelResponse2Builder.getStatus());
    }
  }

  @Test
  public void testMDelete() {
    try (ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(
        DstListService.class, rpcServerPort)) {
      //put
      ListProtocol.PutRequest.Builder putRequestBuilder = ListRpcTestUtil.putRequestBuilder();
      List<String> values = dummyListTestData();
      putRequestBuilder.setKey("k1");
      values.forEach(value -> putRequestBuilder.addValues(value));
      ListProtocol.PutResponse putResponseBuilder =
            ListRpcTestUtil.putResponseBuilder(putRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, putResponseBuilder.getStatus());

      //rdel
      ListProtocol.MDeleteRequest.Builder rdelRequestBuilder = ListRpcTestUtil.rdelRequestBuilder();
      rdelRequestBuilder.setKey("k1");
      rdelRequestBuilder.addIndex(1);
      ListProtocol.MDeleteResponse rdelResponseBuilder =
            ListRpcTestUtil.rdelResponseBuilder(rdelRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, rdelResponseBuilder.getStatus());

      //get
      ListProtocol.GetRequest.Builder getRequestBuilder = ListRpcTestUtil.getRequestBuilder();
      getRequestBuilder.setKey("k1");
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      ListProtocol.GetResponse getResponseBuilder =
            ListRpcTestUtil.getResponseBuilder(getRequestBuilder, proxy);
      Assert.assertEquals(ImmutableList.of("v0", "v1"), getResponseBuilder.getValuesList());

      //KEY_NOT_FOUND
      rdelRequestBuilder.setKey("k2");
      rdelRequestBuilder.addIndex(1);
      ListProtocol.MDeleteResponse rdelResponse2Builder =
            ListRpcTestUtil.rdelResponseBuilder(rdelRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, rdelResponse2Builder.getStatus());
    }
  }
}
