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

public class AsyncSetProxyTest extends BaseTestSupplier {

  @Test
  public void testAsyncSet() throws ExecutionException, InterruptedException {
    DstAsyncClient client = newAsyncDstClient();

    // TestPut
    CompletableFuture<SetProtocol.PutResponse> putFuture =
            client.sets().put("k1", ImmutableSet.of("v1", "v2", "v3"));
    putFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    //TestPutItem
    CompletableFuture<SetProtocol.PutItemResponse> putItemFuture =
            client.sets().putItem("k1", "v4");
    putItemFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    //TestExists
    CompletableFuture<SetProtocol.ExistsResponse> existsFuture =
            client.sets().exists("k1", "v4");
    existsFuture.whenComplete((r, t) -> {
      Assert.assertTrue(r.getResult());
    });

    //TestRemoveItem
    CompletableFuture<SetProtocol.RemoveItemResponse> removeFuture =
            client.sets().removeItem("k1", "v1");
    removeFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    //TestGet
    CompletableFuture<SetProtocol.GetResponse> getFuture =
            client.sets().get("k1");
    getFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getValuesList(), ImmutableList.of("v2", "v3", "v4"));
    });

    //TestDrop
    CompletableFuture<CommonProtocol.DropResponse> dropFuture =
            client.sets().drop("k1");
    dropFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    putFuture.get();
    putItemFuture.get();
    existsFuture.get();
    removeFuture.get();
    getFuture.get();
    dropFuture.get();
    client.disconnect();
  }
}
