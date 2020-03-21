package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;

import java.util.Map;

public interface DistkvDicts extends DistkvBaseOperation<Map<String, String>> {

  /**
   * Get item from the dict stored at Distkv store.
   *
   * @param key     the key related to dict.
   * @param request the request.
   * @param builder the builder to build the response with item value got.
   */
  void getItem(String key, Any request, Builder builder) throws DistkvException;

  /**
   * Pop the item from the dict stored  at Distkv store.
   * @param key the key related to dict.
   * @param request the request.
   * @param builder the builder to build the response with the item value popped.
   */
  void popItem(String key, Any request, Builder builder) throws DistkvException;

  /**
   * Put the item value to dict stored at Distkv store by key.
   * @param key the key related to dict.
   * @param request the request.
   */
  void putItem(String key, Any request) throws DistkvException;

  /**
   * Remove the item value from dict stored at Distkv store.
   * @param key the key related to dict.
   * @param request the request.
   */
  void removeItem(String key, Any request) throws DistkvException;

}

