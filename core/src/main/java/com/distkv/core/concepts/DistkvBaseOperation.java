package com.distkv.core.concepts;


import com.distkv.common.utils.Status;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

public interface DistkvBaseOperation<T> {

  void get(String key, Builder builder);

  T get(String key);

  void put(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

  void put(String key, T t);

  void drop(String key, Builder builder);

  Status drop(String key);

}
