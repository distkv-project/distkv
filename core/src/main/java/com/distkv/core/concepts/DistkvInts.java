package com.distkv.core.concepts;

import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

public interface DistkvInts<T> extends DistkvBaseOperation<T> {


  /**
   * This method will increase the int value
   *
   * @param key   increase a int value based on the key
   * @param delta the increased value
   */
  void incr(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

}
