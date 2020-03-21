package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;

import java.util.List;

public interface DistkvLists extends DistkvBaseOperation<DistkvLists> {

  /**
   * This method will query a list value based on the key
   *
   * @param key Obtain a list value based on the key
   */
  void get(String key, Any request, Builder builder) throws DistkvException;

  String get(String key, int index) throws DistkvException;

  List<String> get(String key, int from, int end) throws DistkvException;

  //insert value from the left of list
  void lput(String key, Any request) throws DistkvException;

  void lput(String key, List<String> value) throws DistkvException;

  //insert value from the right of list
  void rput(String key, Any request) throws DistkvException;

  void rput(String key, List<String> value) throws DistkvException;

  void remove(String key, Any request) throws DistkvException;

  void remove(String key, int index) throws DistkvException;

  void remove(String key, int from, int to) throws DistkvException;

  void mremove(String key, Any request) throws DistkvException;

  /**
   * This method will remove multiple items from the list.
   *
   * @param key     The name of the list in store.
   * @param indexes A list of indexes for those items you wanna to remove.
   */
  void mremove(String key, List<Integer> indexes) throws DistkvException;
}
