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
  public void testAsyncList() {
    DstAsyncClient client = newAsyncDstClient();

    // testPut
    CompletableFuture<ListProtocol.PutResponse> futurePut =
            client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    futurePut.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // testGetOne
    CompletableFuture<ListProtocol.GetResponse> futureGetOne =
            client.lists().get("k1", 0);
    futureGetOne.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
      Assert.assertEquals(r.getValuesList(), ImmutableList.of("v1"));
    });

    // testGetRange
    CompletableFuture<ListProtocol.GetResponse> futureGetRange =
            client.lists().get("k1", 1, 2);
    futureGetRange.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
      Assert.assertEquals(r.getValuesList(), ImmutableList.of("v2", "v3"));
    });

    // testLPut
    CompletableFuture<ListProtocol.LPutResponse> futureLPut =
            client.lists().lput("k1", ImmutableList.of("v0"));
    futureLPut.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // testRemoveOne
    CompletableFuture<ListProtocol.RemoveResponse> futureOne =
            client.lists().remove("k1", 3);
    futureOne.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // testRPut
    CompletableFuture<ListProtocol.RPutResponse> futureRPut =
            client.lists().rput("k1", ImmutableList.of("v3", "v4"));
    futureRPut.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // testRemoveRange
    CompletableFuture<ListProtocol.RemoveResponse> futureRange =
            client.lists().remove("k1", 1, 2);
    futureRange.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // testMutiRemove
    CompletableFuture<ListProtocol.MRemoveResponse> futureMRemove =
            client.lists().mutiRemove("k1", ImmutableList.of(0, 2));
    futureMRemove.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // testGetAll
    CompletableFuture<ListProtocol.GetResponse> futureGetAll =
            client.lists().get("k1");
    futureGetAll.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
      Assert.assertEquals(r.getValuesList(), ImmutableList.of("v1"));
    });

    try {
      futurePut.get();
      futureGetOne.get();
      futureGetRange.get();
      futureLPut.get();
      futureOne.get();
      futureRPut.get();
      futureRange.get();
      futureMRemove.get();
      futureGetAll.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    client.disconnect();
  }
}
