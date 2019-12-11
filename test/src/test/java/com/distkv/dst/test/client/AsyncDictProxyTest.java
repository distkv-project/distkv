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

public class AsyncDictProxyTest extends BaseTestSupplier {

  @Test
  public void testAsyncDict() {
    DstAsyncClient client = newAsyncDstClient();
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    dict.put("k2", "v2");
    dict.put("k3", "v3");

    // TestPut
    CompletableFuture<DictProtocol.PutResponse> futurePut =
            client.dicts().put("m1", dict);
    futurePut.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // TestPutItem
    CompletableFuture<DictProtocol.PutItemResponse> futurePutItem =
            client.dicts().putItem("m1", "k4", "v4");
    futurePutItem.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    // TestGetItem
    CompletableFuture<DictProtocol.GetItemResponse> futureGetItem =
            client.dicts().getItem("m1", "k4");
    futureGetItem.whenComplete((r, t) -> {
      Assert.assertEquals(r.getItemValue(), "v4");
    });

    // TestPopItem
    CompletableFuture<DictProtocol.PopItemResponse> futurePopItem =
            client.dicts().popItem("m1", "k1");
    futurePopItem.whenComplete((r, t) -> {
      Assert.assertEquals(r.getItemValue(), "v1");
    });

    // TestRemove
    CompletableFuture<DictProtocol.RemoveItemResponse> futureRemove =
            client.dicts().removeItem("m1", "k4");
    futureRemove.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    //TestGet
    CompletableFuture<DictProtocol.GetResponse> futureGet =
            client.dicts().get("m1");
    futureGet.whenComplete((r, t) -> {
      final Map<String, String> judgeDict = new HashMap<>();
      DictProtocol.DstDict result = r.getDict();
      for (int i = 0; i < result.getKeysCount(); i++) {
        judgeDict.put(result.getKeys(i), result.getValues(i));
      }
      final Map<String, String> dict1 = new HashMap<>();
      dict1.put("k2", "v2");
      dict1.put("k3", "v3");
      Assert.assertEquals(judgeDict, dict1);
    });

    //TestDrop
    CompletableFuture<CommonProtocol.DropResponse> futureDrop =
            client.dicts().drop("m1");
    futureDrop.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    try {
      futurePut.get();
      futurePutItem.get();
      futureGetItem.get();
      futurePopItem.get();
      futureRemove.get();
      futureGet.get();
      futureDrop.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    client.disconnect();
  }
}
