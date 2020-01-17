package com.distkv.dst.parser;

import com.distkv.common.exception.DstException;
import com.distkv.dst.parser.po.DstParsedResult;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseSetCommandTest {

  private static final DstParser dstParser = new DstParser();

  @Test
  public void testSetPut() {
    final String command = "set.put k1 v1 v2 v3 v4";
    DstParsedResult result = dstParser.parse(command);
    final SetProtocol.PutRequest request = (SetProtocol.PutRequest) result.getRequest();
    Assert.assertEquals(SetProtocol.PutRequest.class, request.getClass());
    Assert.assertEquals("k1", request.getKey());
    Assert.assertEquals(4, request.getValuesCount());
    Assert.assertEquals("v1", request.getValues(0));
    Assert.assertEquals("v2", request.getValues(1));
    Assert.assertEquals("v3", request.getValues(2));
    Assert.assertEquals("v4", request.getValues(3));
  }

  @Test
  public void testSetGet() {
    final String command = "set.get k1";
    DstParsedResult result = dstParser.parse(command);
    final SetProtocol.GetRequest request = (SetProtocol.GetRequest) result.getRequest();
    Assert.assertEquals(SetProtocol.GetRequest.class, request.getClass());
    Assert.assertEquals("k1", request.getKey());
  }

  @Test(expectedExceptions = DstException.class)
  public void testInvalidCommandName() {
    final String command = "set1.get k1";
    dstParser.parse(command);
  }

  @Test(expectedExceptions = DstException.class)
  public void testInvalidValue() {
    final String command = "set.get k1 k2";
    dstParser.parse(command);
  }
}
