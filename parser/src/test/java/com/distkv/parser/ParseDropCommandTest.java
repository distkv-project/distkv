package com.distkv.parser;

import com.distkv.common.exception.DistkvException;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseDropCommandTest {

  private static final DistkvParser distKVParser = new DistkvParser();

  @Test
  public void testDrop() {
    final String command = "drop k1";
    DistkvParsedResult result = distKVParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.DROP);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidCommand() {
    final String command = "drop k1";
    distKVParser.parse(command);
  }
}
