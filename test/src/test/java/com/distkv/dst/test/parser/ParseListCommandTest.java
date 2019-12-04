package com.distkv.dst.test.parser;

import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.parser.DstParser;
import com.distkv.dst.parser.po.DstParsedResult;
import com.distkv.dst.parser.po.RequestTypeEnum;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.ListProtocol;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseListCommandTest {

  private static final DstParser dstParser = new DstParser();

  @Test
  public void testPut() {
    final String command = "list.put k1 v1 v2";
    DstParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.LIST_PUT);
    ListProtocol.PutRequest request = (ListProtocol.PutRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getValuesCount(), 2);
    Assert.assertEquals(request.getValues(0), "v1");
    Assert.assertEquals(request.getValues(1), "v2");
  }

  @Test
  public void testGetAll() {
    final String command = "list.get k1";
    DstParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.LIST_GET);
    ListProtocol.GetRequest request = (ListProtocol.GetRequest) result.getRequest();
    Assert.assertEquals(request.getType(), ListProtocol.GetType.GET_ALL);
    Assert.assertEquals(request.getKey(), "k1");
  }

  @Test
  public void testGetOne() {
    final String command = "list.get k1 3";
    DstParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.LIST_GET);
    ListProtocol.GetRequest request = (ListProtocol.GetRequest) result.getRequest();
    Assert.assertEquals(request.getType(), ListProtocol.GetType.GET_ONE);
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getIndex(), 3);
  }

  @Test
  public void testGetRange() {
    final String command = "list.get k1 4 9";
    DstParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.LIST_GET);
    ListProtocol.GetRequest request = (ListProtocol.GetRequest) result.getRequest();
    Assert.assertEquals(request.getType(), ListProtocol.GetType.GET_RANGE);
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getFrom(), 4);
    Assert.assertEquals(request.getEnd(), 9);
  }

  @Test
  public void testLput() {
    final String command = "list.lput k1 v1 v2";
    DstParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.LIST_LPUT);
    ListProtocol.LPutRequest request = (ListProtocol.LPutRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getValuesCount(), 2);
    Assert.assertEquals(request.getValues(0), "v1");
    Assert.assertEquals(request.getValues(1), "v2");
  }

  @Test
  public void testDrop() {
    final String command = "list.drop k1";
    DstParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.LIST_DROP);
    CommonProtocol.DropRequest request = (CommonProtocol.DropRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
  }

  @Test
  public void testRput() {
    // TODO(qwang): Should be finished.
  }

  @Test
  public void testRdel() {
    // TODO(qwang): Should be finished.
  }

  @Test(expectedExceptions = DstException.class)
  public void testInvalidDropCommand() {
    final String command = "list.drop k1";
    dstParser.parse(command);
  }

  @Test(expectedExceptions = DstException.class)
  public void testInvalidPutCommand() {
    final String command = "list.ldel k1";
    dstParser.parse(command);
  }

}
