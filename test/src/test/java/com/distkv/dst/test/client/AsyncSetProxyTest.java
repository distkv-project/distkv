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

public class AsyncSetProxyTest extends BaseTestSupplier {

  @Test
  public void testAsyncSet() {
    DstAsyncClient client = newAsyncDstClient();

    // TestPut
    CompletableFuture<SetProtocol.PutResponse> futurePut =
            client.sets().put("k1", ImmutableSet.of("v1", "v2", "v3"));
    futurePut.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    //TestPutItem
    CompletableFuture<SetProtocol.PutItemResponse> futurePutItem =
            client.sets().putItem("k1", "v4");
    futurePutItem.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    //TestExists
    CompletableFuture<SetProtocol.ExistsResponse> futureExists =
            client.sets().exists("k1", "v4");
    futureExists.whenComplete((r, t) -> {
      Assert.assertTrue(r.getResult());
    });

    //TestRemoveItem
    CompletableFuture<SetProtocol.RemoveItemResponse> futureRemove =
            client.sets().removeItem("k1", "v1");
    futureRemove.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    //TestGet
    CompletableFuture<SetProtocol.GetResponse> futureGet =
            client.sets().get("k1");
    futureGet.whenComplete((r, t) -> {
      Assert.assertEquals(r.getValuesList(), ImmutableList.of("v2", "v3", "v4"));
    });

    //TestDrop
    CompletableFuture<CommonProtocol.DropResponse> futureDrop =
            client.sets().drop("k1");
    futureDrop.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    client.disConnect();
  }
}
