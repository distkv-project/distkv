package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;

public interface DistkvDicts<T> extends DistkvBaseOperation<T> {

  /**
   * Get item from the dict stored at distkv store.
   * @param key the key related to dict.
   * @param request the request.
   * @param builder the builder to builde the response with item value got.
   */
  void getItem(String key, Any request, Builder builder) throws DistkvException;

  /**
   * Pop the item from the dict stored  at distkv store.
   * @param key the key related to dict.
   * @param request the request.
   * @param builder the builder to build the response with the item value popped.
   */
  void popItem(String key, Any request, Builder builder) throws DistkvException;

  /**
   * Put the item value to dict stored at distkv store by key.
   * @param key the key related to dict.
   * @param request the request.
   */
  void putItem(String key, Any request) throws DistkvException;

  /**
   * Remove the item value from dict stored at distkv store.
   * @param key the key related to dict.
   * @param request the request.
   */
  void removeItem(String key, Any request) throws DistkvException;

}

