package com.distkv.dst.asyncclient.example;

import com.distkv.dst.asyncclient.DefaultAsyncClient;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;
import com.distkv.dst.rpc.protobuf.generated.ListProtocol;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import java.util.HashMap;
import java.util.Arrays;
import java.util.HashSet;
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
          System.out.println("The result of dstClient.strs().get(\"k1\") is: "
                  + r.getValue());
        }
      });
      listGetFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("The result of dstClient.lists().get(\"k1\") is: "
                  + r.getValuesList());
        }
      });
      setGetFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("The result of dstClient.sets().get(\"k1\") is: "
                  + r.getValuesList());
        }
      });
      dictGetFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        } else {
          System.out.println("The result of dstClient.dicts().get(\"dict1\") is: "
                  + "\n" + r.getDict());
        }
      });

      strGetFuture.get();
      listGetFuture.get();
      setGetFuture.get();
      dictGetFuture.get();

      dstClient.disconnect();
    }
  }
}
