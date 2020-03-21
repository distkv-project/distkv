package com.distkv.core.concepts;


import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;

public interface DistkvBaseOperation<T> {

  void get(String key, Builder builder) throws DistkvException;

  T get(String key) throws DistkvException;

  void put(String key, Any requestBody, Builder builder) throws DistkvException;

  void put(String key, T t) throws DistkvException;

  void drop(String key) throws DistkvException;
}
