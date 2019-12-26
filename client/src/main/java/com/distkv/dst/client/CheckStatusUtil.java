package com.distkv.dst.client;

import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.exception.DictKeyNotFoundException;
import com.distkv.dst.common.exception.DstListIndexOutOfBoundsException;
import com.distkv.dst.common.exception.SortedListMemberNotFoundException;
import com.distkv.dst.common.exception.SortedListTopNumBeNonNegativeException;
import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;

public class CheckStatusUtil {

  // Used to check the status and throw the corresponding exception.
  public static void checkStatus(CommonProtocol.Status status, String key, String typeCode) {
    switch (status) {
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key, typeCode);
      case DICT_KEY_NOT_FOUND:
        throw new DictKeyNotFoundException(key, typeCode);
      case LIST_INDEX_OUT_OF_BOUNDS:
        throw new DstListIndexOutOfBoundsException(key, typeCode);
      case SLIST_MEMBER_NOT_FOUND:
        throw new SortedListMemberNotFoundException(key, typeCode);
      case SLIST_TOPNUM_BE_POSITIVE:
        throw new SortedListTopNumBeNonNegativeException(key, typeCode);
      default:
        throw new DstException(typeCode + "000",
              String.format("Error status is %s", status.getClass().toString()));
    }
  }
}
