package com.distkv.client.commandlinetool;

import com.distkv.client.DistkvClient;
import com.distkv.parser.po.DistkvParsedResult;

public class DistkvCommandExecutor {

  DistkvClient distkvClient;

  public DistkvCommandExecutor(DistkvClient distkvClient) {
    this.distkvClient = distkvClient;
  }

  public String execute(DistkvParsedResult parsedResult) {
    switch (parsedResult.getRequestType()) {
      case STR_PUT:
        return CommandExecutorHandler.strPut(distkvClient, parsedResult);
      case STR_GET:
        return CommandExecutorHandler.strGet(distkvClient, parsedResult);
      case LIST_PUT:
        return CommandExecutorHandler.listPut(distkvClient, parsedResult);
      case LIST_GET:
        return CommandExecutorHandler.listGet(distkvClient, parsedResult);
      case LIST_LPUT:
        return CommandExecutorHandler.listLPut(distkvClient, parsedResult);
      case LIST_RPUT:
        return CommandExecutorHandler.listRPut(distkvClient, parsedResult);
      case LIST_REMOVE:
        return CommandExecutorHandler.listRemove(distkvClient, parsedResult);
      case LIST_MREMOVE:
        return CommandExecutorHandler.listMRemove(distkvClient, parsedResult);
      case SET_PUT:
        return CommandExecutorHandler.setPut(distkvClient, parsedResult);
      case SET_GET:
        return CommandExecutorHandler.setGet(distkvClient, parsedResult);
      case SET_PUT_ITEM:
        return CommandExecutorHandler.setPutItem(distkvClient, parsedResult);
      case SET_REMOVE_ITEM:
        return CommandExecutorHandler.setRemoveItem(distkvClient, parsedResult);
      case SET_EXISTS:
        return CommandExecutorHandler.setExists(distkvClient, parsedResult);
      case DICT_PUT:
        return CommandExecutorHandler.dictPut(distkvClient, parsedResult);
      case DICT_GET:
        return CommandExecutorHandler.dictGet(distkvClient, parsedResult);
      case DICT_PUT_ITEM:
        return CommandExecutorHandler.dictPutItem(distkvClient, parsedResult);
      case DICT_GET_ITEM:
        return CommandExecutorHandler.dictGetItem(distkvClient, parsedResult);
      case DICT_POP_ITEM:
        return CommandExecutorHandler.dictPopItem(distkvClient, parsedResult);
      case DICT_REMOVE_ITEM:
        return CommandExecutorHandler.dictRemoveItem(distkvClient, parsedResult);
      case SLIST_PUT:
        return CommandExecutorHandler.slistPut(distkvClient, parsedResult);
      case SLIST_TOP:
        return CommandExecutorHandler.slistTop(distkvClient, parsedResult);
      case SLIST_INCR_SCORE:
        return CommandExecutorHandler.slistIncrScore(distkvClient, parsedResult);
      case SLIST_PUT_MEMBER:
        return CommandExecutorHandler.slistPutMember(distkvClient, parsedResult);
      case SLIST_REMOVE_MEMBER:
        return CommandExecutorHandler.slistRemoveMember(distkvClient, parsedResult);
      case SLIST_GET_MEMBER:
        return CommandExecutorHandler.slistGetMember(distkvClient, parsedResult);
      case ACTIVE_NAMESPACE:
        return CommandExecutorHandler.activeNamespace(distkvClient, parsedResult);
      case DEACTIVE_NAMESPACE:
        return CommandExecutorHandler.deactiveNamespace(distkvClient, parsedResult);
      case INT_PUT:
        return CommandExecutorHandler.intPut(distkvClient, parsedResult);
      case INT_GET:
        return CommandExecutorHandler.intGet(distkvClient, parsedResult);
      case INT_INCR:
        return CommandExecutorHandler.intIncr(distkvClient, parsedResult);
      case DROP:
        return CommandExecutorHandler.drop(distkvClient, parsedResult);
      case EXPIRE:
        return CommandExecutorHandler.expire(distkvClient, parsedResult);
      case EXISTS:
        return CommandExecutorHandler.exists(distkvClient, parsedResult);
      case TTL:
        return CommandExecutorHandler.ttl(distkvClient, parsedResult);
      case EXIT:
        // User inputs `exit`, let's exit client immediately.
        System.out.println("bye bye ~");
        System.exit(0);
        return null;
      default:
        return null;
    }
  }
}
