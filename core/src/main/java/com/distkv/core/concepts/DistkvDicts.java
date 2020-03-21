package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;

public interface DistkvDicts<T> extends DistkvBaseOperation<T> {

  void getItem(String key, Any requestBody, Builder builder) throws DistkvException;

  void popItem(String key, Any requestBody, Builder builder) throws DistkvException;

  void putItem(String key, Any requestBody, Builder builder) throws DistkvException;

  void removeItem(String key, Any requestBody, Builder builder) throws DistkvException;

}

