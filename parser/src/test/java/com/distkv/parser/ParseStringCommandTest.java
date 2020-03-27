package com.distkv.parser;

import com.distkv.common.exception.DistkvException;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.StringProtocol.StrPutRequest;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseStringCommandTest {

  private static final DistkvParser distkvParser = new DistkvParser();

  @Test
  public void testPut() throws InvalidProtocolBufferException {
    final String command = "str.put k1 v1";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.STR_PUT);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getRequest().unpack(StrPutRequest.class).getValue(), "v1");
  }

  @Test
  public void testGet() {
    final String command = "str.get k1";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.STR_GET);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidPutCommand() {
    final String command = "str.put k1 v1 v2";
    distkvParser.parse(command);
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidGetCommand() {
    final String command = "str.get k1 v1";
    distkvParser.parse(command);
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidStrCommand() {
    final String command = "str.get";
    distkvParser.parse(command);
  }
}
