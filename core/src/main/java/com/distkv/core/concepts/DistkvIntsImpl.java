package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvWrongRequestFormatException;
import com.distkv.core.DistkvMapInterface;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.distkv.rpc.protobuf.generated.IntProtocol.IntGetResponse;
import com.distkv.rpc.protobuf.generated.IntProtocol.IntIncrRequest;
import com.distkv.rpc.protobuf.generated.IntProtocol.IntPutRequest;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

public class DistkvIntsImpl extends DistkvConcepts<Integer> implements DistkvInts<Integer> {

  public DistkvIntsImpl(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public void put(String key, Any request)
      throws DistkvException {
    try {
      IntPutRequest intPutRequest = request.unpack(IntPutRequest.class);
      put(key, intPutRequest.getValue());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public void get(String key, Builder builder) throws DistkvException {
    int value = get(key);
    IntGetResponse intBuilder = IntGetResponse.newBuilder().setValue(value).build();
    builder.setResponse(Any.pack(intBuilder));
  }

  @Override
  public void incr(String key, Any request)
      throws DistkvException {
    try {
      IntIncrRequest intIncrRequest = request.unpack(IntIncrRequest.class);
      incr(key, intIncrRequest.getDelta());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  public void incr(String key, int delta) throws DistkvException {
    Integer oldValue = get(key);
    oldValue += delta;
    super.distkvKeyValueMap.put(key, oldValue);
  }
}
