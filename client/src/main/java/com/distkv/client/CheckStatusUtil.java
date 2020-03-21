package com.distkv.client;

import com.distkv.common.exception.DictKeyNotFoundException;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvListIndexOutOfBoundsException;
import com.distkv.common.exception.DistkvUnknownRequestException;
import com.distkv.common.exception.DistkvWrongRequestFormatException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.exception.MasterSyncToSlaveException;
import com.distkv.common.exception.SetItemNotFoundException;
import com.distkv.common.exception.SortedListMemberNotFoundException;
import com.distkv.common.exception.SortedListTopNumIsNonNegativeException;
import com.distkv.rpc.protobuf.generated.CommonProtocol;

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
        throw new DistkvListIndexOutOfBoundsException(key, typeCode);
      case SLIST_MEMBER_NOT_FOUND:
        throw new SortedListMemberNotFoundException(key, typeCode);
      case SLIST_TOPNUM_BE_POSITIVE:
        throw new SortedListTopNumIsNonNegativeException(key, typeCode);
      case SYNC_ERROR:
        throw new MasterSyncToSlaveException(key, typeCode);
      case WRONG_REQUEST_FORMAT:
        throw new DistkvWrongRequestFormatException(key, typeCode);
      case UNKNOWN_REQUEST_TYPE:
        throw new DistkvUnknownRequestException(key, typeCode);
      default:
        throw new DistkvException(typeCode + "000",
            String.format("Error status is %s", status.getClass().toString()));
    }
  }

  public static void checkStatus(
      CommonProtocol.Status status, String key, String itemName, String typeCode) {
    switch (status) {
      case OK:
        break;
      case SET_ITEM_NOT_FOUND:
        throw new SetItemNotFoundException(key, itemName, typeCode);
      default:
        throw new DistkvException(typeCode + "000",
            String.format("Error status is %s", status.getClass().toString()));
    }
  }

}
