package com.distkv.dst.test.client;

import com.distkv.dst.asyncclient.DstAsyncClient;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.ListProtocol;
import com.distkv.dst.test.supplier.BaseTestSupplier;
import com.google.common.collect.ImmutableList;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncListProxyTest extends BaseTestSupplier {

  @Test
  public void testAsyncList() throws ExecutionException, InterruptedException {
    DstAsyncClient client = newAsyncDstClient();

    // testPut
    CompletableFuture<ListProtocol.PutResponse> putFuture =
            client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    putFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // testGetOne
    CompletableFuture<ListProtocol.GetResponse> getOneFuture =
            client.lists().get("k1", 0);
    getOneFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
      Assert.assertEquals(r.getValuesList(), ImmutableList.of("v1"));
    });

    // testGetRange
    CompletableFuture<ListProtocol.GetResponse> getRangeFuture =
            client.lists().get("k1", 1, 2);
    getRangeFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
      Assert.assertEquals(r.getValuesList(), ImmutableList.of("v2", "v3"));
    });

    // testLPut
    CompletableFuture<ListProtocol.LPutResponse> lputFuture =
            client.lists().lput("k1", ImmutableList.of("v0"));
    lputFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // testRemoveOne
    CompletableFuture<ListProtocol.RemoveResponse> removeOneFuture =
            client.lists().remove("k1", 3);
    removeOneFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // testRPut
    CompletableFuture<ListProtocol.RPutResponse> rputFuture =
            client.lists().rput("k1", ImmutableList.of("v3", "v4"));
    rputFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // testRemoveRange
    CompletableFuture<ListProtocol.RemoveResponse> removeRangeFuture =
            client.lists().remove("k1", 1, 2);
    removeRangeFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // testMutiRemove
    CompletableFuture<ListProtocol.MRemoveResponse> mutiRemoveFuture =
            client.lists().mutiRemove("k1", ImmutableList.of(0, 2));
    mutiRemoveFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // testGetAll
    CompletableFuture<ListProtocol.GetResponse> getAllFuture =
            client.lists().get("k1");
    getAllFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
      Assert.assertEquals(r.getValuesList(), ImmutableList.of("v1"));
    });

    putFuture.get();
    getOneFuture.get();
    getRangeFuture.get();
    lputFuture.get();
    removeOneFuture.get();
    rputFuture.get();
    removeRangeFuture.get();
    mutiRemoveFuture.get();
    getAllFuture.get();
    client.disconnect();
  }
}
