package com.distkv.parser;

import com.distkv.common.exception.DistkvException;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseStringCommandTest {

  private static final DistkvParser dstParser = new DistkvParser();

  @Test
  public void testPut() {
    final String command = "str.put k1 v1";
    DistkvParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.STR_PUT);
    StringProtocol.PutRequest request = (StringProtocol.PutRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getValue(), "v1");
  }

  @Test
  public void testGet() {
    final String command = "str.get k1";
    DistkvParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.STR_GET);
    StringProtocol.GetRequest request = (StringProtocol.GetRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
  }

  @Test
  public void testDrop() {
    final String command = "str.drop k1";
    DistkvParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.STR_DROP);
    CommonProtocol.DropRequest request = (CommonProtocol.DropRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidPutCommand() {
    final String command = "str.put k1 v1 v2";
    dstParser.parse(command);
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidGetCommand() {
    final String command = "str.get k1 v1";
    dstParser.parse(command);
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidStrCommand() {
    final String command = "str.get";
    dstParser.parse(command);
  }
}
