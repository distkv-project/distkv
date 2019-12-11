package com.distkv.dst.test.client;

import com.distkv.dst.asyncclient.DstAsyncClient;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import com.distkv.dst.test.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.CompletableFuture;

public class AsyncStrProxyTest extends BaseTestSupplier {

  @Test
  public void testPutGetDrop() {
    DstAsyncClient client = newAsyncDstClient();
    CompletableFuture<StringProtocol.PutResponse> futurePut =
            client.strs().put("k1", "v1");
    futurePut.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    CompletableFuture<StringProtocol.GetResponse> futureGet =
            client.strs().get("k1");
    futureGet.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
      Assert.assertEquals(r.getValue(), "v1");
    });

    CompletableFuture<CommonProtocol.DropResponse> futureDrop =
            client.strs().drop("k1");
    futureDrop.whenComplete((r, t) -> {
      Assert.assertEquals(r.getStatus(), CommonProtocol.Status.OK);
    });

    client.disConnect();
  }
}
