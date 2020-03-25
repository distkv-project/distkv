package com.distkv.parser;

import com.distkv.common.exception.DistkvException;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetPutRequest;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseSetCommandTest {

  private static final DistkvParser distkvParser = new DistkvParser();

  @Test
  public void testSetPut() throws InvalidProtocolBufferException {
    final String command = "set.put k1 v1 v2 v3 v4";
    DistkvParsedResult result = distkvParser.parse(command);
    final DistkvRequest request = result.getRequest();
    Assert.assertEquals(DistkvRequest.class, request.getClass());
    Assert.assertEquals("k1", request.getKey());
    Assert.assertEquals(4, request.getRequest()
        .unpack(SetPutRequest.class).getValuesCount());
    Assert.assertEquals("v1", request.getRequest()
        .unpack(SetPutRequest.class).getValues(0));
    Assert.assertEquals("v2", request.getRequest()
        .unpack(SetPutRequest.class).getValues(1));
    Assert.assertEquals("v3", request.getRequest()
        .unpack(SetPutRequest.class).getValues(2));
    Assert.assertEquals("v4", request.getRequest()
        .unpack(SetPutRequest.class).getValues(3));
  }

  @Test
  public void testSetGet() {
    final String command = "set.get k1";
    DistkvParsedResult result = distkvParser.parse(command);
    final DistkvRequest request = result.getRequest();
    Assert.assertEquals(DistkvRequest.class, request.getClass());
    Assert.assertEquals("k1", request.getKey());
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidCommandName() {
    final String command = "set1.get k1";
    distkvParser.parse(command);
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidValue() {
    final String command = "set.get k1 k2";
    distkvParser.parse(command);
  }
}
