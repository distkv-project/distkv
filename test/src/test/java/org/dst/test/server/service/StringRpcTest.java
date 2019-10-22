package org.dst.test.server.service;

import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.StringProtocol;
import org.dst.rpc.service.DstStringService;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.dst.test.supplier.BaseTestSupplier;
import org.dst.test.supplier.ProxyOnClient;

public class StringRpcTest extends BaseTestSupplier {
  @Test
  public void testRpcServer() {
    try (ProxyOnClient<DstStringService> setProxy = new ProxyOnClient<>(DstStringService.class)) {
      DstStringService stringService = setProxy.getService();
      // Test string put request
      StringProtocol.PutRequest putRequest =
              StringProtocol.PutRequest.newBuilder()
                      .setKey("k1")
                      .setValue("v1")
                      .build();

      StringProtocol.PutResponse putResponse = stringService.put(putRequest);
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());
      // Test string get request
      StringProtocol.GetRequest getRequest =
              StringProtocol.GetRequest.newBuilder()
                      .setKey("k1")
                      .build();

      StringProtocol.GetResponse getResponse = stringService.get(getRequest);
      Assert.assertEquals("v1", getResponse.getValue());
    }
  }
}
