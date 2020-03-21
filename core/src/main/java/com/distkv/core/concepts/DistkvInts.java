package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvException;
import com.google.protobuf.Any;

public interface DistkvInts extends DistkvBaseOperation<DistkvInts> {


  /**
   * This method will increase the int value
   *
   * @param key increase a int value based on the key
   * @param request the request.
   */
  void incr(String key, Any request) throws DistkvException;

}
