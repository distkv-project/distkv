package com.distkv.server.service;

import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListGetResponse;
import com.distkv.rpc.service.DistkvService;
import com.distkv.supplier.BaseTestSupplier;
import com.distkv.supplier.ProxyOnClient;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ListRpcTest extends BaseTestSupplier {

  private static List<String> dummyListTestData() {
    return ImmutableList.of("v0", "v1", "v2");
  }

  @Test
  public void testPutAndGet() throws InvalidProtocolBufferException {
    try (ProxyOnClient<DistkvService> listProxy = new ProxyOnClient<>(
        DistkvService.class, KVSTORE_PORT)) {
      final DistkvService listService = listProxy.getService();

      // Put.
      ListProtocol.ListPutRequest.Builder putRequestBuilder = ListProtocol.ListPutRequest
          .newBuilder();
      List<String> values = dummyListTestData();
      values.forEach(putRequestBuilder::addValues);
      DistkvRequest putRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_PUT)
          .setRequest(Any.pack(putRequestBuilder.build()))
          .build();
      DistkvResponse response = FutureUtils.get(
          listService.call(putRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, response.getStatus());

      // Get.
      ListProtocol.ListGetRequest.Builder getRequestBuilder = ListProtocol.ListGetRequest
          .newBuilder();
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      DistkvRequest getRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_GET)
          .setRequest(Any.pack(getRequestBuilder.build()))
          .build();
      DistkvResponse getResponse = FutureUtils.get(
          listService.call(getRequest));
      Assert.assertEquals(dummyListTestData(), getResponse.getResponse()
          .unpack(ListGetResponse.class).getValuesList());

      // Drop.
      DistkvRequest dropRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.DROP)
          .build();
      DistkvResponse dropResponse = FutureUtils.get(
          listService.call(dropRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, dropResponse.getStatus());

      // Get a non-exist key.
      ListProtocol.ListGetRequest.Builder getRequest2Builder = ListProtocol.ListGetRequest
          .newBuilder();
      getRequest2Builder.setType(ListProtocol.GetType.GET_ALL);
      DistkvRequest getRequest2 = DistkvRequest.newBuilder()
          .setKey("k2")
          .setRequestType(RequestType.LIST_GET)
          .setRequest(Any.pack(getRequest2Builder.build()))
          .build();
      DistkvResponse getResponse2 = FutureUtils.get(
          listService.call(getRequest2));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, getResponse2.getStatus());
    }
  }

  @Test
  public void testDrop() {
    try (ProxyOnClient<DistkvService> listProxy = new ProxyOnClient<>(
        DistkvService.class, KVSTORE_PORT)) {
      final DistkvService listService = listProxy.getService();

      // Put.
      ListProtocol.ListPutRequest.Builder putRequestBuilder = ListProtocol.ListPutRequest
          .newBuilder();
      List<String> values = dummyListTestData();
      values.forEach(putRequestBuilder::addValues);
      DistkvRequest putRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_PUT)
          .setRequest(Any.pack(putRequestBuilder.build()))
          .build();
      DistkvResponse putResponse = FutureUtils.get(
          listService.call(putRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      // Drop.
      DistkvRequest dropRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.DROP)
          .build();
      DistkvResponse dropResponse = FutureUtils.get(
          listService.call(dropRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, dropResponse.getStatus());

      // Test drop a non-exist key.
      DistkvRequest dropRequest2 = DistkvRequest.newBuilder()
          .setKey("k2")
          .setRequestType(RequestType.DROP)
          .build();
      DistkvResponse dropResponse2 = FutureUtils.get(
          listService.call(dropRequest2));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, dropResponse2.getStatus());
    }
  }

  @Test
  public void testLPut() throws InvalidProtocolBufferException {
    try (ProxyOnClient<DistkvService> listProxy = new ProxyOnClient<>(
        DistkvService.class, KVSTORE_PORT)) {
      final DistkvService listService = listProxy.getService();

      // Put.
      ListProtocol.ListPutRequest.Builder putRequestBuilder
          = ListProtocol.ListPutRequest.newBuilder();
      List<String> values = dummyListTestData();
      values.forEach(putRequestBuilder::addValues);
      DistkvRequest putRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_PUT)
          .setRequest(Any.pack(putRequestBuilder.build()))
          .build();
      DistkvResponse putResponse = FutureUtils.get(
          listService.call(putRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      // LPut.
      ListProtocol.ListLPutRequest.Builder lputRequestBuilder
          = ListProtocol.ListLPutRequest.newBuilder();
      List<String> valuesLput = new ArrayList<>();
      valuesLput.add("v3");
      valuesLput.add("v4");
      lputRequestBuilder.addAllValues(valuesLput);
      DistkvRequest lputRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_LPUT)
          .setRequest(Any.pack(lputRequestBuilder.build()))
          .build();
      DistkvResponse lputResponse = FutureUtils.get(
          listService.call(lputRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, lputResponse.getStatus());

      // Get.
      ListProtocol.ListGetRequest.Builder getRequestBuilder
          = ListProtocol.ListGetRequest.newBuilder();
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      DistkvRequest getRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_GET)
          .setRequest(Any.pack(getRequestBuilder.build()))
          .build();
      DistkvResponse getResponse = FutureUtils.get(
          listService.call(getRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, getResponse.getStatus());
      Assert.assertEquals(ImmutableList.of("v3", "v4", "v0", "v1", "v2"),
          getResponse.getResponse().unpack(ListGetResponse.class).getValuesList());

      // Drop.
      DistkvRequest dropRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.DROP)
          .build();
      DistkvResponse dropResponse = FutureUtils.get(
          listService.call(dropRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, dropResponse.getStatus());

      // Test lput a non-exist key.
      List<String> valuesLput2 = new ArrayList<>();
      valuesLput2.add("v3");
      valuesLput2.add("v4");
      valuesLput2.forEach(lputRequestBuilder::addValues);
      DistkvRequest getRequest2 = DistkvRequest.newBuilder()
          .setKey("k2")
          .setRequestType(RequestType.LIST_LPUT)
          .setRequest(Any.pack(lputRequestBuilder.build()))
          .build();
      DistkvResponse lputResponse2 = FutureUtils.get(
          listService.call(getRequest2));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, lputResponse2.getStatus());
    }
  }

  @Test
  public void testRPut() throws InvalidProtocolBufferException {
    try (ProxyOnClient<DistkvService> listProxy = new ProxyOnClient<>(
        DistkvService.class, KVSTORE_PORT)) {
      final DistkvService listService = listProxy.getService();

      // Put.
      ListProtocol.ListPutRequest.Builder putRequestBuilder
          = ListProtocol.ListPutRequest.newBuilder();
      List<String> values = dummyListTestData();
      values.forEach(putRequestBuilder::addValues);
      DistkvRequest putRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_PUT)
          .setRequest(Any.pack(putRequestBuilder.build()))
          .build();
      DistkvResponse putResponse = FutureUtils.get(
          listService.call(putRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      // RPut.
      ListProtocol.ListRPutRequest.Builder rputRequestBuilder
          = ListProtocol.ListRPutRequest.newBuilder();
      List<String> valuesRput = new ArrayList<>();
      valuesRput.add("v3");
      valuesRput.add("v4");
      valuesRput.forEach(rputRequestBuilder::addValues);
      DistkvRequest rputRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_RPUT)
          .setRequest(Any.pack(rputRequestBuilder.build()))
          .build();
      DistkvResponse rputResponse = FutureUtils.get(
          listService.call(rputRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, rputResponse.getStatus());

      // Get.
      ListProtocol.ListGetRequest.Builder getRequestBuilder
          = ListProtocol.ListGetRequest.newBuilder();
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      DistkvRequest getRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_GET)
          .setRequest(Any.pack(getRequestBuilder.build()))
          .build();
      DistkvResponse getResponse = FutureUtils.get(
          listService.call(getRequest));
      Assert.assertEquals(ImmutableList.of("v0", "v1", "v2", "v3", "v4"),
          getResponse.getResponse().unpack(ListGetResponse.class).getValuesList());

      // Drop.
      DistkvRequest dropRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.DROP)
          .build();
      DistkvResponse dropResponse = FutureUtils.get(
          listService.call(dropRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, dropResponse.getStatus());

      // Test rput a non-exist key.
      List<String> valuesRput2 = new ArrayList<>();
      valuesRput2.add("v3");
      valuesRput2.add("v4");
      valuesRput2.forEach(rputRequestBuilder::addValues);
      DistkvRequest getRequest2 = DistkvRequest.newBuilder()
          .setKey("k2")
          .setRequestType(RequestType.LIST_RPUT)
          .setRequest(Any.pack(rputRequestBuilder.build()))
          .build();
      DistkvResponse rputResponse2 = FutureUtils.get(
          listService.call(getRequest2));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, rputResponse2.getStatus());
    }
  }

  @Test
  public void testRemove() throws InvalidProtocolBufferException {
    try (ProxyOnClient<DistkvService> listProxy = new ProxyOnClient<>(
        DistkvService.class, KVSTORE_PORT)) {
      final DistkvService listService = listProxy.getService();

      // Put.
      ListProtocol.ListPutRequest.Builder putRequestBuilder
          = ListProtocol.ListPutRequest.newBuilder();
      List<String> values = dummyListTestData();
      values.forEach(putRequestBuilder::addValues);
      DistkvRequest putRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_PUT)
          .setRequest(Any.pack(putRequestBuilder.build()))
          .build();
      DistkvResponse putResponse = FutureUtils.get(
          listService.call(putRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      // Test removeOne.
      ListProtocol.ListRemoveRequest.Builder removeOneRequestBuilder =
          ListProtocol.ListRemoveRequest.newBuilder();
      removeOneRequestBuilder.setType(ListProtocol.RemoveType.RemoveOne);
      removeOneRequestBuilder.setIndex(1);
      DistkvRequest removeRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_REMOVE)
          .setRequest(Any.pack(removeOneRequestBuilder.build()))
          .build();
      DistkvResponse removeOneResponse = FutureUtils.get(
          listService.call(removeRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, removeOneResponse.getStatus());

      // Get.
      ListProtocol.ListGetRequest.Builder getRequestBuilder
          = ListProtocol.ListGetRequest.newBuilder();
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      DistkvRequest getRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_GET)
          .setRequest(Any.pack(getRequestBuilder.build()))
          .build();
      DistkvResponse getResponse = FutureUtils.get(
          listService.call(getRequest));
      Assert.assertEquals(ImmutableList.of("v0", "v2"), getResponse.getResponse()
          .unpack(ListGetResponse.class).getValuesList());

      // RemoveRange.
      ListProtocol.ListRemoveRequest.Builder removeRangeRequestBuilder =
          ListProtocol.ListRemoveRequest.newBuilder();
      removeRangeRequestBuilder.setType(ListProtocol.RemoveType.RemoveRange);
      removeRangeRequestBuilder.setFrom(0);
      removeRangeRequestBuilder.setEnd(1);
      DistkvRequest removeRangeRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_REMOVE)
          .setRequest(Any.pack(removeRangeRequestBuilder.build()))
          .build();
      DistkvResponse removeRangeResponse = FutureUtils.get(
          listService.call(removeRangeRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, removeRangeResponse.getStatus());

      // Drop.
      DistkvRequest dropRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.DROP)
          .build();
      DistkvResponse dropResponse = FutureUtils.get(
          listService.call(dropRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, dropResponse.getStatus());

      // Test remove range with a non-exist key.
      removeRangeRequestBuilder.setIndex(1);
      DistkvRequest removeRangeRequest2 = DistkvRequest.newBuilder()
          .setKey("k2")
          .setRequestType(RequestType.LIST_REMOVE)
          .setRequest(Any.pack(removeRangeRequestBuilder.build()))
          .build();
      DistkvResponse removeResponse2 = FutureUtils.get(
          listService.call(removeRangeRequest2));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, removeResponse2.getStatus());
    }
  }

  @Test
  public void testMRemove() throws InvalidProtocolBufferException {
    try (ProxyOnClient<DistkvService> listProxy = new ProxyOnClient<>(
        DistkvService.class, KVSTORE_PORT)) {
      final DistkvService listService = listProxy.getService();

      // Put.
      ListProtocol.ListPutRequest.Builder putRequestBuilder
          = ListProtocol.ListPutRequest.newBuilder();
      List<String> values = dummyListTestData();
      values.forEach(putRequestBuilder::addValues);
      DistkvRequest putRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_PUT)
          .setRequest(Any.pack(putRequestBuilder.build()))
          .build();
      DistkvResponse putResponse = FutureUtils.get(
          listService.call(putRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      // Multiple remove.
      ListProtocol.ListMRemoveRequest.Builder multipleRemoveRequestBuilder =
          ListProtocol.ListMRemoveRequest.newBuilder();
      multipleRemoveRequestBuilder.addIndexes(1);
      multipleRemoveRequestBuilder.addIndexes(0);
      DistkvRequest mremoveRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_MREMOVE)
          .setRequest(Any.pack(multipleRemoveRequestBuilder.build()))
          .build();
      DistkvResponse multipleRemoveResponse = FutureUtils.get(
          listService.call(mremoveRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, multipleRemoveResponse.getStatus());

      // Get.
      ListProtocol.ListGetRequest.Builder getRequestBuilder
          = ListProtocol.ListGetRequest.newBuilder();
      getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);
      DistkvRequest getRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.LIST_GET)
          .setRequest(Any.pack(getRequestBuilder.build()))
          .build();
      DistkvResponse getResponse = FutureUtils.get(
          listService.call(getRequest));
      Assert.assertEquals(ImmutableList.of("v2"), getResponse.getResponse()
          .unpack(ListGetResponse.class).getValuesList());

      // Drop.
      DistkvRequest dropRequest = DistkvRequest.newBuilder()
          .setKey("list_r_k1")
          .setRequestType(RequestType.DROP)
          .build();
      DistkvResponse dropResponse = FutureUtils.get(
          listService.call(dropRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, dropResponse.getStatus());

      // Test multi-remove a non-exist key.
      multipleRemoveRequestBuilder.addIndexes(1);
      DistkvRequest mremoveRequest2 = DistkvRequest.newBuilder()
          .setKey("k2")
          .setRequestType(RequestType.LIST_MREMOVE)
          .setRequest(Any.pack(multipleRemoveRequestBuilder.build()))
          .build();
      DistkvResponse multipleRemoveResponse2 = FutureUtils.get(
          listService.call(mremoveRequest2));
      Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND,
          multipleRemoveResponse2.getStatus());
    }
  }
}
