package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.Status;
import com.distkv.core.DistkvMapInterface;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DistkvDictsImpl extends DistkvConcepts<Map<String, String>>
    implements DistkvDicts<Map<String, String>> {


  private static Logger LOGGER = LoggerFactory.getLogger(DistkvDictsImpl.class);

  public DistkvDictsImpl(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public void get(String key, Builder builder) {

    Map<String, String> dict = null;
    try {
      dict = get(key);
    } catch (KeyNotFoundException e) {
      LOGGER.info("Failed to get dict from store: {1}", e);
    }
    if (dict == null) {
      builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    } else {
      DictProtocol.DictGetResponse.Builder responseBuilder =
          DictProtocol.DictGetResponse.newBuilder();
      DictProtocol.DistKVDict.Builder dictBuilder = DictProtocol.DistKVDict.newBuilder();
      for (Map.Entry<String, String> entry : dict.entrySet()) {
        dictBuilder.addKeys(entry.getKey());
        dictBuilder.addValues(entry.getValue());
      }
      responseBuilder.setDict(dictBuilder);
      builder.setResponse(Any.pack(responseBuilder.build()));
    }
  }

  @Override
  public void put(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    DictProtocol.DictPutRequest dictPutRequest = requestBody
        .unpack(DictProtocol.DictPutRequest.class);
    try {
      final Map<String, String> map = new HashMap<>();
      DictProtocol.DistKVDict distKVDict = dictPutRequest.getDict();
      for (int i = 0; i < distKVDict.getKeysCount(); i++) {
        map.put(distKVDict.getKeys(i), distKVDict.getValues(i));
      }
      put(key, map);
      builder.setStatus(CommonProtocol.Status.OK);
    } catch (DistkvKeyDuplicatedException e) {
      builder.setStatus(CommonProtocol.Status.DUPLICATED_KEY);
    } catch (DistkvException e) {
      builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    }
  }

  @Override
  public void drop(String key, Builder builder) {
    builder.setStatus(CommonProtocol.Status.OK);
    Status status = drop(key);
    if (Status.KEY_NOT_FOUND == status) {
      builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    } else if (Status.OK != status) {
      builder.setStatus(CommonProtocol.Status.UNKNOWN_ERROR);
    }
  }

  @Override
  public void getItem(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    DictProtocol.DictGetItemRequest dictGetItemRequest = requestBody
        .unpack(DictProtocol.DictGetItemRequest.class);
    final Map<String, String> dict = get(key);
    builder.setStatus(CommonProtocol.Status.OK);
    if (dict == null) {
      builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    } else {
      final String itemValue = dict.get(dictGetItemRequest.getItemKey());
      if (itemValue == null) {
        builder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
      } else {
        DictProtocol.DictGetItemResponse.Builder dictBuilder =
            DictProtocol.DictGetItemResponse.newBuilder();
        dictBuilder.setItemValue(itemValue);
        builder.setResponse(Any.pack(dictBuilder.build()));
      }
    }
  }

  @Override
  public void popItem(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    DictProtocol.DictPopItemRequest dictPopItemRequest = requestBody
        .unpack(DictProtocol.DictPopItemRequest.class);
    builder.setStatus(CommonProtocol.Status.OK);
    final Map<String, String> dict = get(key);
    if (dict == null) {
      builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    } else {
      final String itemValue = dict.remove(dictPopItemRequest.getItemKey());
      if (itemValue == null) {
        builder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
      } else {
        DictProtocol.DictPopItemResponse.Builder dictBuilder =
            DictProtocol.DictPopItemResponse.newBuilder();
        dictBuilder.setItemValue(itemValue);
        builder.setResponse(Any.pack(dictBuilder.build()));
      }
    }
  }

  @Override
  public void putItem(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    DictProtocol.DictPutItemRequest dictPutItemRequest = requestBody
        .unpack(DictProtocol.DictPutItemRequest.class);
    builder.setStatus(CommonProtocol.Status.OK);
    final Map<String, String> dict = get(key);
    if (dict == null) {
      builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    } else {
      dict.put(dictPutItemRequest.getItemKey(), dictPutItemRequest.getItemValue());
    }
  }

  @Override
  public void removeItem(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    DictProtocol.DictRemoveItemRequest dictRemoveItemRequest = requestBody
        .unpack(DictProtocol.DictRemoveItemRequest.class);
    builder.setStatus(CommonProtocol.Status.OK);
    final Map<String, String> dict = get(key);
    if (dict == null) {
      builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    } else {
      final String itemValue = dict.remove(dictRemoveItemRequest.getItemKey());
      if (itemValue == null) {
        builder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
      }
      dict.remove(dictRemoveItemRequest.getItemKey());
    }
  }
}
