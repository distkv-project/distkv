package com.distkv.client;

import com.distkv.asyncclient.DstAsyncClient;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.test.base.BaseTestSupplier;
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
          throws InterruptedException, ExecutionException, TimeoutException {
    DstAsyncClient client = newAsyncDstClient();

    // TestPut
    LinkedList<SortedListEntity> list = new LinkedList<>();
    list.add(new SortedListEntity("xswl", 7));
    list.add(new SortedListEntity("wlll", 8));
    list.add(new SortedListEntity("fw", 9));
    list.add(new SortedListEntity("55", 6));
    CompletableFuture<SortedListProtocol.PutResponse> putFuture =
            client.sortedLists().put("k1", list);
    putFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestIncScore
    CompletableFuture<SortedListProtocol.IncrScoreResponse> incFuture =
            client.sortedLists().incrScore("k1", "fw", 1);
    incFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestPutMember
    CompletableFuture<SortedListProtocol.PutMemberResponse> putMemberFuture =
            client.sortedLists().putMember("k1", new SortedListEntity("aa", 10));
    putMemberFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestRemoveMember
    CompletableFuture<SortedListProtocol.RemoveMemberResponse> removeFuture =
            client.sortedLists().removeMember("k1", "xswl");
    removeFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestTop
    CompletableFuture<SortedListProtocol.TopResponse> topFuture =
            client.sortedLists().top("k1", 3);
    topFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestGetMember
    CompletableFuture<SortedListProtocol.GetMemberResponse> getMemberFuture =
            client.sortedLists().getMember("k1", "55");
    getMemberFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    //TestDrop
    CompletableFuture<CommonProtocol.DropResponse> dropFuture =
            client.sortedLists().drop("k1");
    dropFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    SortedListProtocol.PutResponse putResponse =
            putFuture.get(1, TimeUnit.SECONDS);
    SortedListProtocol.IncrScoreResponse incrScoreResponse =
            incFuture.get(1, TimeUnit.SECONDS);
    SortedListProtocol.PutMemberResponse putMemberResponse =
            putMemberFuture.get(1, TimeUnit.SECONDS);
    SortedListProtocol.RemoveMemberResponse removeMemberResponse =
            removeFuture.get(1, TimeUnit.SECONDS);
    SortedListProtocol.TopResponse topResponse =
            topFuture.get(1, TimeUnit.SECONDS);
    CommonProtocol.DropResponse dropResponse =
            dropFuture.get(1, TimeUnit.SECONDS);

    Assert.assertEquals(putResponse.getStatus(), status);
    Assert.assertEquals(incrScoreResponse.getStatus(), status);
    Assert.assertEquals(putMemberResponse.getStatus(), status);
    Assert.assertEquals(removeMemberResponse.getStatus(), status);
    Assert.assertEquals(topResponse.getList(0).getMember(), "aa");
    Assert.assertEquals(topResponse.getList(1).getMember(), "fw");
    Assert.assertEquals(dropResponse.getStatus(), status);
    client.disconnect();
  }
}
