package com.distkv.dst.test.client;

import com.distkv.dst.asyncclient.DstAsyncClient;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.dst.test.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncSortedListTest extends BaseTestSupplier {

  @Test
  public void testAsyncSortedList() {
    try {
      testAsync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    }
  }

  private void testAsync()
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
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    }).exceptionally(e -> {
      System.out.println("Put Error:" + e.getMessage() + "\n" + e.getLocalizedMessage());
      return SortedListProtocol.PutResponse.newBuilder().build();
    });
    putFuture.get(1, TimeUnit.SECONDS);

    // TestIncScore
    CompletableFuture<SortedListProtocol.IncrScoreResponse> incFuture =
            client.sortedLists().incrScore("k1", "fw", 1);
    incFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    }).exceptionally(e -> {
      System.out.println("Inc Error:" + e.getMessage());
      return SortedListProtocol.IncrScoreResponse.newBuilder().build();
    });
    incFuture.get(1, TimeUnit.SECONDS);

    // TestPutMember
    CompletableFuture<SortedListProtocol.PutMemberResponse> putItemFuture =
            client.sortedLists().putMember("k1", new SortedListEntity("aa", 10));
    putItemFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    }).exceptionally(e -> {
      System.out.println("PutItem Error:" +e.getMessage());
      return SortedListProtocol.PutMemberResponse.newBuilder().build();
    });

    // TestRemoveMember
    CompletableFuture<SortedListProtocol.RemoveMemberResponse> removeFuture =
            client.sortedLists().removeMember("k1", "xswl");
    removeFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    }).exceptionally(e -> {
      System.out.println("RemoveItem Error:" +e.getMessage());
      return SortedListProtocol.RemoveMemberResponse.newBuilder().build();
    });

    // TestTop
    CompletableFuture<SortedListProtocol.TopResponse> topFuture =
            client.sortedLists().top("k1", 3);
    topFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
      List<SortedListProtocol.SortedListEntity> list1 = r.getListList();
      System.out.println(list1);
      Assert.assertEquals(r.getList(0).getMember(), "55");
      Assert.assertEquals(r.getList(1).getMember(), "wlll");
    }).exceptionally(e -> {
      System.out.println("Top Error:" +e.getMessage());
      return SortedListProtocol.TopResponse.newBuilder().build();
    });

    //TestDrop
    CompletableFuture<CommonProtocol.DropResponse> dropFuture =
            client.sortedLists().drop("k1");
    dropFuture.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    }).exceptionally(e -> {
      System.out.println("Drop Error:" +e.getMessage());
      return CommonProtocol.DropResponse.newBuilder().build();
    });




    putItemFuture.get(1, TimeUnit.SECONDS);
    removeFuture.get(1, TimeUnit.SECONDS);
    topFuture.get(1, TimeUnit.SECONDS);
    dropFuture.get(1, TimeUnit.SECONDS);
    client.disconnect();
  }
}
