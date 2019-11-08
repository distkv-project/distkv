package org.dst.test.parser;

import org.dst.common.exception.DstException;
import org.dst.parser.DstParser;
import org.dst.parser.po.DstParsedResult;
import org.dst.parser.po.RequestTypeEnum;
import org.dst.rpc.protobuf.generated.ListProtocol;
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
    Assert.assertEquals(request.getValueCount(), 2);
    Assert.assertEquals(request.getValue(0), "v1");
    Assert.assertEquals(request.getValue(1), "v2");
  }

  @Test
  public void testGet() {
    final String command = "list.get k1";
    DstParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.LIST_GET);
    ListProtocol.GetRequest request = (ListProtocol.GetRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
  }

  @Test
  public void testLput() {
    final String command = "list.lput k1 v1 v2";
    DstParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.LIST_LPUT);
    ListProtocol.LPutRequest request = (ListProtocol.LPutRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getValueCount(), 2);
    Assert.assertEquals(request.getValue(0), "v1");
    Assert.assertEquals(request.getValue(1), "v2");
  }

  @Test
  public void testRput() {
    // TODO(qwang): Should be finished.
  }

  @Test
  public void testLdel() {
    final String command = "list.ldel k1 100";
    DstParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.LIST_LDEL);
    ListProtocol.LDelRequest request = (ListProtocol.LDelRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getValue(), 100);
  }

  @Test
  public void testRdel() {
    // TODO(qwang): Should be finished.
  }

  @Test(expectedExceptions = DstException.class)
  public void testInvalidPutCommand() {
    final String command = "list.ldel k1";
    dstParser.parse(command);
  }

}
