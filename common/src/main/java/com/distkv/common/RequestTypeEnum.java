package com.distkv.common;

public enum RequestTypeEnum {
  // basic operations

  // TODO(qwang): Remove this and use the enum in `common_pb`.
  EXIT,

  STR_PUT,
  STR_GET,
  STR_DROP,

  LIST_PUT,
  LIST_GET,
  LIST_LPUT,
  LIST_RPUT,
  LIST_REMOVE,
  LIST_M_REMOVE,
  LIST_DROP,

  SET_PUT,
  SET_GET,
  SET_DROP,
  SET_PUT_ITEM,
  SET_REMOVE_ITEM,
  SET_EXIST,

  DICT_PUT,
  DICT_GET,
  DICT_PUT_ITEM,
  DICT_GET_ITEM,
  DICT_POP_ITEM,
  DICT_REMOVE_ITEM,
  DICT_DROP,

  SLIST_PUT,
  SLIST_TOP,
  SLIST_INCR_SCORE,
  SLIST_PUT_MEMBER,
  SLIST_REMOVE_MEMBER,
  SLIST_DROP,
  SLIST_GET_MEMBER,
}
