package com.distkv.core.concepts;

import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

public interface DistkvDicts<T> extends DistkvBaseOperation<T> {
  void getItem(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

  void popItem(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

  void putItem(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

  void removeItem(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

}

