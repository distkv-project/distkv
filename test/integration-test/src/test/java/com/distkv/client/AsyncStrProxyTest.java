package com.distkv.client;

import com.distkv.asyncclient.DstAsyncClient;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.test.base.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncStrProxyTest extends BaseTestSupplier {
  CommonProtocol.Status status = CommonProtocol.Status.OK;

  @Test
  public void testPutGet() throws ExecutionException, InterruptedException, TimeoutException {
    DstAsyncClient client = newAsyncDstClient();

    CompletableFuture<StringProtocol.PutResponse> putFuture =
            client.strs().put("k1", "v1");
    putFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    CompletableFuture<StringProtocol.GetResponse> getFuture =
            client.strs().get("k1");
    getFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    StringProtocol.PutResponse putResponse =
            putFuture.get(1, TimeUnit.SECONDS);
    StringProtocol.GetResponse getResponse =
            getFuture.get(1, TimeUnit.SECONDS);

    Assert.assertEquals(putResponse.getStatus(), status);
    Assert.assertEquals(getResponse.getStatus(), CommonProtocol.Status.OK);
    Assert.assertEquals(getResponse.getValue(), "v1");
    client.disconnect();
  }
}
