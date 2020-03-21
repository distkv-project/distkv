package com.distkv.core.concepts;

import com.distkv.common.exception.DictKeyNotFoundException;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvWrongRequestFormatException;
import com.distkv.core.DistkvMapInterface;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetItemResponse;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetResponse;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPopItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPopItemResponse;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPutItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPutRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictRemoveItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DistKVDict;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.HashMap;
import java.util.Map;

public class DistkvDictsImpl extends DistkvConcepts<Map<String, String>>
    implements DistkvDicts<Map<String, String>> {

  public DistkvDictsImpl(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public void get(String key, Builder builder) throws DistkvException {
    Map<String, String> dict = get(key);
    DictGetResponse.Builder responseBuilder = DictGetResponse.newBuilder();
    DistKVDict.Builder dictBuilder = DistKVDict.newBuilder();
    for (Map.Entry<String, String> entry : dict.entrySet()) {
      dictBuilder.addKeys(entry.getKey());
      dictBuilder.addValues(entry.getValue());
    }
    responseBuilder.setDict(dictBuilder);
    builder.setResponse(Any.pack(responseBuilder.build()));
  }

  @Override
  public void put(String key, Any requestBody, Builder builder) throws DistkvException {

    try {
      DictPutRequest dictPutRequest = requestBody.unpack(DictPutRequest.class);
      DistKVDict distKVDict = dictPutRequest.getDict();

      final Map<String, String> map = new HashMap<>();
      for (int i = 0; i < distKVDict.getKeysCount(); i++) {
        map.put(distKVDict.getKeys(i), distKVDict.getValues(i));
      }
      put(key, map);
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public void getItem(String key, Any requestBody, Builder builder) throws DistkvException {

    try {
      DictGetItemRequest dictGetItemRequest = requestBody
          .unpack(DictGetItemRequest.class);
      final Map<String, String> dict = get(key);
      final String itemValue = dict.get(dictGetItemRequest.getItemKey());

      if (itemValue == null) {
        throw new DictKeyNotFoundException(key + "." + dictGetItemRequest.getItemKey());
      } else {
        DictGetItemResponse.Builder dictBuilder = DictGetItemResponse.newBuilder();
        dictBuilder.setItemValue(itemValue);
        builder.setResponse(Any.pack(dictBuilder.build()));
      }
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public void popItem(String key, Any requestBody, Builder builder) throws DistkvException {

    try {
      DictPopItemRequest dictPopItemRequest = requestBody.unpack(DictPopItemRequest.class);
      final Map<String, String> dict = get(key);
      final String itemValue = dict.remove(dictPopItemRequest.getItemKey());
      if (itemValue == null) {
        throw new DictKeyNotFoundException(key + "." + dictPopItemRequest.getItemKey());
      } else {
        DictPopItemResponse.Builder dictBuilder = DictPopItemResponse.newBuilder();
        dictBuilder.setItemValue(itemValue);
        builder.setResponse(Any.pack(dictBuilder.build()));
      }
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public void putItem(String key, Any requestBody, Builder builder) throws DistkvException {

    try {
      DictPutItemRequest dictPutItemRequest = requestBody.unpack(DictPutItemRequest.class);
      final Map<String, String> dict = get(key);
      dict.put(dictPutItemRequest.getItemKey(), dictPutItemRequest.getItemValue());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public void removeItem(String key, Any requestBody, Builder builder) throws DistkvException {

    try {
      DictRemoveItemRequest dictRemoveItemRequest = requestBody.unpack(DictRemoveItemRequest.class);
      final Map<String, String> dict = get(key);
      final String itemValue = dict.remove(dictRemoveItemRequest.getItemKey());
      if (itemValue == null) {
        throw new DictKeyNotFoundException(key + "." + dictRemoveItemRequest.getItemKey());
      }
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }
}
