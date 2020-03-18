package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncClient;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistTopResponse;
import com.distkv.supplier.BaseTestSupplier;

import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncSortedListTest extends BaseTestSupplier {

  CommonProtocol.Status status = CommonProtocol.Status.OK;

  @Test
  public void testAsync()
      throws InterruptedException, ExecutionException, TimeoutException,
      InvalidProtocolBufferException {
    DistkvAsyncClient client = newAsyncDistkvClient();

    // TestPut
    LinkedList<SortedListEntity> list = new LinkedList<>();
    list.add(new SortedListEntity("xswl", 7));
    list.add(new SortedListEntity("wlll", 8));
    list.add(new SortedListEntity("fw", 9));
    list.add(new SortedListEntity("55", 6));
    CompletableFuture<DistkvResponse> putFuture =
        client.sortedLists().put("k1", list);
    putFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestIncScore
    CompletableFuture<DistkvResponse> incFuture =
        client.sortedLists().incrScore("k1", "fw", 1);
    incFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestPutMember
    CompletableFuture<DistkvResponse> putMemberFuture =
        client.sortedLists().putMember("k1", new SortedListEntity("aa", 10));
    putMemberFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestRemoveMember
    CompletableFuture<DistkvResponse> removeFuture =
        client.sortedLists().removeMember("k1", "xswl");
    removeFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestTop
    CompletableFuture<DistkvResponse> topFuture =
        client.sortedLists().top("k1", 3);
    topFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestGetMember
    CompletableFuture<DistkvResponse> getMemberFuture =
        client.sortedLists().getMember("k1", "55");
    getMemberFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestDrop
    CompletableFuture<DistkvResponse> dropFuture =
        client.drop("k1");
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
        client.sortedLists().getMember("k1", "fw");
    DistkvResponse distkvResponse = getMember.get();
    Assert.assertEquals(topResponse.getResponse()
        .unpack(SlistTopResponse.class).getList(0).getMember(), "aa");
    Assert.assertEquals(topResponse.getResponse()
        .unpack(SlistTopResponse.class).getList(1).getMember(), "fw");
    Assert.assertEquals(dropResponse.getStatus(), CommonProtocol.Status.OK);
    client.disconnect();
  }
}
