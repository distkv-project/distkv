package com.distkv.dst.test.client;

import com.distkv.dst.asyncclient.DstAsyncClient;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;
import com.distkv.dst.test.supplier.BaseTestSupplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncSetProxyTest extends BaseTestSupplier {
  CommonProtocol.Status status = CommonProtocol.Status.OK;

  @Test
  public void testAsyncSet() throws ExecutionException, InterruptedException, TimeoutException {
    DstAsyncClient client = newAsyncDstClient();

    // TestPut
    CompletableFuture<SetProtocol.PutResponse> putFuture =
            client.sets().put("k1", ImmutableSet.of("v1", "v2", "v3"));
    putFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    //TestPutItem
    CompletableFuture<SetProtocol.PutItemResponse> putItemFuture =
            client.sets().putItem("k1", "v4");
    putItemFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    //TestExists
    CompletableFuture<SetProtocol.ExistsResponse> existsFuture =
            client.sets().exists("k1", "v4");
    existsFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    //TestRemoveItem
    CompletableFuture<SetProtocol.RemoveItemResponse> removeFuture =
            client.sets().removeItem("k1", "v1");
    removeFuture.whenComplete((r, t) -> {
      if (t != null) {
        System.out.println(t);
        throw new IllegalStateException(t);
      }
    });

    //TestGet
    CompletableFuture<SetProtocol.GetResponse> getFuture =
            client.sets().get("k1");
    getFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    //TestDrop
    CompletableFuture<CommonProtocol.DropResponse> dropFuture =
            client.sets().drop("k1");
    dropFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    SetProtocol.PutResponse putResponse =
            putFuture.get(1, TimeUnit.SECONDS);
    SetProtocol.PutItemResponse putItemResponse =
            putItemFuture.get(1, TimeUnit.SECONDS);
    SetProtocol.ExistsResponse existsResponse =
            existsFuture.get(1, TimeUnit.SECONDS);
    SetProtocol.RemoveItemResponse removeItemResponse =
            removeFuture.get(1, TimeUnit.SECONDS);
    SetProtocol.GetResponse getResponse =
            getFuture.get(1, TimeUnit.SECONDS);
    CommonProtocol.DropResponse dropResponse =
            dropFuture.get(1, TimeUnit.SECONDS);

    Assert.assertEquals(putResponse.getStatus(), status);
    Assert.assertEquals(putItemResponse.getStatus(), status);
    Assert.assertTrue(existsResponse.getResult());
    Assert.assertEquals(removeItemResponse.getStatus(), status);
    Assert.assertEquals(getResponse.getValuesList(), ImmutableList.of("v2", "v3", "v4"));
    Assert.assertEquals(dropResponse.getStatus(), CommonProtocol.Status.OK);
    client.disconnect();
  }
}
