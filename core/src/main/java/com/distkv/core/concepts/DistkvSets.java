package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;

public interface DistkvSets extends DistkvBaseOperation<DistkvSets> {

  /**
   * Put item to the set.
   *
   * @param key     The key that represents the name of the set.
   * @param request The value of the item which will be putted into the set.
   */
  void putItem(String key, Any request) throws DistkvException;

  void removeItem(String key, Any request) throws DistkvException;

  /**
   * This method will delete a value according to the key.
   *
   * @param key       The key existing in set.
   * @param itemValue The item value will be deleted.
   */
  void removeItem(String key, String itemValue) throws DistkvException;

  void exists(String key, Any request, Builder builder) throws DistkvException;

  /**
   * This method will judge that if the value exists in map or not.
   *
   * @param key   the key exists in map
   * @param value the set value you want to judge
   * @return indicates that the value exists or not.
   * @throws DistkvException If the key don't exist in map
   */
  boolean exists(String key, String value) throws DistkvException;
}
