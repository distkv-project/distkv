package com.distkv.parser;

import com.distkv.parser.po.DistKVParsedResult;
import com.distkv.common.RequestTypeEnum;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseDictCommandTest {

  private static final DistKVParser distKVParser = new DistKVParser();

  @Test
  public void testPut() {
    final String putDictCommand = "dict.put dict1 k1 v1 k2 v2 k3 v3";
    DistKVParsedResult result = distKVParser.parse(putDictCommand);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.DICT_PUT);
    DictProtocol.PutRequest request = (DictProtocol.PutRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "dict1");
    final DictProtocol.DistKVDict DistKVDict = request.getDict();
    Assert.assertEquals(DistKVDict.getKeysCount(), 3);
    Assert.assertEquals(DistKVDict.getKeys(0), "k1");
    Assert.assertEquals(DistKVDict.getKeys(1), "k2");
    Assert.assertEquals(DistKVDict.getKeys(2), "k3");

    Assert.assertEquals(DistKVDict.getValuesCount(), 3);
    Assert.assertEquals(DistKVDict.getValues(0), "v1");
    Assert.assertEquals(DistKVDict.getValues(1), "v2");
    Assert.assertEquals(DistKVDict.getValues(2), "v3");
  }

  @Test
  public void testGet() {
    final String getDictCommand = "dict.get dict1";
    DistKVParsedResult result = distKVParser.parse(getDictCommand);
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
