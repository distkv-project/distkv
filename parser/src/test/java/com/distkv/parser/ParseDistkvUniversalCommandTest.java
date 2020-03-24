package com.distkv.parser;

import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.DROP;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.EXPIRED;
import com.distkv.common.exception.DistkvException;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseDistkvUniversalCommandTest {

  private static final DistkvParser distkvParser = new DistkvParser();

  @Test
  public void testDrop() {
    final String command = "drop k1";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), DROP);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
  }

  @Test
  public void testExpire() {
    final String command = "expire k1 1000";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), EXPIRED);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
  }

  @Test
  public void testDrop1InvalidCommand() {
    final String command = "drop k1 v1 v2";
    Assert.assertThrows(DistkvException.class, () ->  distkvParser.parse(command));
  }

  @Test
  public void testDrop2InvalidCommand() {
    final String command = "drop";
    Assert.assertThrows(DistkvException.class, () -> distkvParser.parse(command));
  }

  @Test
  public void testExpire1InvalidCommand() {
    final String command = "expire k1 v1 v2";
    Assert.assertThrows(DistkvException.class, () ->  distkvParser.parse(command));
  }

  @Test
  public void testExpire2InvalidCommand() {
    final String command = "expire k1 1000 1000";
    Assert.assertThrows(DistkvException.class, () ->  distkvParser.parse(command));
  }

  @Test
  public void testExpire3InvalidCommand() {
    final String command = "expire";
    Assert.assertThrows(DistkvException.class, () -> distkvParser.parse(command));
  }
}
