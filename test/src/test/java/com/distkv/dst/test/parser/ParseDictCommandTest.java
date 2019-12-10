package com.distkv.dst.test.parser;

import com.distkv.dst.parser.DstParser;
import com.distkv.dst.parser.po.DstParsedResult;
import com.distkv.dst.common.RequestTypeEnum;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;
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

  @Test
  public void testGet() {
    final String getDictCommand = "dict.get dict1";
    DstParsedResult result = dstParser.parse(getDictCommand);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.DICT_GET);
    DictProtocol.GetRequest request = (DictProtocol.GetRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "dict1");
  }

  @Test
  public void testPutItem() {
    // TODO(qwang): Should be finished.
  }

  @Test
  public void testGetItem() {
    // TODO(qwang): Should be finished.
  }

  @Test
  public void testPopItem() {
    // TODO(qwang): Should be finished.
  }

  @Test
  public void testRemoveItem() {
    // TODO(qwang): Should be finished.
  }

  @Test
  public void testDrop() {
    // TODO(qwang): Should be finished.
  }

  @Test
  public void testInvalidCommand() {
    // TODO(qwang): Should be finished.
  }
}
