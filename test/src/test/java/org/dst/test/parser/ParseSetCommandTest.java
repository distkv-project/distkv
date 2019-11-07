package org.dst.test.parser;

import org.dst.common.exception.DstException;
import org.dst.parser.DstParser;
import org.dst.parser.po.DstParsedResult;
import org.dst.rpc.protobuf.generated.SetProtocol;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseSetCommandTest {

  private static final DstParser dstParser = new DstParser();

  @Test
  public void testSetPut() {
    final String command = "set.put \"k1\" \"v1\" \"v2\" \"v3\" \"v4\"";
    DstParsedResult result = dstParser.parse(command);
    final SetProtocol.PutRequest request = (SetProtocol.PutRequest) result.getRequest();
    Assert.assertEquals(SetProtocol.PutRequest.class, request.getClass());
    Assert.assertEquals("\"k1\"", request.getKey());
    Assert.assertEquals(4, request.getValuesCount());
    Assert.assertEquals("\"v1\"", request.getValues(0));
  }

  @Test
  public void testSetGet() {
    final String command = "set.get \"k1\"";
    DstParsedResult result = dstParser.parse(command);
    final SetProtocol.GetRequest request = (SetProtocol.GetRequest) result.getRequest();
    Assert.assertEquals(SetProtocol.GetRequest.class, request.getClass());
  }

  @Test(expectedExceptions = DstException.class)
  public void testInvalidCommand() {
    final String command = "set1.get \"k1\"";
    dstParser.parse(command);
  }
}
