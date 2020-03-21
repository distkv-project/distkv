package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvWrongRequestFormatException;
import com.distkv.common.exception.SetItemNotFoundException;
import com.distkv.core.DistkvMapInterface;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetExistsRequest;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetExistsResponse;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetGetResponse;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetPutItemRequest;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetPutRequest;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetRemoveItemRequest;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.HashSet;
import java.util.Set;

public class DistkvSetsImpl extends DistkvConcepts<Set<String>> implements DistkvSets<Set<String>> {

  public DistkvSetsImpl(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public void putItem(String key, Any itemValue, Builder builder)
      throws DistkvException {
    try {
      SetPutItemRequest setPutItemRequest = itemValue.unpack(SetPutItemRequest.class);
      putItem(key, setPutItemRequest.getItemValue());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  private void putItem(String key, String itemValue) throws DistkvException {
    get(key).add(itemValue);
  }

  @Override
  public void removeItem(String key, Any itemValue, Builder builder)
      throws DistkvException {
    try {
      SetRemoveItemRequest setRemoveItemRequest = itemValue.unpack(SetRemoveItemRequest.class);
      removeItem(key, setRemoveItemRequest.getItemValue());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public void removeItem(String key, String itemValue) throws DistkvException {
    if (!get(key).contains(itemValue)) {
      throw new SetItemNotFoundException(key, itemValue);
    }
    get(key).remove(itemValue);
  }

  @Override
  public void exists(String key, Any value, Builder builder) throws DistkvException {
    try {
      SetExistsRequest setExistsRequest = value.unpack(SetExistsRequest.class);
      boolean result = exists(key, setExistsRequest.getEntity());
      SetExistsResponse.Builder setBuilder = SetExistsResponse.newBuilder();
      setBuilder.setResult(result);
      builder.setResponse(Any.pack(setBuilder.build()));
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public boolean exists(String key, String value) throws DistkvException {
    return get(key).contains(value);
  }

  @Override
  public void get(String key, Builder builder) throws DistkvException {
    Set<String> values = get(key);
    SetGetResponse.Builder setBuilder = SetGetResponse.newBuilder();
    values.forEach(setBuilder::addValues);
    builder.setResponse(Any.pack(setBuilder.build()));
  }

  @Override
  public void put(String key, Any requestBody, Builder builder) throws DistkvException {
    try {
      SetPutRequest setPutRequest = requestBody.unpack(SetProtocol.SetPutRequest.class);
      // TODO(qwang): Any thoughts on how to avoid this `new HasSet`.
      put(key, new HashSet<>(setPutRequest.getValuesList()));
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }
}
