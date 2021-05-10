package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncClient;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListGetResponse;
import com.distkv.supplier.BaseTestSupplier;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Test(singleThreaded = true)
public class AsyncListProxyTest extends BaseTestSupplier {

  CommonProtocol.Status status = CommonProtocol.Status.OK;

  @Test
  public void testList()
      throws ExecutionException, InterruptedException, TimeoutException,
      InvalidProtocolBufferException {
    DistkvAsyncClient client = newAsyncDistkvClient();

    // testPut
    CompletableFuture<DistkvResponse> putFuture =
        client.lists().put("async_list_p_k1", ImmutableList.of("v1", "v2", "v3"));
    putFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testGetOne
    CompletableFuture<DistkvResponse> getOneFuture =
        client.lists().get("async_list_p_k1", 0);
    getOneFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testGetRange
    CompletableFuture<DistkvResponse> getRangeFuture =
        client.lists().get("async_list_p_k1", 1, 3);
    getRangeFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testLPut
    CompletableFuture<DistkvResponse> lputFuture =
        client.lists().lput("async_list_p_k1", ImmutableList.of("v0"));
    lputFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testRemoveOne
    CompletableFuture<DistkvResponse> removeOneFuture =
        client.lists().remove("async_list_p_k1", 3);
    removeOneFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testRPut
    CompletableFuture<DistkvResponse> rputFuture =
        client.lists().rput("async_list_p_k1", ImmutableList.of("v3", "v4"));
    rputFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testRemoveRange
    CompletableFuture<DistkvResponse> removeRangeFuture =
        client.lists().remove("async_list_p_k1", 1, 2);
    removeRangeFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testMRemove
    CompletableFuture<DistkvResponse> mremoveFuture =
        client.lists().mremove("async_list_p_k1", ImmutableList.of(0, 2));
    mremoveFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testGetAll
    CompletableFuture<DistkvResponse> getAllFuture =
        client.lists().get("async_list_p_k1");
    getAllFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    CompletableFuture<DistkvResponse> dropFuture =
        client.drop("async_list_p_k1");
    dropFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    DistkvResponse putResponse = putFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse getOneResponse = getOneFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse getRangeResponse = getRangeFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse lputResponse = lputFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse removeOneResponse = removeOneFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse rputResponse = rputFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse removeRangeResponse = removeRangeFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse mremoveResponse = mremoveFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse getResponse = getAllFuture.get(1, TimeUnit.SECONDS);
    dropFuture.get();

    Assert.assertEquals(putResponse.getStatus(), status);
    Assert.assertEquals(rputResponse.getStatus(), status);
    Assert.assertEquals(lputResponse.getStatus(), status);
    Assert.assertEquals(getOneResponse.getStatus(), status);
    Assert.assertEquals(getRangeResponse.getStatus(), status);
    Assert.assertEquals(removeOneResponse.getStatus(), status);
    Assert.assertEquals(removeRangeResponse.getStatus(), status);
    Assert.assertEquals(mremoveResponse.getStatus(), status);
    Assert.assertEquals(getResponse.getStatus(), status);
    Assert.assertEquals(getRangeResponse.getResponse()
        .unpack(ListGetResponse.class).getValuesList(), ImmutableList.of("v2", "v3"));
    Assert.assertEquals(getOneResponse.getResponse()
        .unpack(ListGetResponse.class).getValuesList(), ImmutableList.of("v1"));
    Assert.assertEquals(getResponse.getResponse()
        .unpack(ListGetResponse.class).getValuesList(), ImmutableList.of("v2", "v4"));
    client.disconnect();
  }
}
