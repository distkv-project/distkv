package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncClient;
import com.distkv.common.entity.sortedList.SlistEntity;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.SlistProtocol.SlistTopResponse;
import com.distkv.supplier.BaseTestSupplier;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncSlistTest extends BaseTestSupplier {

  CommonProtocol.Status status = CommonProtocol.Status.OK;

  @Test
  public void testAsync()
      throws InterruptedException, ExecutionException, TimeoutException,
      InvalidProtocolBufferException {
    DistkvAsyncClient client = newAsyncDistkvClient();

    // TestPut
    LinkedList<SlistEntity> list = new LinkedList<>();
    list.add(new SlistEntity("xswl", 7));
    list.add(new SlistEntity("wlll", 8));
    list.add(new SlistEntity("fw", 9));
    list.add(new SlistEntity("55", 6));
    CompletableFuture<DistkvResponse> putFuture =
        client.slists().put("async_slist_k1", list);
    putFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestIncScore
    CompletableFuture<DistkvResponse> incFuture =
        client.slists().incrScore("async_slist_k1", "fw", 1);
    incFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestPutMember
    CompletableFuture<DistkvResponse> putMemberFuture =
        client.slists().putMember("async_slist_k1", new SlistEntity("aa", 10));
    putMemberFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestRemoveMember
    CompletableFuture<DistkvResponse> removeFuture =
        client.slists().removeMember("async_slist_k1", "xswl");
    removeFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestTop
    CompletableFuture<DistkvResponse> topFuture =
        client.slists().top("async_slist_k1", 3);
    topFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestGetMember
    CompletableFuture<DistkvResponse> getMemberFuture =
        client.slists().getMember("async_slist_k1", "55");
    getMemberFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    //TestDrop
    CompletableFuture<DistkvResponse> dropFuture =
        client.drop("async_slist_k1");
    dropFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    DistkvResponse putResponse =
        putFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse incrScoreResponse =
        incFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse putMemberResponse =
        putMemberFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse removeMemberResponse =
        removeFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse topResponse =
        topFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse dropResponse =
        dropFuture.get(1, TimeUnit.SECONDS);

    Assert.assertEquals(putResponse.getStatus(), status);
    Assert.assertEquals(incrScoreResponse.getStatus(), status);
    Assert.assertEquals(putMemberResponse.getStatus(), status);
    Assert.assertEquals(removeMemberResponse.getStatus(), status);
    CompletableFuture<DistkvResponse> getMember =
        client.slists().getMember("async_slist_k1", "fw");
    DistkvResponse distkvResponse = getMember.get();
    Assert.assertEquals(topResponse.getResponse()
        .unpack(SlistTopResponse.class).getList(0).getMember(), "aa");
    Assert.assertEquals(topResponse.getResponse()
        .unpack(SlistTopResponse.class).getList(1).getMember(), "fw");
    Assert.assertEquals(dropResponse.getStatus(), status);
    client.disconnect();
  }
}
