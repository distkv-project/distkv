package com.distkv.parser;

import com.distkv.common.exception.DistkvException;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.IntProtocol;

import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseIntCommandTest {

  private static final DistkvParser distKVParser = new DistkvParser();

  @Test
  public void testPut() throws InvalidProtocolBufferException {
    final String command = "int.put key1 1";
    DistkvParsedResult result = distKVParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.INT_PUT);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "key1");
    Assert.assertEquals(
        request.getRequest().unpack(IntProtocol.IntPutRequest.class).getValue(), 1);
  }

  @Test
  public void testGet() {
    final String command = "int.get key1";
    DistkvParsedResult result = distKVParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.INT_GET);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "key1");
  }

  @Test
  public void testIncrDefault() throws InvalidProtocolBufferException {
    final String command = "int.incr key1";
    DistkvParsedResult result = distKVParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.INT_INCR);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "key1");
    Assert.assertEquals(
        request.getRequest().unpack(IntProtocol.IntIncrRequest.class).getDelta(), 1);
  }

  @Test
  public void testIncrDelta() throws InvalidProtocolBufferException {
    final String command = "int.incr key1 -12";
    DistkvParsedResult result = distKVParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.INT_INCR);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "key1");
    Assert.assertEquals(
        request.getRequest().unpack(IntProtocol.IntIncrRequest.class).getDelta(), -12);
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidPutCommand() {
    final String command = "int.put k1 1 2 3";
    distKVParser.parse(command);
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidPutCommand2() {
    final String command = "int.put abc";
    distKVParser.parse(command);
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidGetCommand() {
    final String command = "int.get k1 v1";
    distKVParser.parse(command);
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidGetCommand2() {
    final String command = "int.get";
    distKVParser.parse(command);
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidIncrCommand() {
    final String command = "int.incr k1 v1";
    distKVParser.parse(command);
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidIncrCommand2() {
    final String command = "int.incr k1 12 13";
    distKVParser.parse(command);
  }
}
