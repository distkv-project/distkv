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
    String cmd = "set.put \"k1\" \"v1\" \"v2\" \"v3\"";
    DstParser dstParser = new DstParser();
    DstParsedResult parse = dstParser.parse(cmd);
    Assert.assertEquals(parse.getRequest().getClass(), SetProtocol.PutRequest.class);
    //get
    cmd = "set.get \"k1\"";
    parse = dstParser.parse(cmd);
    Assert.assertEquals(parse.getRequest().getClass(), SetProtocol.GetRequest.class);
    //error grammar
    try {
      cmd = "set1.get \"k1\"";
      parse = dstParser.parse(cmd);
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
