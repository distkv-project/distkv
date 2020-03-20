package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.Status;
import com.distkv.core.DistkvMapInterface;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.distkv.rpc.protobuf.generated.IntProtocol;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistkvIntsImpl extends DistkvConcepts<Integer> implements DistkvInts<Integer> {

  private static Logger LOGGER = LoggerFactory.getLogger(DistkvIntsImpl.class);

  public DistkvIntsImpl(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }


  @Override
  public void put(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    IntProtocol.IntPutRequest intPutRequest = requestBody
        .unpack(IntProtocol.IntPutRequest.class);
    try {
      put(key, intPutRequest.getValue());
    } catch (DistkvKeyDuplicatedException e) {
      builder.setStatus(CommonProtocol.Status.DUPLICATED_KEY);
    }
    builder.setStatus(CommonProtocol.Status.OK);
  }

  @Override
  public void get(String key, Builder builder) {

    try {
      int value = get(key);
      IntProtocol.IntGetResponse intBuilder = IntProtocol.IntGetResponse
          .newBuilder().setValue(value).build();
      builder.setStatus(CommonProtocol.Status.OK).setResponse(Any.pack(intBuilder));
    } catch (KeyNotFoundException e) {
      builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    }
  }

  @Override
  public void drop(String key, Builder builder) {

    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = drop(key);
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (DistkvException e) {
      LOGGER.error("Failed to drop a int to store :{1}", e);
    }
    builder.setStatus(status);
  }

  @Override
  public void incr(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    IntProtocol.IntIncrRequest intIncrRequest = requestBody
        .unpack(IntProtocol.IntIncrRequest.class);
    CommonProtocol.Status status;
    try {
      incr(key, intIncrRequest.getDelta());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DistkvException e) {
      LOGGER.error("Failed to incr a int value in ints store: {1}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    builder.setStatus(status);
  }

  public void incr(String key, int delta) {
    Integer oldValue = get(key);
    oldValue += delta;
    super.distkvKeyValueMap.put(key, oldValue);
  }
}
