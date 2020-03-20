package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.exception.SetItemNotFoundException;
import com.distkv.common.utils.Status;
import com.distkv.core.DistkvMapInterface;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.distkv.rpc.protobuf.generated.SetProtocol;
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
      throws InvalidProtocolBufferException {

    SetProtocol.SetPutItemRequest setPutItemRequest = itemValue
        .unpack(SetProtocol.SetPutItemRequest.class);
    CommonProtocol.Status status;
    try {
      putItem(key, setPutItemRequest.getItemValue());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    }
    builder.setStatus(status);
  }

  private void putItem(String key, String itemValue) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    get(key).add(itemValue);
  }

  @Override
  public void removeItem(String key, Any itemValue, Builder builder)
      throws InvalidProtocolBufferException {
    SetProtocol.SetRemoveItemRequest setRemoveItemRequest = itemValue
        .unpack(SetProtocol.SetRemoveItemRequest.class);
    CommonProtocol.Status status = null;
    try {
      Status localStatus = removeItem(key, setRemoveItemRequest.getItemValue());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (SetItemNotFoundException e) {
      status = CommonProtocol.Status.SET_ITEM_NOT_FOUND;
    } catch (DistkvException e) {
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    builder.setStatus(status);
  }

  @Override
  public Status removeItem(String key, String itemValue) {
    if (!distkvKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    if (!get(key).contains(itemValue)) {
      throw new SetItemNotFoundException(key, itemValue);
    }
    get(key).remove(itemValue);
    return Status.OK;
  }

  @Override
  public void exists(String key, Any value, Builder builder)
      throws InvalidProtocolBufferException {
    SetProtocol.SetExistsRequest setExistsRequest = value
        .unpack(SetProtocol.SetExistsRequest.class);
    try {
      boolean result = exists(key, setExistsRequest.getEntity());
      SetProtocol.SetExistsResponse.Builder setBuilder = SetProtocol.SetExistsResponse
          .newBuilder();
      setBuilder.setResult(result);
      builder.setResponse(Any.pack(setBuilder.build())).setStatus(CommonProtocol.Status.OK);
    } catch (KeyNotFoundException e) {
      builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    } catch (DistkvException e) {
      builder.setStatus(CommonProtocol.Status.UNKNOWN_ERROR);
    }
  }

  @Override
  public boolean exists(String key, String value) throws KeyNotFoundException {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    return get(key).contains(value);
  }

  @Override
  public void get(String key, Builder builder) {
    try {
      Set<String> values = get(key);
      SetProtocol.SetGetResponse.Builder setBuilder = SetProtocol.SetGetResponse
          .newBuilder();
      values.forEach(setBuilder::addValues);
      builder.setStatus(CommonProtocol.Status.OK).setResponse(Any.pack(setBuilder.build()));
    } catch (KeyNotFoundException e) {
      builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    } catch (
        DistkvException e) {
      builder.setStatus(CommonProtocol.Status.UNKNOWN_ERROR);
    }
  }

  @Override
  public void put(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {
    SetProtocol.SetPutRequest setPutRequest = requestBody
        .unpack(SetProtocol.SetPutRequest.class);
    // TODO(qwang): Any thoughts on how to avoid this `new HasSet`.
    try {
      put(key, new HashSet<>(setPutRequest.getValuesList()));
    } catch (DistkvKeyDuplicatedException e) {
      builder.setStatus(CommonProtocol.Status.DUPLICATED_KEY);
    }
    builder.setStatus(CommonProtocol.Status.OK);
  }

  @Override
  public void drop(String key, Builder builder) {
    CommonProtocol.Status status = null;
    try {
      Status localStatus = drop(key);
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (DistkvException e) {
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    builder.setStatus(status);
  }
}
