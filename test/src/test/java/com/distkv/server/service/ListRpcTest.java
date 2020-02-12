package com.distkv.server.service;

import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.service.DistkvListService;
import com.distkv.supplier.BaseTestSupplier;
import com.distkv.supplier.ProxyOnClient;
import com.google.common.collect.ImmutableList;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ListRpcTest extends BaseTestSupplier {

  private static List<String> dummyListTestData() {
    return ImmutableList.of("v0", "v1", "v2");
  }

  @Test
  public void testPutAndGet() {
    try (ProxyOnClient<DistkvListService> listProxy = new ProxyOnClient<>(
        DistkvListService.class, rpcServerPort)) {
      final DistkvListService listService = listProxy.getService();

      // Put.
      ListProtocol.PutRequest.Builder putRequestBuilder = ListProtocol.PutRequest.newBuilder();
      List<String> values = dummyListTestData();
      putRequestBuilder.setKey("k1");
      values.forEach(value -> putRequestBuilder.addValues(value));
      ListProtocol.PutResponse response = FutureUtils.get(
          listService.put(putRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, response.getStatus());

      // Get.
      ListProtocol.GetRequest.Builder getRequestBuilder = ListProtocol.GetRequest.newBuilder();
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      getRequestBuilder.setKey("k1");
      ListProtocol.GetResponse getResponse = FutureUtils.get(
          listService.get(getRequestBuilder.build()));
      Assert.assertEquals(dummyListTestData(), getResponse.getValuesList());

      // Get a non-exist key.
      ListProtocol.GetRequest.Builder getRequest2Builder = ListProtocol.GetRequest.newBuilder();
      getRequest2Builder.setKey("k2");
      getRequest2Builder.setType(ListProtocol.GetType.GET_ALL);
      ListProtocol.GetResponse getResponse2 = FutureUtils.get(
          listService.get(getRequest2Builder.build()));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, getResponse2.getStatus());
    }
  }

  @Test
  public void testDrop() {
    try (ProxyOnClient<DistkvListService> listProxy = new ProxyOnClient<>(
        DistkvListService.class, rpcServerPort)) {
      final DistkvListService listService = listProxy.getService();

      // Put.
      ListProtocol.PutRequest.Builder putRequestBuilder = ListProtocol.PutRequest.newBuilder();
      List<String> values = dummyListTestData();
      putRequestBuilder.setKey("k1");
      values.forEach(value -> putRequestBuilder.addValues(value));
      ListProtocol.PutResponse putResponse = FutureUtils.get(
          listService.put(putRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      // Drop.
      CommonProtocol.DropRequest.Builder dropRequestBuilder
          = CommonProtocol.DropRequest.newBuilder();
      dropRequestBuilder.setKey("k1");

      CommonProtocol.DropResponse dropResponse = FutureUtils.get(
          listService.drop(dropRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, dropResponse.getStatus());

      // Test drop a non-exist key.
      dropRequestBuilder.setKey("k2");
      CommonProtocol.DropResponse dropResponse2 = FutureUtils.get(
          listService.drop(dropRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, dropResponse2.getStatus());
    }
  }

  @Test
  public void testLPut() {
    try (ProxyOnClient<DistkvListService> listProxy = new ProxyOnClient<>(
        DistkvListService.class, rpcServerPort)) {
      final DistkvListService listService = listProxy.getService();

      // Put.
      ListProtocol.PutRequest.Builder putRequestBuilder
          = ListProtocol.PutRequest.newBuilder();
      List<String> values = dummyListTestData();
      putRequestBuilder.setKey("k1");
      values.forEach(value -> putRequestBuilder.addValues(value));
      ListProtocol.PutResponse putResponse = FutureUtils.get(
          listService.put(putRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      // LPut.
      ListProtocol.LPutRequest.Builder lputRequestBuilder
          = ListProtocol.LPutRequest.newBuilder();
      lputRequestBuilder.setKey("k1");
      List<String> valuesLput = new ArrayList<>();
      valuesLput.add("v3");
      valuesLput.add("v4");
      lputRequestBuilder.addAllValues(valuesLput);
      ListProtocol.LPutResponse lputResponse = FutureUtils.get(
          listService.lput(lputRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, lputResponse.getStatus());

      // Get.
      ListProtocol.GetRequest.Builder getRequestBuilder
          = ListProtocol.GetRequest.newBuilder();
      getRequestBuilder.setKey("k1");
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      ListProtocol.GetResponse getResponse = FutureUtils.get(
          listService.get(getRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, getResponse.getStatus());
      Assert.assertEquals(ImmutableList.of("v3", "v4", "v0", "v1", "v2"),
          getResponse.getValuesList());

      // Test lput a non-exist key.
      lputRequestBuilder.setKey("k2");
      List<String> valuesLput2 = new ArrayList<>();
      valuesLput2.add("v3");
      valuesLput2.add("v4");
      valuesLput2.forEach(value -> lputRequestBuilder.addValues(value));

      ListProtocol.LPutResponse lputResponse2 = FutureUtils.get(
          listService.lput(lputRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, lputResponse2.getStatus());
    }
  }

  @Test
  public void testRPut() {
    try (ProxyOnClient<DistkvListService> listProxy = new ProxyOnClient<>(
        DistkvListService.class, rpcServerPort)) {
      final DistkvListService listService = listProxy.getService();

      // Put.
      ListProtocol.PutRequest.Builder putRequestBuilder
          = ListProtocol.PutRequest.newBuilder();
      List<String> values = dummyListTestData();
      putRequestBuilder.setKey("k1");
      values.forEach(value -> putRequestBuilder.addValues(value));

      ListProtocol.PutResponse putResponse = FutureUtils.get(
          listService.put(putRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      // RPut.
      ListProtocol.RPutRequest.Builder rputRequestBuilder
          = ListProtocol.RPutRequest.newBuilder();
      rputRequestBuilder.setKey("k1");
      List<String> valuesRput = new ArrayList<>();
      valuesRput.add("v3");
      valuesRput.add("v4");
      valuesRput.forEach(value -> rputRequestBuilder.addValues(value));

      ListProtocol.RPutResponse rputResponse = FutureUtils.get(
          listService.rput(rputRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, rputResponse.getStatus());

      // Get.
      ListProtocol.GetRequest.Builder getRequestBuilder
          = ListProtocol.GetRequest.newBuilder();
      getRequestBuilder.setKey("k1");
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);

      ListProtocol.GetResponse getResponse = FutureUtils.get(
          listService.get(getRequestBuilder.build()));
      Assert.assertEquals(ImmutableList.of("v0", "v1", "v2", "v3", "v4"),
          getResponse.getValuesList());

      // Test rput a non-exist key.
      rputRequestBuilder.setKey("k2");
      List<String> valuesRput2 = new ArrayList<>();
      valuesRput2.add("v3");
      valuesRput2.add("v4");
      valuesRput2.forEach(value -> rputRequestBuilder.addValues(value));
      ListProtocol.RPutResponse rputResponse2 = FutureUtils.get(
          listService.rput(rputRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, rputResponse2.getStatus());
    }
  }

  @Test
  public void testRemove() {
    try (ProxyOnClient<DistkvListService> listProxy = new ProxyOnClient<>(
        DistkvListService.class, rpcServerPort)) {
      final DistkvListService listService = listProxy.getService();

      // Put.
      ListProtocol.PutRequest.Builder putRequestBuilder
          = ListProtocol.PutRequest.newBuilder();
      List<String> values = dummyListTestData();
      putRequestBuilder.setKey("k1");
      values.forEach(value -> putRequestBuilder.addValues(value));

      ListProtocol.PutResponse putResponse = FutureUtils.get(
          listService.put(putRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      // Test removeOne.
      ListProtocol.RemoveRequest.Builder removeOneRequestBuilder =
          ListProtocol.RemoveRequest.newBuilder();
      removeOneRequestBuilder.setType(ListProtocol.RemoveType.RemoveOne);
      removeOneRequestBuilder.setKey("k1");
      removeOneRequestBuilder.setIndex(1);

      ListProtocol.RemoveResponse removeOneResponse = FutureUtils.get(
          listService.remove(removeOneRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, removeOneResponse.getStatus());

      // Get.
      ListProtocol.GetRequest.Builder getRequestBuilder
          = ListProtocol.GetRequest.newBuilder();
      getRequestBuilder.setKey("k1");
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);

      ListProtocol.GetResponse getResponse = FutureUtils.get(
          listService.get(getRequestBuilder.build()));
      Assert.assertEquals(ImmutableList.of("v0", "v2"), getResponse.getValuesList());

      // RemoveRange.
      ListProtocol.RemoveRequest.Builder removeRangeRequestBuilder =
          ListProtocol.RemoveRequest.newBuilder();
      removeRangeRequestBuilder.setType(ListProtocol.RemoveType.RemoveRange);
      removeRangeRequestBuilder.setKey("k1");
      removeRangeRequestBuilder.setFrom(0);
      removeRangeRequestBuilder.setEnd(1);
      ListProtocol.RemoveResponse removeRangeResponse = FutureUtils.get(
          listService.remove(removeRangeRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, removeRangeResponse.getStatus());

      // Test remove range with a non-exist key.
      removeRangeRequestBuilder.setKey("k2");
      removeRangeRequestBuilder.setIndex(1);
      ListProtocol.RemoveResponse removeResponse2 = FutureUtils.get(
          listService.remove(removeRangeRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, removeResponse2.getStatus());
    }
  }

  @Test
  public void testMRemove() {
    try (ProxyOnClient<DistkvListService> listProxy = new ProxyOnClient<>(
        DistkvListService.class, rpcServerPort)) {
      final DistkvListService listService = listProxy.getService();

      // Put.
      ListProtocol.PutRequest.Builder putRequestBuilder
          = ListProtocol.PutRequest.newBuilder();
      List<String> values = dummyListTestData();
      putRequestBuilder.setKey("k1");
      values.forEach(value -> putRequestBuilder.addValues(value));
      ListProtocol.PutResponse putResponse = FutureUtils.get(
          listService.put(putRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      // Multiple remove.
      ListProtocol.MRemoveRequest.Builder multipleRemoveRequestBuilder =
          ListProtocol.MRemoveRequest.newBuilder();
      multipleRemoveRequestBuilder.setKey("k1");
      multipleRemoveRequestBuilder.addIndexes(1);
      multipleRemoveRequestBuilder.addIndexes(0);
      ListProtocol.MRemoveResponse multipleRemoveResponse = FutureUtils.get(
          listService.mremove(multipleRemoveRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.OK, multipleRemoveResponse.getStatus());

      // Get.
      ListProtocol.GetRequest.Builder getRequestBuilder
          = ListProtocol.GetRequest.newBuilder();
      getRequestBuilder.setKey("k1");
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      ListProtocol.GetResponse getResponse = FutureUtils.get(
          listService.get(getRequestBuilder.build()));
      Assert.assertEquals(ImmutableList.of("v2"), getResponse.getValuesList());

      // Test multi-remove a non-exist key.
      multipleRemoveRequestBuilder.setKey("k2");
      multipleRemoveRequestBuilder.addIndexes(1);
      ListProtocol.MRemoveResponse multipleRemoveResponse2 = FutureUtils.get(
          listService.mremove(multipleRemoveRequestBuilder.build()));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND,
          multipleRemoveResponse2.getStatus());
    }
  }
}
