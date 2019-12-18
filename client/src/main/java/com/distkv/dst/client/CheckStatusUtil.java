package com.distkv.dst.client;

import com.distkv.dst.common.exception.DictKeyNotFoundException;
import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;

public class CheckStatusUtil {
  // Used to check the status and throw the corresponding exception.
  public static void checkStatus(CommonProtocol.Status status, String key) {
    switch (status) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      case DICT_KEY_NOT_FOUND:
        throw new DictKeyNotFoundException(key);
      case LIST_INDEX_OUT_OF_BOUNDS:
        throw new IndexOutOfBoundsException(key);
      default:
        throw new DstException(String.format("Error code is %d", status.getNumber()));
    }
  }
}
