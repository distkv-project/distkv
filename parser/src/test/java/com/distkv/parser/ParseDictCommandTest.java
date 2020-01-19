package com.distkv.parser;

import com.distkv.common.exception.DistKVException;
import com.distkv.parser.po.DistKVParsedResult;
import com.distkv.common.RequestTypeEnum;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
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
    final String command = "dict.putItem dict1 k1 v1";
    DistKVParsedResult result = distKVParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.DICT_PUT_ITEM);
    DictProtocol.PutItemRequest request = (DictProtocol.PutItemRequest) result.getRequest();
    Assert.assertEquals(request.getItemValue(), "v1");
    Assert.assertEquals(request.getItemKey(), "k1");
  }

  @Test
  public void testGetItem() {
    final String command = "dict.getItem dict1 v1";
    DistKVParsedResult result = distKVParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.DICT_GET_ITEM);
    DictProtocol.GetItemRequest request = (DictProtocol.GetItemRequest) result.getRequest();
    Assert.assertEquals(request.getItemKey(), "v1");

  }

  @Test
  public void testPopItem() {
    final String command = "dict.popItem k1 v1";
    DistKVParsedResult result = distKVParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.DICT_POP_ITEM);
    DictProtocol.PopItemRequest request = (DictProtocol.PopItemRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getItemKey(), "v1");
  }

  @Test
  public void testRemoveItem() {
    final String command = "dict.removeItem dict1 v1";
    DistKVParsedResult result = distKVParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.DICT_REMOVE_ITEM);
    DictProtocol.RemoveItemRequest request = (DictProtocol.RemoveItemRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "dict1");
  }

  @Test
  public void testDrop() {
    final String command = "dict.drop dict1";
    DistKVParsedResult result = distKVParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.DICT_DROP);
    CommonProtocol.DropRequest request = (CommonProtocol.DropRequest) result.getRequest();
    Assert.assertEquals(request.getKey(), "dict1");
  }

  @Test(expectedExceptions = DistKVException.class)
  public void testInvalidCommand() {
    final String command = "dict.ldel k1";
    distKVParser.parse(command);
  }
}
