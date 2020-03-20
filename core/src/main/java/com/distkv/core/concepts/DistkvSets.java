package com.distkv.core.concepts;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.Status;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

public interface DistkvSets<T> extends DistkvBaseOperation<T> {

  /**
   * Put item to the set.
   *
   * @param key       The key that represents the name of the set.
   * @param itemValue The value of the item which will be putted into the set.
   */
  void putItem(String key, Any itemValue, Builder builder)
      throws InvalidProtocolBufferException;

  /**
   * This method will delete a value according to the key.
   *
   * @param key       The key existing in set.
   * @param itemValue The item value will be deleted.
   * @return Status Indicates that the deletion succeeded or failed.
   */
  void removeItem(String key, Any itemValue, Builder builder)
      throws InvalidProtocolBufferException;

  Status removeItem(String key, String itemValue);

  /**
   * This method will judge that if the value exists in map or not.
   *
   * @param key   the key exists in map
   * @param value the set value you want to judge
   * @return Status indicates that the value exists or not.
   * @throws KeyNotFoundException If the key don't exist in map
   */
  void exists(String key, Any value, Builder builder)
      throws InvalidProtocolBufferException;

  boolean exists(String key, String value) throws KeyNotFoundException;
}
