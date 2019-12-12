package com.distkv.dst.test.client;

import com.distkv.dst.asyncclient.DstAsyncClient;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.dst.test.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncSortedListTest extends BaseTestSupplier {

  @Test
  public void testAsyncSortedList() throws ExecutionException, InterruptedException {
    DstAsyncClient client = newAsyncDstClient();

    // TestPut
    LinkedList<SortedListEntity> list =
            new LinkedList<>();
    list.add(new SortedListEntity("xswl", 7));
    list.add(new SortedListEntity("wlll", 8));
    list.add(new SortedListEntity("fw", 9));
    list.add(new SortedListEntity("55", 6));
    CompletableFuture<SortedListProtocol.PutResponse> putFuture =
            client.sortedLists().put("k1", list);
    putFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // TestIncItem
    CompletableFuture<SortedListProtocol.IncrScoreResponse> incFuture =
            client.sortedLists().incrItem("k1", "fw", 1);
    incFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // TestPutItem
    CompletableFuture<SortedListProtocol.PutMemberResponse> putItemFuture =
            client.sortedLists().putItem("k1", new SortedListEntity("aa", 10));
    putItemFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // TestDel
    CompletableFuture<SortedListProtocol.RemoveMemberResponse> removeFuture =
            client.sortedLists().removeItem("k1", "xswl");
    removeFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // TestTop
    CompletableFuture<SortedListProtocol.TopResponse> topFuture =
            client.sortedLists().top("k1", 3);
    topFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getList(0).getMember(), "55");
      Assert.assertEquals(r.getList(1).getMember(), "wlll");
    });

    //TestDrop
    CompletableFuture<CommonProtocol.DropResponse> dropFuture =
            client.sortedLists().drop("k1");
    dropFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    putFuture.get();
    incFuture.get();
    putItemFuture.get();
    removeFuture.get();
    topFuture.get();
    dropFuture.get();
    client.disconnect();
  }
}
