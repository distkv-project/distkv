package com.distkv.asyncclient.example;

import com.distkv.asyncclient.DefaultAsyncClient;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DstAsyncUsageExample {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    DefaultAsyncClient dstClient = new DefaultAsyncClient("list://127.0.0.1:8082");
    if (dstClient.isConnected()) {
      CompletableFuture<StringProtocol.PutResponse> strPutFuture =
              dstClient.strs().put("k1","asd");
      CompletableFuture<ListProtocol.PutResponse> listPutFuture =
              dstClient.lists().put("k1", Arrays.asList("v1","v2","v3"));
      CompletableFuture<SetProtocol.PutResponse> setPutFuture =
              dstClient.sets().put("k1", new HashSet<>(Arrays.asList("v1", "v2","v3")));
      Map<String, String> map = new HashMap<>();
      map.put("k1", "v1");
      map.put("k2", "v2");
      map.put("k3", "v3");
      CompletableFuture<DictProtocol.PutResponse> dictPutFuture =
              dstClient.dicts().put("dict", map);
      LinkedList<SortedListEntity> slist = new LinkedList<>();
      slist.add(new SortedListEntity("a", 10));
      slist.add(new SortedListEntity("b", 8));
      slist.add(new SortedListEntity("c", 6));
      slist.add(new SortedListEntity("d", 4));

      strPutFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("String put completed.");
        }
      });
      listPutFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("List put completed.");
        }
      });
      setPutFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("Set put completed.");
        }
      });
      dictPutFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("Dict put completed.");
        }
      });

      strPutFuture.get();
      listPutFuture.get();
      setPutFuture.get();
      dictPutFuture.get();

      CompletableFuture<StringProtocol.GetResponse> strGetFuture =
              dstClient.strs().get("k1");
      CompletableFuture<ListProtocol.GetResponse> listGetFuture =
              dstClient.lists().get("k1");
      CompletableFuture<SetProtocol.GetResponse> setGetFuture =
              dstClient.sets().get("k1");
      CompletableFuture<DictProtocol.GetResponse> dictGetFuture =
              dstClient.dicts().get("dict");

      strGetFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("String get completed.");
        }
      });
      listGetFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("List get completed.");
        }
      });
      setGetFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("Set get completed.");
        }
      });
      dictGetFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("Dict get completed.");
        }
      });

      StringProtocol.GetResponse strGet = strGetFuture.get();
      ListProtocol.GetResponse listGet = listGetFuture.get();
      SetProtocol.GetResponse setGet = setGetFuture.get();
      DictProtocol.GetResponse dictGet = dictGetFuture.get();

      System.out.println("The result of dstClient.strs().get(\"k1\") is: "
            + strGet.getValue());
      System.out.println("The result of dstClient.lists().get(\"k1\") is: "
            + listGet.getValuesList());
      System.out.println("The result of dstClient.sets().get(\"k1\") is: "
            + setGet.getValuesList());
      System.out.println("The result of dstClient.dicts().get(\"dict1\") is: "
            + "\n" + dictGet.getDict());

      // SortedList example.
      CompletableFuture<SortedListProtocol.PutResponse> slistPutFuture =
            dstClient.sortedLists().put("k1", slist);
      CompletableFuture<SortedListProtocol.PutMemberResponse> slistPutMFuture =
            dstClient.sortedLists().putMember("k1", new SortedListEntity("s",100));

      slistPutFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("SortedList put completed.");
        }
      });

      // Make sure the "Put" request is done first.
      slistPutFuture.get();

      slistPutMFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("SortedList putMember completed.");
        }
      });
      slistPutMFuture.get();

      CompletableFuture<SortedListProtocol.TopResponse> slistTopFuture =
            dstClient.sortedLists().top("k1", 3);

      slistTopFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("SortedList top completed.");
        }
      });

      SortedListProtocol.TopResponse slistTop = slistTopFuture.get();
      System.out.println("The result of dstClient.sortedLists().top(\"k1\") is: " +
            "{ First: " + slistTop.getList(0).getMember() +
            "; Second: " + slistTop.getList(1).getMember() +
            "; Third: " + slistTop.getList(2).getMember() + "; }");

      dstClient.disconnect();
    }
  }
}
