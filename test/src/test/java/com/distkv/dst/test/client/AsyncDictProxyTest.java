package com.distkv.dst.test.client;

import com.distkv.dst.asyncclient.DstAsyncClient;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;
import com.distkv.dst.test.supplier.BaseTestSupplier;
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
  public void testDict() throws ExecutionException, InterruptedException, TimeoutException {
    DstAsyncClient client = newAsyncDstClient();
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    dict.put("k2", "v2");
    dict.put("k3", "v3");

    // TestPut
    CompletableFuture<DictProtocol.PutResponse> putFuture =
            client.dicts().put("m1", dict);
    putFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestPutItem
    CompletableFuture<DictProtocol.PutItemResponse> putItemFuture =
            client.dicts().putItem("m1", "k4", "v4");
    putItemFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestGetItem
    CompletableFuture<DictProtocol.GetItemResponse> getItemFuture =
            client.dicts().getItem("m1", "k4");
    getItemFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestPopItem
    CompletableFuture<DictProtocol.PopItemResponse> popItemFuture =
            client.dicts().popItem("m1", "k1");
    popItemFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    // TestRemove
    CompletableFuture<DictProtocol.RemoveItemResponse> removeFuture =
            client.dicts().removeItem("m1", "k4");
    removeFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    //TestGet
    CompletableFuture<DictProtocol.GetResponse> getFuture =
            client.dicts().get("m1");
    getFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    //TestDrop
    CompletableFuture<CommonProtocol.DropResponse> dropFuture =
            client.dicts().drop("m1");
    dropFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    DictProtocol.PutResponse putResponse =
            putFuture.get(1, TimeUnit.SECONDS);
    DictProtocol.PutItemResponse putItemResponse =
            putItemFuture.get(1, TimeUnit.SECONDS);
    DictProtocol.GetItemResponse getItemResponse =
            getItemFuture.get(1, TimeUnit.SECONDS);
    DictProtocol.PopItemResponse popItemResponse =
            popItemFuture.get(1, TimeUnit.SECONDS);
    DictProtocol.RemoveItemResponse removeItemResponse =
            removeFuture.get(1, TimeUnit.SECONDS);
    DictProtocol.GetResponse getResponse =
            getFuture.get(1, TimeUnit.SECONDS);
    CommonProtocol.DropResponse dropResponse =
            dropFuture.get(1, TimeUnit.SECONDS);

    Assert.assertEquals(putResponse.getStatus(), status);
    Assert.assertEquals(putItemResponse.getStatus(), status);
    Assert.assertEquals(getItemResponse.getItemValue(), "v4");
    Assert.assertEquals(popItemResponse.getItemValue(), "v1");
    Assert.assertEquals(removeItemResponse.getStatus(), CommonProtocol.Status.OK);
    final Map<String, String> judgeDict = new HashMap<>();
    DictProtocol.DstDict result = getResponse.getDict();
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
