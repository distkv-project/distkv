package com.distkv.asyncclient.example;

import com.distkv.asyncclient.DefaultAsyncClient;
import com.distkv.common.entity.sortedList.SlistEntity;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.IntProtocol;
import com.distkv.rpc.protobuf.generated.SlistProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DistkvAsyncUsageExample {

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, InvalidProtocolBufferException {
    DefaultAsyncClient distkvClient = new DefaultAsyncClient("distkv://127.0.0.1:8082");
    if (distkvClient.isConnected()) {
      CompletableFuture<DistkvProtocol.DistkvResponse> strPutFuture =
          distkvClient.strs().put("k1", "asd");
      CompletableFuture<DistkvProtocol.DistkvResponse> listPutFuture =
          distkvClient.lists().put("k1", Arrays.asList("v1", "v2", "v3"));
      CompletableFuture<DistkvProtocol.DistkvResponse> setPutFuture =
          distkvClient.sets().put("k1", new HashSet<>(Arrays.asList("v1", "v2", "v3")));
      CompletableFuture<DistkvProtocol.DistkvResponse> intPutFuture =
          distkvClient.ints().put("k1", 1);
      Map<String, String> map = new HashMap<>();
      map.put("k1", "v1");
      map.put("k2", "v2");
      map.put("k3", "v3");
      CompletableFuture<DistkvProtocol.DistkvResponse> dictPutFuture =
          distkvClient.dicts().put("dict", map);
      LinkedList<SlistEntity> slist = new LinkedList<>();
      slist.add(new SlistEntity("a", 10));
      slist.add(new SlistEntity("b", 8));
      slist.add(new SlistEntity("c", 6));
      slist.add(new SlistEntity("d", 4));

      strPutFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("String put completed.");
        }
      });
      intPutFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("Int put completed.");
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
      intPutFuture.get();
      listPutFuture.get();
      setPutFuture.get();
      dictPutFuture.get();

      CompletableFuture<DistkvProtocol.DistkvResponse> strGetFuture =
          distkvClient.strs().get("k1");
      CompletableFuture<DistkvProtocol.DistkvResponse> intGetFuture =
          distkvClient.ints().get("k1");
      CompletableFuture<DistkvProtocol.DistkvResponse> listGetFuture =
          distkvClient.lists().get("k1");
      CompletableFuture<DistkvProtocol.DistkvResponse> setGetFuture =
          distkvClient.sets().get("k1");
      CompletableFuture<DistkvProtocol.DistkvResponse> dictGetFuture =
          distkvClient.dicts().get("dict");

      strGetFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("String get completed.");
        }
      });
      intGetFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("Int get completed.");
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

      DistkvProtocol.DistkvResponse strGet = strGetFuture.get();
      DistkvProtocol.DistkvResponse intGet = strGetFuture.get();
      DistkvProtocol.DistkvResponse listGet = listGetFuture.get();
      DistkvProtocol.DistkvResponse setGet = setGetFuture.get();
      DistkvProtocol.DistkvResponse dictGet = dictGetFuture.get();

      System.out.println("The result of distkvClient.strs().get(\"k1\") is: "
          + strGet.getResponse().unpack(StringProtocol.StrGetResponse.class).getValue());
      System.out.println("The result of distkvClient.ints().get(\"k1\") is: "
          + intGet.getResponse().unpack(IntProtocol.IntGetResponse.class).getValue());
      System.out.println("The result of distkvClient.lists().get(\"k1\") is: "
          + listGet.getResponse().unpack(ListProtocol.ListGetResponse.class).getValuesList());
      System.out.println("The result of distkvClient.sets().get(\"k1\") is: "
          + setGet.getResponse().unpack(SetProtocol.SetGetResponse.class).getValuesList());
      System.out.println("The result of distkvClient.dicts().get(\"dict1\") is: "
          + "\n" + dictGet.getResponse().unpack(DictProtocol.DictGetResponse.class).getDict());

      // SortedList example.
      CompletableFuture<DistkvProtocol.DistkvResponse> slistPutFuture =
          distkvClient.slists().put("k1", slist);
      CompletableFuture<DistkvProtocol.DistkvResponse> slistPutMFuture =
          distkvClient.slists().putMember("k1", new SlistEntity("s", 100));

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

      CompletableFuture<DistkvProtocol.DistkvResponse> slistTopFuture =
          distkvClient.slists().top("k1", 3);

      slistTopFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("SortedList top completed.");
        }
      });

      DistkvProtocol.DistkvResponse slistTop = slistTopFuture.get();
      SlistProtocol.SlistTopResponse slistTopResponse = slistTop.getResponse()
          .unpack(SlistProtocol.SlistTopResponse.class);
      System.out.println(slistTopResponse.getListCount());
      System.out.println(slistTopResponse.getListList());
      System.out.println(slistTopResponse.getList(0));
      System.out.println("The result of dstClient.sortedLists().top(\"k1\") is: " +
          "{ First: " + slistTopResponse.getList(0).getMember() +
          "; Second: " + slistTopResponse.getList(1).getMember() +
          "; Third: " + slistTopResponse.getList(2).getMember() + "; }");

      distkvClient.disconnect();
    }
  }
}
