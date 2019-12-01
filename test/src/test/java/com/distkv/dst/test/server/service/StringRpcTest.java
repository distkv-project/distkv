package com.distkv.dst.test.server.service;

import com.distkv.dst.common.utils.Utils;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import com.distkv.dst.rpc.service.DstStringService;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.distkv.dst.test.supplier.BaseTestSupplier;
import com.distkv.dst.test.supplier.ProxyOnClient;

public class StringRpcTest extends BaseTestSupplier {
  @Test
  public void testRpcServer() {
    try (ProxyOnClient<DstStringService> stringProxy = new ProxyOnClient<>(
        DstStringService.class, rpcServerPort)) {
      DstStringService stringService = stringProxy.getService();
      // Test string put request.
      StringProtocol.PutRequest putRequest =
              StringProtocol.PutRequest.newBuilder()
                      .setKey("k1")
                      .setValue("v1")
                      .build();

      StringProtocol.PutResponse response = Utils.getFromFuture(stringService.put(putRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, response.getStatus());

      // Test string get request.
      StringProtocol.GetRequest getRequest =
              StringProtocol.GetRequest.newBuilder()
                      .setKey("k1")
                      .build();

      StringProtocol.GetResponse response2 = Utils.getFromFuture(stringService.get(getRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, response2.getStatus());
      Assert.assertEquals("v1", response2.getValue());
    }
  }
}
