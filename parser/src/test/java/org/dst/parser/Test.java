package org.dst.parser;

import org.dst.parser.po.DstParsedResult;
import org.dst.rpc.protobuf.generated.SetProtocol;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

import static org.dst.parser.util.CodeUtils.executeExpression;

public class Test {


  @org.junit.Test
  public void testCmdParse() {
    //put
    DstParser dstParser = new DstParser();
    String command = "set.put \"k1\" \"v1\" \"v2\" \"v3\"";
    DstParsedResult result = dstParser.parse(command);
    Assert.assertEquals(result.getRequest().getClass(), SetProtocol.PutRequest.class);
    Assert.assertEquals("\"k1\"", ((SetProtocol.PutRequest)result.getRequest()).getKey());
    Assert.assertEquals(3, ((SetProtocol.PutRequest)result.getRequest()).getValuesCount());

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


  @org.junit.Test
  public void testExecuteExpression() {
    Map<String, Object> map = new HashMap<>();
    map.put("alive", "coding every day");
    map.put("out", System.out);
    String expression = "out.print(alive)";
    executeExpression(expression, map);
  }

}
