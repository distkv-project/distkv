package org.dst.test.parser;

import org.dst.common.exception.DstException;
import org.dst.parser.DstParser;
import org.dst.parser.po.DstParsedResult;
import org.dst.parser.po.RequestTypeEnum;
import org.dst.rpc.protobuf.generated.StringProtocol;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseStringCommandTest {

  private static final DstParser dstParser = new DstParser();

  @Test
  public void testPut() {
    final String command = "str.put k1 v1";
    DstParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.STR_PUT);
    StringProtocol.PutRequest request = (StringProtocol.PutRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getValue(), "v1");
  }

  @Test
  public void testGet() {
    final String command = "str.get k1 v1";
    DstParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.STR_GET);
    StringProtocol.GetRequest request = (StringProtocol.GetRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
  }

  @Test(expectedExceptions = DstException.class)
  public void testInvalidPutCommand() {
    final String command = "str.put k1 v1 v2";
    dstParser.parse(command);
  }

  @Test(expectedExceptions = DstException.class)
  public void testInvalidGetCommand() {
    final String command = "str.get k1 v1";
    dstParser.parse(command);
  }

  @Test(expectedExceptions = DstException.class)
  public void testInvalidStrCommand() {
    final String command = "str.get";
    dstParser.parse(command);
  }
}
