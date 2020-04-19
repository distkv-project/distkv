package com.distkv.parser;

import com.distkv.common.exception.DistkvException;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListGetRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListLPutRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListMRemoveRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListPutRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListRemoveRequest;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseListCommandTest {

  private static final DistkvParser distkvParser = new DistkvParser();

  @Test
  public void testPut() throws InvalidProtocolBufferException {
    final String command = "list.put k1 v1 v2";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.LIST_PUT);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getRequest()
        .unpack(ListPutRequest.class).getValuesCount(), 2);
    Assert.assertEquals(request.getRequest()
        .unpack(ListPutRequest.class).getValues(0), "v1");
    Assert.assertEquals(request.getRequest()
        .unpack(ListPutRequest.class).getValues(1), "v2");
  }

  @Test
  public void testGetAll() throws InvalidProtocolBufferException {
    final String command = "list.get k1";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.LIST_GET);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getRequest()
        .unpack(ListGetRequest.class).getType(), ListProtocol.GetType.GET_ALL);
    Assert.assertEquals(request.getKey(), "k1");
  }

  @Test
  public void testGetOne() throws InvalidProtocolBufferException {
    final String command = "list.get k1 3";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.LIST_GET);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getRequest()
        .unpack(ListGetRequest.class).getType(), ListProtocol.GetType.GET_ONE);
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getRequest()
        .unpack(ListGetRequest.class).getIndex(), 3);
  }

  @Test
  public void testGetRange() throws InvalidProtocolBufferException {
    final String command = "list.get k1 4 9";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.LIST_GET);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getRequest()
        .unpack(ListGetRequest.class).getType(), ListProtocol.GetType.GET_RANGE);
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getRequest()
        .unpack(ListGetRequest.class).getFrom(), 4);
    Assert.assertEquals(request.getRequest().unpack(ListGetRequest.class).getEnd(), 9);
  }

  @Test
  public void testLput() throws InvalidProtocolBufferException {
    final String command = "list.lput k1 v1 v2";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.LIST_LPUT);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getRequest()
        .unpack(ListLPutRequest.class).getValuesCount(), 2);
    Assert.assertEquals(request.getRequest()
        .unpack(ListLPutRequest.class).getValues(0), "v1");
    Assert.assertEquals(request.getRequest()
        .unpack(ListLPutRequest.class).getValues(1), "v2");
  }

  @Test
  public void testRput() {
    // TODO(qwang): Should be finished.
  }

  @Test
  public void testRemoveOne() throws InvalidProtocolBufferException {
    final String command = "list.remove k1 3";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.LIST_REMOVE);
    DistkvRequest request = result.getRequest();

    Assert.assertEquals(request.getRequest()
        .unpack(ListRemoveRequest.class).getType(), ListProtocol.RemoveType.RemoveOne);
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getRequest()
        .unpack(ListRemoveRequest.class).getIndex(), 3);
  }

  @Test
  public void testRemoveRange() throws InvalidProtocolBufferException {
    final String command = "list.remove k1 3 5";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.LIST_REMOVE);
    DistkvRequest request = result.getRequest();

    Assert.assertEquals(request.getRequest()
        .unpack(ListRemoveRequest.class).getType(), ListProtocol.RemoveType.RemoveRange);
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getRequest().unpack(ListRemoveRequest.class).getFrom(), 3);
    Assert.assertEquals(request.getRequest().unpack(ListRemoveRequest.class).getEnd(), 5);
  }

  @Test
  public void testMRemove() throws InvalidProtocolBufferException {
    final String command = "list.mremove k1 2 4 5 7";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.LIST_MREMOVE);
    DistkvRequest request = result.getRequest();

    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getRequest()
        .unpack(ListMRemoveRequest.class).getIndexes(0), 2);
    Assert.assertEquals(request.getRequest()
        .unpack(ListMRemoveRequest.class).getIndexes(1), 4);
    Assert.assertEquals(request.getRequest()
        .unpack(ListMRemoveRequest.class).getIndexes(2), 5);
    Assert.assertEquals(request.getRequest()
        .unpack(ListMRemoveRequest.class).getIndexes(3), 7);
  }

  @Test
  public void testRdel() {
    // TODO(qwang): Should be finished.
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidPutCommand() {
    final String command = "list.ldel k1";
    distkvParser.parse(command);
  }

}
