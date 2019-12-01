package com.distkv.dst.test.server.service;

import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import com.distkv.dst.rpc.service.DstStringService;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.distkv.dst.test.supplier.BaseTestSupplier;
import com.distkv.dst.test.supplier.ProxyOnClient;
import java.util.concurrent.CompletableFuture;

public class StringRpcTest extends BaseTestSupplier {
  @Test
  public void testRpcServer() {
    try (ProxyOnClient<DstStringService> stringProxy = new ProxyOnClient<>(
        DstStringService.class, rpcServerPort)) {
      DstStringService stringService = stringProxy.getService();
      // Test string put request
      StringProtocol.PutRequest putRequest =
              StringProtocol.PutRequest.newBuilder()
                      .setKey("k1")
                      .setValue("v1")
                      .build();

      CompletableFuture<StringProtocol.PutResponse> putFuture = stringService.put(putRequest);
      putFuture.whenComplete((response, throwable) -> {
        Assert.assertEquals(CommonProtocol.Status.OK, response.getStatus());
        Assert.assertNotNull(throwable);
      });

      // Test string get request.
      StringProtocol.GetRequest getRequest =
              StringProtocol.GetRequest.newBuilder()
                      .setKey("k1")
                      .build();

      CompletableFuture<StringProtocol.GetResponse> getFuture = stringService.get(getRequest);
      getFuture.whenComplete((response, throwable) -> {
        Assert.assertEquals(CommonProtocol.Status.OK, response.getStatus());
        Assert.assertEquals("v1", response.getValue());
        Assert.assertNotNull(throwable);
      });
    }
  }
}
