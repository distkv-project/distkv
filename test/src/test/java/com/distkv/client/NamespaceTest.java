package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncClient;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.supplier.BaseTestSupplier;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class NamespaceTest extends BaseTestSupplier {

  @Test
  public void testNamespaceEnabled()
      throws ExecutionException, InterruptedException, InvalidProtocolBufferException {
    DistkvAsyncClient client1 = newAsyncDistkvClient();
    DistkvAsyncClient client2 = newAsyncDistkvClient();

    client1.activeNamespace("a");
    client2.activeNamespace("b");

    CompletableFuture<DistkvProtocol.DistkvResponse> putFuture =
        client1.strs().put("nsp_k1", "v1");
    Assert.assertEquals(CommonProtocol.Status.OK, putFuture.get().getStatus());
    putFuture = client2.strs().put("nsp_k1", "v2");
    Assert.assertEquals(CommonProtocol.Status.OK, putFuture.get().getStatus());

    CompletableFuture<DistkvProtocol.DistkvResponse> getFuture = client1.strs().get("nsp_k1");
    String value = getFuture.get()
        .getResponse().unpack(StringProtocol.StrGetResponse.class).getValue();
    Assert.assertEquals(value, "v1");

    getFuture = client2.strs().get("nsp_k1");
    value = getFuture.get()
        .getResponse().unpack(StringProtocol.StrGetResponse.class).getValue();
    Assert.assertEquals(value, "v2");

    //drop
    client1.drop("nsp_k1").get();
    client2.drop("nsp_k1").get();

    //close
    client1.disconnect();
    client2.disconnect();
  }

  @Test
  public void testNamespaceDisabled()
      throws ExecutionException, InterruptedException  {
    DistkvAsyncClient client1 = newAsyncDistkvClient();
    DistkvAsyncClient client2 = newAsyncDistkvClient();

    CompletableFuture<DistkvProtocol.DistkvResponse> putFuture =
        client1.strs().put("nsp_k1", "v1");

    Assert.assertEquals(CommonProtocol.Status.OK, putFuture.get().getStatus());
    final CompletableFuture<DistkvProtocol.DistkvResponse> putFuture2 =
        client2.strs().put("nsp_k1", "v2");
    Assert.assertEquals(CommonProtocol.Status.DUPLICATED_KEY, putFuture2.get().getStatus());

    //drop
    client1.drop("nsp_k1").get();
    client2.drop("nsp_k1").get();
    //close
    client1.disconnect();
    client2.disconnect();
  }


  @Test
  public void testNamespaceDeactive()
      throws ExecutionException, InterruptedException, InvalidProtocolBufferException {
    DistkvAsyncClient client1 = newAsyncDistkvClient();
    DistkvAsyncClient client2 = newAsyncDistkvClient();

    client1.activeNamespace("d");

    CompletableFuture<DistkvProtocol.DistkvResponse> putFuture =
        client1.strs().put("nsp_k1", "v1");
    Assert.assertEquals(CommonProtocol.Status.OK, putFuture.get().getStatus());
    putFuture = client2.strs().put("nsp_k1", "v2");
    Assert.assertEquals(CommonProtocol.Status.OK, putFuture.get().getStatus());

    CompletableFuture<DistkvProtocol.DistkvResponse> getFuture = client1.strs().get("nsp_k1");
    String value = getFuture.get()
        .getResponse().unpack(StringProtocol.StrGetResponse.class).getValue();
    Assert.assertEquals(value, "v1");

    getFuture = client2.strs().get("nsp_k1");
    value = getFuture.get()
        .getResponse().unpack(StringProtocol.StrGetResponse.class).getValue();
    Assert.assertEquals(value, "v2");

    client1.deactiveNamespace();
    CompletableFuture<DistkvProtocol.DistkvResponse> getFuture1 = client1.strs().get("nsp_k1");
    String value1 = getFuture1.get()
        .getResponse().unpack(StringProtocol.StrGetResponse.class).getValue();
    Assert.assertEquals(value1, "v2");

    //drop
    client2.drop("nsp_k1").get();

    //close
    client1.disconnect();
    client2.disconnect();
  }

}
