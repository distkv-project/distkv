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

      //drop
      CommonProtocol.DropRequest.Builder delRequestBuilder = ListRpcTestUtil.dropRequestBuilder();
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

      //delete
      ListProtocol.DeleteRequest.Builder deleteRequestBuilder =
              ListRpcTestUtil.deleteRequestBuilder();
      deleteRequestBuilder.setType(ListProtocol.DeleteType.DeleteOne);
      deleteRequestBuilder.setKey("k1");
      deleteRequestBuilder.setIndex(1);
      ListProtocol.DeleteResponse deleteResponseBuilder =
            ListRpcTestUtil.deleteResponseBuilder(deleteRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, deleteResponseBuilder.getStatus());

      //get
      ListProtocol.GetRequest.Builder getRequestBuilder = ListRpcTestUtil.getRequestBuilder();
      getRequestBuilder.setKey("k1");
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      ListProtocol.GetResponse getResponseBuilder =
            ListRpcTestUtil.getResponseBuilder(getRequestBuilder, proxy);
      Assert.assertEquals(ImmutableList.of("v0", "v2"), getResponseBuilder.getValuesList());

      //KEY_NOT_FOUND
      deleteRequestBuilder.setKey("k2");
      deleteRequestBuilder.setIndex(1);
      ListProtocol.DeleteResponse deleteResponse2Builder =
            ListRpcTestUtil.deleteResponseBuilder(deleteRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, deleteResponse2Builder.getStatus());
    }
  }

  @Test
  public void testMDelete() {
    //TODO(LCM):exist problem
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

      //mdelete
      ListProtocol.MDeleteRequest.Builder mdeleteRequestBuilder =
              ListRpcTestUtil.mdeleteRequestBuilder();
      mdeleteRequestBuilder.setKey("k1");
      mdeleteRequestBuilder.addIndex(1);
      ListProtocol.MDeleteResponse mdeleteResponseBuilder =
            ListRpcTestUtil.mdeleteResponseBuilder(mdeleteRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.OK, mdeleteResponseBuilder.getStatus());

      //get
      ListProtocol.GetRequest.Builder getRequestBuilder = ListRpcTestUtil.getRequestBuilder();
      getRequestBuilder.setKey("k1");
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      ListProtocol.GetResponse getResponseBuilder =
            ListRpcTestUtil.getResponseBuilder(getRequestBuilder, proxy);
      Assert.assertEquals(ImmutableList.of("v0", "v2"), getResponseBuilder.getValuesList());

      //KEY_NOT_FOUND
      mdeleteRequestBuilder.setKey("k2");
      mdeleteRequestBuilder.addIndex(1);
      ListProtocol.MDeleteResponse mdeleteResponse2Builder =
            ListRpcTestUtil.mdeleteResponseBuilder(mdeleteRequestBuilder, proxy);
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, mdeleteResponse2Builder.getStatus());
    }
  }
}
