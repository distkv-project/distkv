package com.distkv.dst.test.server.service;

import com.google.common.collect.ImmutableList;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.ListProtocol;
import com.distkv.dst.rpc.service.DstListService;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.distkv.dst.test.supplier.BaseTestSupplier;
import com.distkv.dst.test.supplier.ListRpcTestUtil;
import com.distkv.dst.test.supplier.ProxyOnClient;
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
  public void testDrop() {
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

      //drop
      CommonProtocol.DropRequest.Builder dropRequestBuilder = ListRpcTestUtil.dropRequestBuilder();
      dropRequestBuilder.setKey("k1");
      CommonProtocol.DropResponse delResponseBuilder =
            ListRpcTestUtil.dropResponseBuilder(dropRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, delResponseBuilder.getStatus());

      //KEY_NOT_FOUND
      dropRequestBuilder.setKey("k2");
      CommonProtocol.DropResponse delResponse2Builder =
            ListRpcTestUtil.dropResponseBuilder(dropRequestBuilder, proxy);
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
  public void testRemove() {
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

      //removeOne
      ListProtocol.RemoveRequest.Builder removeOneRequestBuilder =
              ListRpcTestUtil.removeRequestBuilder();
      removeOneRequestBuilder.setType(ListProtocol.RemoveType.RemoveOne);
      removeOneRequestBuilder.setKey("k1");
      removeOneRequestBuilder.setIndex(1);
      ListProtocol.RemoveResponse removeOneResponseBuilder =
            ListRpcTestUtil.removeResponseBuilder(removeOneRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, removeOneResponseBuilder.getStatus());

      //get
      ListProtocol.GetRequest.Builder getRequestBuilder = ListRpcTestUtil.getRequestBuilder();
      getRequestBuilder.setKey("k1");
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      ListProtocol.GetResponse getResponseBuilder =
            ListRpcTestUtil.getResponseBuilder(getRequestBuilder, proxy);
      Assert.assertEquals(ImmutableList.of("v0", "v2"), getResponseBuilder.getValuesList());

      //removeOne
      ListProtocol.RemoveRequest.Builder removeRangeRequestBuilder =
              ListRpcTestUtil.removeRequestBuilder();
      removeRangeRequestBuilder.setType(ListProtocol.RemoveType.RemoveRange);
      removeRangeRequestBuilder.setKey("k1");
      removeRangeRequestBuilder.setFrom(0);
      removeRangeRequestBuilder.setEnd(1);
      ListProtocol.RemoveResponse removeRangeResponseBuilder =
              ListRpcTestUtil.removeResponseBuilder(removeRangeRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, removeRangeResponseBuilder.getStatus());

      //KEY_NOT_FOUND
      removeRangeRequestBuilder.setKey("k2");
      removeRangeRequestBuilder.setIndex(1);
      ListProtocol.RemoveResponse removeResponse2Builder =
            ListRpcTestUtil.removeResponseBuilder(removeRangeRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, removeResponse2Builder.getStatus());
    }
  }

  @Test
  public void testMRemove() {
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

      //mRemove
      ListProtocol.MRemoveRequest.Builder multipleRemoveRequestBuilder =
              ListRpcTestUtil.multipleRemoveRequestBuilder();
      multipleRemoveRequestBuilder.setKey("k1");
      multipleRemoveRequestBuilder.addIndexes(1);
      multipleRemoveRequestBuilder.addIndexes(0);
      ListProtocol.MRemoveResponse multipleRemoveResponseBuilder =
            ListRpcTestUtil.multipleRemoveResponseBuilder(multipleRemoveRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, multipleRemoveResponseBuilder.getStatus());

      //get
      ListProtocol.GetRequest.Builder getRequestBuilder = ListRpcTestUtil.getRequestBuilder();
      getRequestBuilder.setKey("k1");
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      ListProtocol.GetResponse getResponseBuilder =
            ListRpcTestUtil.getResponseBuilder(getRequestBuilder, proxy);
      Assert.assertEquals(ImmutableList.of("v2"), getResponseBuilder.getValuesList());

      //KEY_NOT_FOUND
      multipleRemoveRequestBuilder.setKey("k2");
      multipleRemoveRequestBuilder.addIndexes(1);
      ListProtocol.MRemoveResponse multipleRemoveResponse2Builder =
            ListRpcTestUtil.multipleRemoveResponseBuilder(multipleRemoveRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND,
              multipleRemoveResponse2Builder.getStatus());
    }
  }
}
