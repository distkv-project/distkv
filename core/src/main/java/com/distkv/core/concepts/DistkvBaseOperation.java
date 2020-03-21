package com.distkv.core.concepts;


import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;

public interface DistkvBaseOperation<T> {

  /**
   * Get the value by given key.
   * @param key the key to get related value.
   * @param builder the builder which builds response with the value.
   */
  void get(String key, Builder builder) throws DistkvException;

  /**
   * Get the value by given key.
   * @param key the key to get related value.
   * @return the value
   */
  T get(String key) throws DistkvException;

  /**
   * Put the value to Distkv store with given key.
   * @param key the key related to the value.
   * @param request the request
   */
  void put(String key, Any request) throws DistkvException;

  /**
   * Put the value to Distkv store with given key.
   * @param key the key related to the value.
   * @param t the value to put.
   */
  void put(String key, T t) throws DistkvException;

  /**
   * Drop the related entry from Distkv store by given key.
   * @param key the key related the entry to drop.
   */
  void drop(String key) throws DistkvException;
}
