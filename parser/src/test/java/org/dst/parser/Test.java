package org.dst.parser;

import org.dst.parser.po.DstParsedResult;
import org.dst.rpc.protobuf.generated.SetProtocol;
import org.junit.Assert;

public class Test {


  @org.junit.Test
  public void testCmdParse() {
    //put
    DstParser dstParser = new DstParser();
    String command = "set.put \"k1\" \"v1\" \"v2\" \"v3\" \"v4\"";
    DstParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequest().getClass(), SetProtocol.PutRequest.class);
    Assert.assertEquals("\"k1\"", ((SetProtocol.PutRequest)result.getRequest()).getKey());
    Assert.assertEquals(4, ((SetProtocol.PutRequest)result.getRequest()).getValuesCount());

    //get
    command = "set.get \"k1\"";
    result = dstParser.parse(command);
    Assert.assertEquals(result.getRequest().getClass(), SetProtocol.GetRequest.class);
    //error grammar
    try {
      command = "set1.get \"k1\"";
      result = dstParser.parse(command);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

}
