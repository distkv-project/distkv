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
  public void testAsyncSortedList() {
    DstAsyncClient client = newAsyncDstClient();

    // TestPut
    LinkedList<SortedListEntity> list =
            new LinkedList<>();
    list.add(new SortedListEntity("xswl", 7));
    list.add(new SortedListEntity("wlll", 8));
    list.add(new SortedListEntity("fw", 9));
    list.add(new SortedListEntity("55", 6));
    CompletableFuture<SortedListProtocol.PutResponse> futurePut =
            client.sortedLists().put("k1", list);
    futurePut.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // TestIncItem
    CompletableFuture<SortedListProtocol.IncrScoreResponse> futureInc =
            client.sortedLists().incrItem("k1", "fw", 1);
    futureInc.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // TestPutItem
    CompletableFuture<SortedListProtocol.PutMemberResponse> futurePutItem =
            client.sortedLists().putItem("k1", new SortedListEntity("aa", 10));
    futurePutItem.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // TestDel
    CompletableFuture<SortedListProtocol.RemoveMemberResponse> futuredel =
            client.sortedLists().delItem("k1", "xswl");
    futuredel.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // TestTop
    CompletableFuture<SortedListProtocol.TopResponse> futureTop =
            client.sortedLists().top("k1", 3);
    futureTop.whenComplete((r, t) -> {
      Assert.assertEquals(r.getList(0).getMember(), "55");
      Assert.assertEquals(r.getList(1).getMember(), "wlll");
    });

    //TestDrop
    CompletableFuture<CommonProtocol.DropResponse> futureDrop =
            client.sortedLists().drop("k1");
    futureDrop.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    try {
      futurePut.get();
      futureInc.get();
      futurePutItem.get();
      futuredel.get();
      futureTop.get();
      futureDrop.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    client.disconnect();
  }
}
