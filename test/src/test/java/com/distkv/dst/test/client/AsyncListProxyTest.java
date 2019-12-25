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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncListProxyTest extends BaseTestSupplier {
  CommonProtocol.Status status = CommonProtocol.Status.OK;

  @Test
  public void testList() throws ExecutionException, InterruptedException, TimeoutException {
    DstAsyncClient client = newAsyncDstClient();

    // testPut
    CompletableFuture<ListProtocol.PutResponse> putFuture =
            client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    putFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testGetOne
    CompletableFuture<ListProtocol.GetResponse> getOneFuture =
            client.lists().get("k1", 0);
    getOneFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testGetRange
    CompletableFuture<ListProtocol.GetResponse> getRangeFuture =
            client.lists().get("k1", 1, 3);
    getRangeFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testLPut
    CompletableFuture<ListProtocol.LPutResponse> lputFuture =
            client.lists().lput("k1", ImmutableList.of("v0"));
    lputFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testRemoveOne
    CompletableFuture<ListProtocol.RemoveResponse> removeOneFuture =
            client.lists().remove("k1", 3);
    removeOneFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testRPut
    CompletableFuture<ListProtocol.RPutResponse> rputFuture =
            client.lists().rput("k1", ImmutableList.of("v3", "v4"));
    rputFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testRemoveRange
    CompletableFuture<ListProtocol.RemoveResponse> removeRangeFuture =
            client.lists().remove("k1", 1, 2);
    removeRangeFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testMRemove
    CompletableFuture<ListProtocol.MRemoveResponse> mremoveFuture =
            client.lists().mremove("k1", ImmutableList.of(0, 2));
    mremoveFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // testGetAll
    CompletableFuture<ListProtocol.GetResponse> getAllFuture =
            client.lists().get("k1");
    getAllFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    ListProtocol.PutResponse putResponse =
            putFuture.get(1, TimeUnit.SECONDS);
    ListProtocol.GetResponse getOneResponse =
            getOneFuture.get(1, TimeUnit.SECONDS);
    ListProtocol.GetResponse getRangeResponse =
            getRangeFuture.get(1, TimeUnit.SECONDS);
    ListProtocol.LPutResponse lputResponse =
            lputFuture.get(1, TimeUnit.SECONDS);
    ListProtocol.RemoveResponse removeOneResponse =
            removeOneFuture.get(1, TimeUnit.SECONDS);
    ListProtocol.RPutResponse rputResponse =
            rputFuture.get(1, TimeUnit.SECONDS);
    ListProtocol.RemoveResponse removeRangeResponse =
            removeRangeFuture.get(1, TimeUnit.SECONDS);
    ListProtocol.MRemoveResponse mremoveResponse =
            mremoveFuture.get(1, TimeUnit.SECONDS);
    ListProtocol.GetResponse getResponse =
            getAllFuture.get(1, TimeUnit.SECONDS);

    Assert.assertEquals(putResponse.getStatus(), status);
    Assert.assertEquals(rputResponse.getStatus(), status);
    Assert.assertEquals(lputResponse.getStatus(), status);
    Assert.assertEquals(getOneResponse.getStatus(), status);
    Assert.assertEquals(getOneResponse.getValuesList(), ImmutableList.of("v1"));
    Assert.assertEquals(getRangeResponse.getStatus(), status);
    Assert.assertEquals(getRangeResponse.getValuesList(), ImmutableList.of("v2", "v3"));
    Assert.assertEquals(removeOneResponse.getStatus(), status);
    Assert.assertEquals(removeRangeResponse.getStatus(), status);
    Assert.assertEquals(mremoveResponse.getStatus(), status);
    Assert.assertEquals(getResponse.getStatus(), status);
    Assert.assertEquals(getResponse.getValuesList(), ImmutableList.of("v2", "v4"));
    client.disconnect();
  }
}
