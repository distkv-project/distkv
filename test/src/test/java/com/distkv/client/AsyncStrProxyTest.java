package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncClient;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.StringProtocol.StrGetResponse;
import com.distkv.supplier.BaseTestSupplier;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncStrProxyTest extends BaseTestSupplier {

  CommonProtocol.Status status = CommonProtocol.Status.OK;

  @Test
  public void testPutGet()
      throws ExecutionException, InterruptedException, TimeoutException,
      InvalidProtocolBufferException {
    DistkvAsyncClient client = newAsyncDistkvClient();

    CompletableFuture<DistkvResponse> putFuture =
        client.strs().put("async_str_k1", "v1");
    putFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    CompletableFuture<DistkvResponse> getFuture =
        client.strs().get("async_str_k1");
    getFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    CompletableFuture<DistkvResponse> dropFuture =
        client.drop("async_str_k1");
    dropFuture.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      }
    });

    DistkvResponse putResponse =
        putFuture.get(1, TimeUnit.SECONDS);
    DistkvResponse getResponse =
        getFuture.get(1, TimeUnit.SECONDS);
    dropFuture.get();


    Assert.assertEquals(putResponse.getStatus(), status);
    Assert.assertEquals(getResponse.getStatus(), CommonProtocol.Status.OK);
    Assert.assertEquals(getResponse.getResponse()
        .unpack(StrGetResponse.class).getValue(), "v1");
    client.disconnect();
  }
}
