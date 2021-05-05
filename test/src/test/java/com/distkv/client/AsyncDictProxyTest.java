package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncClient;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.supplier.BaseTestSupplier;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncDictProxyTest extends BaseTestSupplier {

  CommonProtocol.Status status = CommonProtocol.Status.OK;

  @Test
  public void testDict()
      throws ExecutionException, InterruptedException, TimeoutException,
      InvalidProtocolBufferException {
    DistkvAsyncClient client = newAsyncDistkvClient();
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    dict.put("k2", "v2");
    dict.put("k3", "v3");

    // TestPut
    CompletableFuture<DistkvResponse> putFuture =
        client.dicts().put("m1", dict);
    putFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestPutItem
    CompletableFuture<DistkvResponse> putItemFuture =
        client.dicts().putItem("m1", "k4", "v4");
    putItemFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestGetItem
    CompletableFuture<DistkvResponse> getItemFuture =
        client.dicts().getItem("m1", "k4");
    getItemFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestPopItem
    CompletableFuture<DistkvResponse> popItemFuture =
        client.dicts().popItem("m1", "k1");
    popItemFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestRemove
    CompletableFuture<DistkvResponse> removeFuture =
        client.dicts().removeItem("m1", "k4");
    removeFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    //TestGet
    CompletableFuture<DistkvResponse> getFuture =
        client.dicts().get("m1");
    getFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    //TestDrop
    CompletableFuture<DistkvResponse> dropFuture =
        client.drop("m1");
    dropFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    DistkvResponse putResponse = putFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse putItemResponse = putItemFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse getItemResponse = getItemFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse popItemResponse = popItemFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse removeItemResponse = removeFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse getResponse = getFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse dropResponse = dropFuture.get(1, TimeUnit.SECONDS);

    Assert.assertEquals(putResponse.getStatus(), status);
    Assert.assertEquals(putItemResponse.getStatus(), CommonProtocol.Status.OK);
    final Map<String, String> judgeDict = new HashMap<>();
    DictProtocol.DistKVDict result = getResponse.getResponse().unpack(DictGetResponse.class)
        .getDict();
    for (int i = 0; i < result.getKeysCount(); i++) {
      judgeDict.put(result.getKeys(i), result.getValues(i));
    }
    final Map<String, String> dict1 = new HashMap<>();
    dict1.put("k2", "v2");
    dict1.put("k3", "v3");
    Assert.assertEquals(judgeDict, dict1);
    Assert.assertEquals(dropResponse.getStatus(), CommonProtocol.Status.OK);
    client.disconnect();
  }
}
