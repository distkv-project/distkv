package com.distkv.parser;

import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseDropCommandTest {

  private static final DistkvParser distKVParser = new DistkvParser();

  @Test
  public void testDrop() {
    final String getDictCommand = "drop k1";
    DistkvParsedResult result = distKVParser.parse(getDictCommand);
    Assert.assertEquals(result.getRequestType(), RequestType.DROP);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
  }
}
