package com.distkv.dst.client.commandlinetool;

import com.distkv.dst.client.DstClient;
import com.distkv.dst.parser.po.DstParsedResult;

public class DstCommandExecutor {

  DstClient dstClient;

  public DstCommandExecutor(DstClient dstClient) {
    this.dstClient = dstClient;
  }

  public String execute(DstParsedResult parsedResult) {
    switch (parsedResult.getRequestType()) {
      case STR_PUT:
        return CommandExecutorHandler.strPut(dstClient, parsedResult);
      case STR_GET:
        return CommandExecutorHandler.strGet(dstClient, parsedResult);
      case STR_DROP:
        return CommandExecutorHandler.strDrop(dstClient, parsedResult);
      case LIST_PUT:
        return CommandExecutorHandler.listPut(dstClient, parsedResult);
      case LIST_GET:
        return CommandExecutorHandler.listGet(dstClient, parsedResult);
      case LIST_LPUT:
        return CommandExecutorHandler.listLPut(dstClient, parsedResult);
      case LIST_RPUT:
        return CommandExecutorHandler.listRPut(dstClient, parsedResult);
      case LIST_REMOVE:
        return CommandExecutorHandler.listRemove(dstClient, parsedResult);
      case LIST_M_REMOVE:
        return CommandExecutorHandler.listMRemove(dstClient, parsedResult);
      case LIST_DROP:
        return CommandExecutorHandler.listDrop(dstClient, parsedResult);
      case SET_PUT:
        return CommandExecutorHandler.setPut(dstClient, parsedResult);
      case SET_GET:
        return CommandExecutorHandler.setGet(dstClient, parsedResult);
      case SET_DROP:
        return CommandExecutorHandler.setDrop(dstClient, parsedResult);
      case SET_PUT_ITEM:
        return CommandExecutorHandler.setPutItem(dstClient, parsedResult);
      case SET_REMOVE_ITEM:
        return CommandExecutorHandler.setRemoveItem(dstClient, parsedResult);
      case SET_EXIST:
        return CommandExecutorHandler.setExist(dstClient, parsedResult);
      case DICT_PUT:
        return CommandExecutorHandler.dictPut(dstClient, parsedResult);
      case DICT_GET:
        return CommandExecutorHandler.dictGet(dstClient, parsedResult);
      case DICT_PUT_ITEM:
        return CommandExecutorHandler.dictPutItem(dstClient, parsedResult);
      case DICT_GET_ITEM:
        return CommandExecutorHandler.dictGetItem(dstClient, parsedResult);
      case DICT_POP_ITEM:
        return CommandExecutorHandler.dictPopItem(dstClient, parsedResult);
      case DICT_REMOVE_ITEM:
        return CommandExecutorHandler.dictRemoveItem(dstClient, parsedResult);
      case DICT_DROP:
        return CommandExecutorHandler.dictDrop(dstClient, parsedResult);
      case SLIST_PUT:
        return CommandExecutorHandler.slistPut(dstClient, parsedResult);
      case SLIST_TOP:
        return CommandExecutorHandler.slistTop(dstClient, parsedResult);
      case SLIST_INCR_SCORE:
        return CommandExecutorHandler.slistIncrScore(dstClient, parsedResult);
      case SLIST_PUT_MEMBER:
        return CommandExecutorHandler.slistPutMember(dstClient, parsedResult);
      case SLIST_REMOVE_MEMBER:
        return CommandExecutorHandler.slistRemoveMember(dstClient, parsedResult);
      case SLIST_DROP:
        return CommandExecutorHandler.slistDrop(dstClient, parsedResult);
      case SLIST_GET_MEMBER:
        return CommandExecutorHandler.slistGetMember(dstClient, parsedResult);
      default:
        return null;
    }
  }
}
