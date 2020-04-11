package com.distkv.parser;

import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.DROP;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.EXISTS;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.EXPIRE;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.TTL;
import com.distkv.common.exception.DistkvException;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseBasicOperationCommandTest {

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
    Assert.assertEquals(result.getRequestType(), EXPIRE);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
  }

  @Test
  public void testExist() {
    final String command = "exists k1";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), EXISTS);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
  }

  @Test
  public void testTTL() {
    final String command = "ttl k1";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), TTL);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
  }

  @Test
  public void testDropManyKeys() {
    final String command = "drop k1 k2 k3";
    Assert.assertThrows(DistkvException.class, () ->  distkvParser.parse(command));
  }

  @Test
  public void testDropWithoutKey() {
    final String command = "drop";
    Assert.assertThrows(DistkvException.class, () -> distkvParser.parse(command));
  }

  @Test
  public void testExpireManyTime() {
    final String command = "expire k1 1000 1000";
    Assert.assertThrows(DistkvException.class, () ->  distkvParser.parse(command));
  }

  @Test
  public void testExpireWithoutTime() {
    final String command = "expire k1";
    Assert.assertThrows(DistkvException.class, () ->  distkvParser.parse(command));
  }

  @Test
  public void testExpireWithoutKeyAndTime() {
    final String command = "expire";
    Assert.assertThrows(DistkvException.class, () -> distkvParser.parse(command));
  }


  @Test
  public void testExistManyKeys() {
    final String command = "exists k1 k2 k3";
    Assert.assertThrows(DistkvException.class, () ->  distkvParser.parse(command));
  }

  @Test
  public void testExistWithoutKey() {
    final String command = "exists";
    Assert.assertThrows(DistkvException.class, () -> distkvParser.parse(command));
  }


  @Test
  public void testTTLManyKeys() {
    final String command = "ttl k1 k2 k3";
    Assert.assertThrows(DistkvException.class, () -> distkvParser.parse(command));
  }

  @Test
  public void testTTLWithoutKey() {
    final String command = "ttl";
    Assert.assertThrows(DistkvException.class, () -> distkvParser.parse(command));
  }

}
