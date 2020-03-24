package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncClient;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetExistsResponse;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetGetResponse;
import com.distkv.supplier.BaseTestSupplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncSetProxyTest extends BaseTestSupplier {
  CommonProtocol.Status status = CommonProtocol.Status.OK;

  @Test
  public void testAsyncSet()
      throws ExecutionException, InterruptedException, TimeoutException,
      InvalidProtocolBufferException {
    DistkvAsyncClient client = newAsyncDistkvClient();

    // TestPut
    CompletableFuture<DistkvResponse> putFuture =
            client.sets().put("k1", ImmutableSet.of("v1", "v2", "v3"));
    putFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    //TestPutItem
    CompletableFuture<DistkvResponse> putItemFuture =
            client.sets().putItem("k1", "v4");
    putItemFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    //TestExists
    CompletableFuture<DistkvResponse> existsFuture =
            client.sets().exists("k1", "v4");
    existsFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    //TestRemoveItem
    CompletableFuture<DistkvResponse> removeFuture =
            client.sets().removeItem("k1", "v1");
    removeFuture.whenComplete((r, t) -> {
      if (t != null) {
        System.out.println(t);
        throw new IllegalStateException(t);
      }
    });

    //TestGet
    CompletableFuture<DistkvResponse> getFuture =
            client.sets().get("k1");
    getFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    //TestDrop
    CompletableFuture<DistkvResponse> dropFuture =
            client.drop("k1");
    dropFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    DistkvResponse putResponse = putFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse putItemResponse = putItemFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse existsResponse = existsFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse removeItemResponse = removeFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse getResponse = getFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse dropResponse = dropFuture.get(1, TimeUnit.SECONDS);

    Assert.assertEquals(putResponse.getStatus(), status);
    Assert.assertEquals(putItemResponse.getStatus(), status);
    Assert.assertEquals(removeItemResponse.getStatus(), status);
    Assert.assertEquals(dropResponse.getStatus(), CommonProtocol.Status.OK);
    Assert.assertTrue(existsResponse.getResponse()
        .unpack(SetExistsResponse.class).getResult());
    Assert.assertEquals(getResponse.getResponse()
        .unpack(SetGetResponse.class).getValuesList(), ImmutableList.of("v2", "v3", "v4"));
    client.disconnect();
  }
}
