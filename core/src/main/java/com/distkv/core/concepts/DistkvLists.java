package com.distkv.core.concepts;

import com.distkv.common.utils.Status;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;

public interface DistkvLists<T> extends DistkvBaseOperation<T> {

  /**
   * This method will query a list value based on the key
   *
   * @param key Obtain a list value based on the key
   * @return the list value.
   */
  void get(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

  String get(String key, int index);

  List<String> get(String key, int from, int end);

  //insert value from the left of list
  void lput(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

  Status lput(String key, List<String> value);

  //insert value from the right of list
  void rput(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

  Status rput(String key, List<String> value);

  void remove(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

  Status remove(String key, int index);

  Status remove(String key, int from, int to);

  /**
   * This method will remove multiple items from the list.
   *
   * @param key         The name of the list in store.
   * @param requestBody A list of indexes for those items you wanna to remove.
   * @return Whether we succeed to remove the items.
   */
  void mremove(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;


  Status mremove(String key, List<Integer> indexes);
}
