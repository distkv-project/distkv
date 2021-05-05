package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncClient;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.IntProtocol.IntGetResponse;
import com.distkv.supplier.BaseTestSupplier;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncIntProxyTest extends BaseTestSupplier {

  CommonProtocol.Status status = CommonProtocol.Status.OK;

  @Test
  public void testPutGetIncrDrop()
      throws ExecutionException, InterruptedException, TimeoutException,
      InvalidProtocolBufferException {
    DistkvAsyncClient client = newAsyncDistkvClient();

    try {
      CompletableFuture<DistkvResponse> putFuture =
          client.ints().put("int_k1", 1);
      putFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        }
      });

      CompletableFuture<DistkvResponse> getFuture =
          client.ints().get("int_k1");
      getFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        }
      });

      CompletableFuture<DistkvResponse> incrFuture =
          client.ints().incr("int_k1", 2);
      getFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        }
      });

      CompletableFuture<DistkvResponse> dropFuture =
          client.drop("int_k1");
      getFuture.whenComplete((r, t) -> {
        if (t != null) {
          throw new IllegalStateException(t);
        }
      });

      DistkvResponse putResponse =
          putFuture.get(1, TimeUnit.SECONDS);
      DistkvResponse getResponse =
          getFuture.get(1, TimeUnit.SECONDS);
      DistkvResponse incrResponse =
          incrFuture.get(1, TimeUnit.SECONDS);
      DistkvResponse dropResponse =
          dropFuture.get(1, TimeUnit.SECONDS);

      Assert.assertEquals(putResponse.getStatus(), status);
      Assert.assertEquals(getResponse.getStatus(), status);
      Assert.assertEquals(getResponse.getResponse()
          .unpack(IntGetResponse.class).getValue(), 1);
      Assert.assertEquals(dropResponse.getStatus(), status);
      Assert.assertEquals(incrResponse.getStatus(), status);

    } finally {
      client.disconnect();
    }
  }
}
