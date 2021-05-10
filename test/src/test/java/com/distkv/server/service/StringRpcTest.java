package com.distkv.server.service;

import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol.StrGetResponse;
import com.distkv.rpc.service.DistkvService;
import com.distkv.supplier.BaseTestSupplier;
import com.distkv.supplier.ProxyOnClient;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StringRpcTest extends BaseTestSupplier {

  @Test
  public void testRpcServer() throws InvalidProtocolBufferException {
    try (ProxyOnClient<DistkvService> stringProxy = new ProxyOnClient<>(
        DistkvService.class, KVSTORE_PORT)) {
      DistkvService stringService = stringProxy.getService();
      // Test string put request.
      StringProtocol.StrPutRequest strPutRequest =
          StringProtocol.StrPutRequest.newBuilder()
              .setValue("v1")
              .build();
      DistkvRequest putRequest = DistkvRequest.newBuilder()
          .setKey("str_r_k1")
          .setRequestType(RequestType.STR_PUT)
          .setRequest(Any.pack(strPutRequest))
          .build();
      DistkvResponse putResponse = FutureUtils.get(stringService.call(putRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

      // Test string get request.
      DistkvRequest getRequest = DistkvRequest.newBuilder()
          .setKey("str_r_k1")
          .setRequestType(RequestType.STR_GET)
          .build();
      DistkvResponse getResponse = FutureUtils.get(stringService.call(getRequest));
      Assert.assertEquals(CommonProtocol.Status.OK, getResponse.getStatus());
      Assert.assertEquals("v1", getResponse.getResponse()
          .unpack(StrGetResponse.class).getValue());
    }
  }
}
