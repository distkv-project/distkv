package com.distkv.parser;

import com.distkv.common.exception.DistkvException;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPopItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPutItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPutRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseDictCommandTest {

  private static final DistkvParser distkvParser = new DistkvParser();

  @Test
  public void testPut() throws InvalidProtocolBufferException {
    final String putDictCommand = "dict.put dict1 k1 v1 k2 v2 k3 v3";
    DistkvParsedResult result = distkvParser.parse(putDictCommand);
    Assert.assertEquals(result.getRequestType(), RequestType.DICT_PUT);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "dict1");
    final DictProtocol.DistKVDict DistKVDict = request.getRequest()
        .unpack(DictPutRequest.class).getDict();
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
    DistkvParsedResult result = distkvParser.parse(getDictCommand);
    Assert.assertEquals(result.getRequestType(), RequestType.DICT_GET);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "dict1");
  }

  @Test
  public void testPutItem() throws InvalidProtocolBufferException {
    final String command = "dict.putItem dict1 k1 v1";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.DICT_PUT_ITEM);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getRequest()
        .unpack(DictPutItemRequest.class).getItemValue(), "v1");
    Assert.assertEquals(request.getRequest()
        .unpack(DictPutItemRequest.class).getItemKey(), "k1");
  }

  @Test
  public void testGetItem() throws InvalidProtocolBufferException {
    // TODO(qwang): Should be finished.
    final String command = "dict.getItem dict1 v1";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.DICT_GET_ITEM);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getRequest()
        .unpack(DictGetItemRequest.class).getItemKey(), "v1");

  }

  @Test
  public void testPopItem() throws InvalidProtocolBufferException {
    // TODO(qwang): Should be finished.
    final String command = "dict.popItem k1 v1";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.DICT_POP_ITEM);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "k1");
    Assert.assertEquals(request.getRequest()
        .unpack(DictPopItemRequest.class).getItemKey(), "v1");
  }

  @Test
  public void testRemoveItem() {
    final String command = "dict.removeItem dict1 v1";
    DistkvParsedResult result = distkvParser.parse(command);
    Assert.assertEquals(result.getRequestType(), RequestType.DICT_REMOVE_ITEM);
    DistkvRequest request = result.getRequest();
    Assert.assertEquals(request.getKey(), "dict1");
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidCommand() {
    final String command = "dict.ldel k1";
    distkvParser.parse(command);
  }
}
