package org.dst.test.parser;

import org.dst.parser.DstParser;
import org.dst.parser.po.DstParsedResult;
import org.dst.parser.po.RequestTypeEnum;
import org.dst.rpc.protobuf.generated.DictProtocol;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseDictCommandTest {

  private static final DstParser dstParser = new DstParser();

  @Test
  public void testPut() {
    final String putDictCommand = "dict.put dict1 k1 v1 k2 v2 k3 v3";
    DstParsedResult result = dstParser.parse(putDictCommand);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.DICT_PUT);
    DictProtocol.PutRequest request = (DictProtocol.PutRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "dict1");
    final DictProtocol.DstDict dstDict = request.getDict();
    Assert.assertEquals(dstDict.getKeysCount(), 3);
    Assert.assertEquals(dstDict.getKeys(0), "k1");
    Assert.assertEquals(dstDict.getKeys(1), "k2");
    Assert.assertEquals(dstDict.getKeys(2), "k3");

    Assert.assertEquals(dstDict.getValuesCount(), 3);
    Assert.assertEquals(dstDict.getValues(0), "v1");
    Assert.assertEquals(dstDict.getValues(1), "v2");
    Assert.assertEquals(dstDict.getValues(2), "v3");
  }

}
