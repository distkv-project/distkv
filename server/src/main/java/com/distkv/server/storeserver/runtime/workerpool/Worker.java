package com.distkv.server.storeserver.runtime.workerpool;

import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.EXPIRE;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.TTL;
import com.distkv.common.DistkvTuple;
import com.distkv.common.entity.sortedList.SlistEntity;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.DistkvListIndexOutOfBoundsException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.exception.SetItemNotFoundException;
import com.distkv.common.exception.SlistMemberNotFoundException;
import com.distkv.common.exception.SlistTopNumIsNonNegativeException;
import com.distkv.common.utils.Status;
import com.distkv.core.KVStore;
import com.distkv.core.struct.slist.Slist;
import com.distkv.core.struct.slist.SlistLinkedImpl;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.CommonProtocol.ExistsResponse;
import com.distkv.rpc.protobuf.generated.CommonProtocol.TTLResponse;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.IntProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import com.distkv.rpc.protobuf.generated.SlistProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.server.storeserver.runtime.StoreRuntime;
import com.distkv.server.storeserver.runtime.slave.SlaveClient;
import com.google.common.base.Preconditions;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker extends Thread {

  private static Logger LOG = LoggerFactory.getLogger(Worker.class);

  private StoreRuntime storeRuntime;

  private BlockingQueue<InternalRequest> queue;

  /**
   * Store engine.
   */
  private KVStore storeEngine;

  public Worker(StoreRuntime storeRuntime) {
    this.storeRuntime = storeRuntime;
    storeEngine = storeRuntime.getStoreEngine();
    queue = new LinkedBlockingQueue<>();
  }


  // Note that this method is threading-safe because of the threading-safe blocking queue.
  public void post(InternalRequest internalRequest) throws InterruptedException {
    queue.put(internalRequest);
  }


  @Override
  public void run() {
    while (true) {
      try {
        InternalRequest internalRequest = queue.take();
        DistkvRequest distkvRequest = internalRequest.getRequest();
        CompletableFuture<DistkvResponse> future = internalRequest.getCompletableFuture();
        DistkvResponse.Builder builder = DistkvResponse.newBuilder();

        handleExpiration(distkvRequest, builder);
        syncToSlaves(distkvRequest, future);
        storeHandler(distkvRequest, builder);

        future.complete(builder.setRequestType(distkvRequest.getRequestType()).build());
      } catch (Throwable e) {
        LOG.error("Failed to execute event loop:" + e);
        // TODO(tuowang): Clean up some resource associated with StoreRuntime
        storeRuntime.shutdown();
        Runtime.getRuntime().exit(-1);
      }
    }
  }

  // Add expire request to ExpireCycle.
  private void handleExpiration(DistkvRequest request, DistkvResponse.Builder builder) {
    if (needExpire(request)) {
      storeRuntime.getExpirationManager().addToCycle(request);
    }
    if (isTTLRequest(request)) {
      String key = request.getKey();
      long timeToLive = storeRuntime.getExpirationManager().getTheTimeToLive(key);
      if (timeToLive == -1) {
        if (!existsInStore(key)) {
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
          return;
        }
      }
      TTLResponse response = TTLResponse.newBuilder().setTtl(timeToLive).build();
      builder.setStatus(CommonProtocol.Status.OK).setResponse(Any.pack(response));
    }
  }

  private void syncToSlaves(DistkvRequest request, CompletableFuture<DistkvResponse> future) {
    if (needToSync(request)) {
      boolean isMaster = storeRuntime.getNodeInfo().isMaster();
      ConcurrentHashMap<String, SlaveClient> slaveClients = storeRuntime.getAllSlaveClients();
      if (isMaster) {
        for (SlaveClient client : slaveClients.values()) {
          synchronized (client) {
            try {
              DistkvResponse response =
                  client.getDistkvService().call(request).get();
              if (response.getStatus() != CommonProtocol.Status.OK) {
                future.complete(DistkvResponse.newBuilder()
                    .setStatus(CommonProtocol.Status.SYNC_ERROR).build());
              }
            } catch (ExecutionException | InterruptedException e) {
              future.complete(DistkvResponse.newBuilder()
                  .setStatus(CommonProtocol.Status.SYNC_ERROR).build());
              LOG.error("Process terminated because write to salve failed");
              Runtime.getRuntime().exit(-1);
            }
          }
        }
      }
    }
  }

  // Check if it's a request with ttl.
  private static boolean isTTLRequest(DistkvRequest distkvRequest) {
    RequestType requestType = distkvRequest.getRequestType();
    return requestType == TTL;
  }

  private boolean existsInStore(String key) {
    return storeEngine.exists(key);
  }

  // A helper method to check if it's a request with expiration.
  private static boolean needExpire(DistkvRequest distkvRequest) {
    RequestType requestType = distkvRequest.getRequestType();
    return requestType == EXPIRE;
  }

  // A helper method to query if we need sync the request to slaves.
  private static boolean needToSync(DistkvRequest distkvRequest) {
    RequestType requestType = distkvRequest.getRequestType();
    switch (requestType) {
      case STR_PUT:
      case LIST_PUT:
      case LIST_LPUT:
      case LIST_RPUT:
      case LIST_REMOVE:
      case LIST_MREMOVE:
      case SET_PUT:
      case SET_PUT_ITEM:
      case SET_REMOVE_ITEM:
      case DICT_PUT:
      case DICT_PUT_ITEM:
      case DICT_REMOVE_ITEM:
      case SLIST_PUT:
      case SLIST_PUT_MEMBER:
      case SLIST_INCR_SCORE:
      case SLIST_REMOVE_MEMBER:
      case INT_PUT:
      case INT_INCR:
      case DROP: {
        return true;
      }
      default: {
        break;
      }
    }
    return false;
  }

  private void storeHandler(
      DistkvRequest distkvRequest, DistkvResponse.Builder builder)
      throws InvalidProtocolBufferException {
    RequestType requestType = distkvRequest.getRequestType();
    String key = distkvRequest.getKey();
    // warning: Need to cover the exception of each case, otherwise the server will crash.
    switch (requestType) {
      case STR_PUT: {
        StringProtocol.StrPutRequest strPutRequest = distkvRequest.getRequest()
            .unpack(StringProtocol.StrPutRequest.class);
        try {
          storeEngine.strs().put(key, strPutRequest.getValue());
          builder.setStatus(CommonProtocol.Status.OK);
        } catch (DistkvKeyDuplicatedException e) {
          builder.setStatus(CommonProtocol.Status.DUPLICATED_KEY);
        }
        break;
      }
      case STR_GET: {
        try {
          String value = storeEngine.strs().get(key);
          StringProtocol.StrGetResponse strBuilder = StringProtocol.StrGetResponse
              .newBuilder().setValue(value).build();
          builder.setStatus(CommonProtocol.Status.OK).setResponse(Any.pack(strBuilder));
        } catch (KeyNotFoundException e) {
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
        }
        break;
      }
      case SET_PUT: {
        SetProtocol.SetPutRequest setPutRequest = distkvRequest.getRequest()
            .unpack(SetProtocol.SetPutRequest.class);
        // TODO(qwang): Any thoughts on how to avoid this `new HasSet`.
        try {
          storeEngine.sets().put(key, new HashSet<>(setPutRequest.getValuesList()));
        } catch (DistkvKeyDuplicatedException e) {
          builder.setStatus(CommonProtocol.Status.DUPLICATED_KEY);
        }
        builder.setStatus(CommonProtocol.Status.OK);
        break;
      }
      case SET_GET: {
        try {
          Set<String> values = storeEngine.sets().get(key);
          SetProtocol.SetGetResponse.Builder setBuilder = SetProtocol.SetGetResponse
              .newBuilder();
          values.forEach(setBuilder::addValues);
          builder.setStatus(CommonProtocol.Status.OK).setResponse(Any.pack(setBuilder.build()));
        } catch (KeyNotFoundException e) {
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
        } catch (DistkvException e) {
          builder.setStatus(CommonProtocol.Status.UNKNOWN_ERROR);
        }
        break;
      }
      case SET_PUT_ITEM: {
        SetProtocol.SetPutItemRequest setPutItemRequest = distkvRequest.getRequest()
            .unpack(SetProtocol.SetPutItemRequest.class);
        CommonProtocol.Status status;
        try {
          storeEngine.sets().putItem(key, setPutItemRequest.getItemValue());
          status = CommonProtocol.Status.OK;
        } catch (KeyNotFoundException e) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        }
        builder.setStatus(status);
        break;
      }
      case SET_REMOVE_ITEM: {
        SetProtocol.SetRemoveItemRequest setRemoveItemRequest = distkvRequest.getRequest()
            .unpack(SetProtocol.SetRemoveItemRequest.class);
        CommonProtocol.Status status = null;
        try {
          Status localStatus = storeEngine.sets()
              .removeItem(key, setRemoveItemRequest.getItemValue());
          if (localStatus == Status.OK) {
            status = CommonProtocol.Status.OK;
          } else if (localStatus == Status.KEY_NOT_FOUND) {
            status = CommonProtocol.Status.KEY_NOT_FOUND;
          }
        } catch (SetItemNotFoundException e) {
          status = CommonProtocol.Status.SET_ITEM_NOT_FOUND;
        } catch (DistkvException e) {
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case SET_EXISTS: {
        SetProtocol.SetExistsRequest setExistsRequest = distkvRequest.getRequest()
            .unpack(SetProtocol.SetExistsRequest.class);
        try {
          boolean result = storeEngine.sets().exists(key, setExistsRequest.getEntity());
          SetProtocol.SetExistsResponse.Builder setBuilder = SetProtocol.SetExistsResponse
              .newBuilder();
          setBuilder.setResult(result);
          builder.setResponse(Any.pack(setBuilder.build())).setStatus(CommonProtocol.Status.OK);
        } catch (KeyNotFoundException e) {
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
        } catch (DistkvException e) {
          builder.setStatus(CommonProtocol.Status.UNKNOWN_ERROR);
        }
        break;
      }
      case LIST_PUT: {
        ListProtocol.ListPutRequest listPutRequest = distkvRequest.getRequest()
            .unpack(ListProtocol.ListPutRequest.class);
        CommonProtocol.Status status = CommonProtocol.Status.OK;
        try {
          // TODO(qwang): Avoid this copy. See the discussion
          // at https://github.com/distkv-project/distkv/issues/349
          ArrayList<String> values = new ArrayList<>(listPutRequest.getValuesList());
          storeEngine.lists().put(key, values);
        } catch (DistkvKeyDuplicatedException e) {
          status = CommonProtocol.Status.DUPLICATED_KEY;
        } catch (DistkvException e) {
          LOG.error("Failed to put a list to store: {1}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case LIST_GET: {
        ListProtocol.ListGetRequest listGetRequest = distkvRequest.getRequest()
            .unpack(ListProtocol.ListGetRequest.class);
        CommonProtocol.Status status = CommonProtocol.Status.OK;
        final ListProtocol.GetType type = listGetRequest.getType();
        try {
          ListProtocol.ListGetResponse.Builder listBuilder = ListProtocol.ListGetResponse
              .newBuilder();
          if (type == ListProtocol.GetType.GET_ALL) {
            final List<String> values = storeEngine.lists().get(key);
            Optional.ofNullable(values).ifPresent(v -> listBuilder.addAllValues(values));
            builder.setResponse(Any.pack(listBuilder.build()));
          } else if (type == ListProtocol.GetType.GET_ONE) {
            Preconditions.checkState(listGetRequest.getIndex() >= 0);
            listBuilder.addValues(storeEngine.lists().get(key, listGetRequest.getIndex()));
            builder.setResponse(Any.pack(listBuilder.build()));
          } else if (type == ListProtocol.GetType.GET_RANGE) {
            final List<String> values = storeEngine.lists().get(
                key, listGetRequest.getFrom(), listGetRequest.getEnd());
            Optional.ofNullable(values).ifPresent(v -> listBuilder.addAllValues(values));
            builder.setResponse(Any.pack(listBuilder.build()));
          } else {
            LOG.error("Failed to get a list from store.");
            status = CommonProtocol.Status.UNKNOWN_REQUEST_TYPE;
          }
        } catch (KeyNotFoundException e) {
          LOG.info("Failed to get a list from store: {1}", e);
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (DistkvListIndexOutOfBoundsException e) {
          LOG.info("Failed to get a list from store: {1}", e);
          status = CommonProtocol.Status.LIST_INDEX_OUT_OF_BOUNDS;
        }
        builder.setStatus(status);
        break;
      }
      case LIST_LPUT: {
        ListProtocol.ListLPutRequest listLPutRequest = distkvRequest.getRequest()
            .unpack(ListProtocol.ListLPutRequest.class);
        CommonProtocol.Status status = null;
        try {
          Status localStatus =
              storeEngine.lists().lput(key, listLPutRequest.getValuesList());
          if (localStatus == Status.OK) {
            status = CommonProtocol.Status.OK;
          } else if (localStatus == Status.KEY_NOT_FOUND) {
            status = CommonProtocol.Status.KEY_NOT_FOUND;
          }
        } catch (DistkvException e) {
          status = CommonProtocol.Status.UNKNOWN_ERROR;
          LOG.error("Failed to lput a list to store: {1}", e);
        }
        builder.setStatus(status);
        break;
      }
      case LIST_RPUT: {
        ListProtocol.ListRPutRequest listRPutRequest = distkvRequest.getRequest()
            .unpack(ListProtocol.ListRPutRequest.class);
        CommonProtocol.Status status = null;
        try {
          Status localStatus =
              storeEngine.lists().rput(key, listRPutRequest.getValuesList());
          if (localStatus == Status.OK) {
            status = CommonProtocol.Status.OK;
          } else if (localStatus == Status.KEY_NOT_FOUND) {
            status = CommonProtocol.Status.KEY_NOT_FOUND;
          }
        } catch (DistkvException e) {
          status = CommonProtocol.Status.UNKNOWN_ERROR;
          LOG.error("Failed to rput a list to store: {1}", e);
        }
        builder.setStatus(status);
        break;
      }
      case LIST_MREMOVE: {
        ListProtocol.ListMRemoveRequest listMRemoveRequest = distkvRequest.getRequest()
            .unpack(ListProtocol.ListMRemoveRequest.class);
        CommonProtocol.Status status = null;
        try {
          Status localStatus =
              storeEngine.lists().mremove(key, listMRemoveRequest.getIndexesList());
          if (localStatus == Status.OK) {
            status = CommonProtocol.Status.OK;
          } else if (localStatus == Status.KEY_NOT_FOUND) {
            status = CommonProtocol.Status.KEY_NOT_FOUND;
          }
        } catch (KeyNotFoundException e) {
          LOG.info("Failed to mRemove item from store: {1}", e);
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (DistkvListIndexOutOfBoundsException e) {
          LOG.info("Failed to mRemove item from store: {1}", e);
          status = CommonProtocol.Status.LIST_INDEX_OUT_OF_BOUNDS;
        }
        builder.setStatus(status);
        break;
      }
      case LIST_REMOVE: {
        ListProtocol.ListRemoveRequest listRemoveRequest = distkvRequest.getRequest()
            .unpack(ListProtocol.ListRemoveRequest.class);
        CommonProtocol.Status status = null;
        final ListProtocol.RemoveType type = listRemoveRequest.getType();
        try {
          Status localStatus = null;
          if (type == ListProtocol.RemoveType.RemoveOne) {
            localStatus = storeEngine.lists().remove(key, listRemoveRequest.getIndex());
            if (localStatus == Status.OK) {
              status = CommonProtocol.Status.OK;
            } else if (localStatus == Status.KEY_NOT_FOUND) {
              status = CommonProtocol.Status.KEY_NOT_FOUND;
            }
          } else if (type == ListProtocol.RemoveType.RemoveRange) {
            localStatus = storeEngine.lists()
                .remove(key, listRemoveRequest.getFrom(), listRemoveRequest.getEnd());
          }
          if (localStatus == Status.OK) {
            status = CommonProtocol.Status.OK;
          } else if (localStatus == Status.KEY_NOT_FOUND) {
            status = CommonProtocol.Status.KEY_NOT_FOUND;
          }
        } catch (KeyNotFoundException e) {
          LOG.info("Failed to remove item from store: {1}", e);
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (DistkvListIndexOutOfBoundsException e) {
          LOG.info("Failed to remove item from store: {1}", e);
          status = CommonProtocol.Status.LIST_INDEX_OUT_OF_BOUNDS;
        }
        builder.setStatus(status);
        break;
      }
      case DICT_PUT: {
        DictProtocol.DictPutRequest dictPutRequest = distkvRequest.getRequest()
            .unpack(DictProtocol.DictPutRequest.class);
        try {
          final Map<String, String> map = new HashMap<>();
          DictProtocol.DistKVDict distKVDict = dictPutRequest.getDict();
          for (int i = 0; i < distKVDict.getKeysCount(); i++) {
            map.put(distKVDict.getKeys(i), distKVDict.getValues(i));
          }
          storeEngine.dicts().put(key, map);
          builder.setStatus(CommonProtocol.Status.OK);
        } catch (DistkvKeyDuplicatedException e) {
          builder.setStatus(CommonProtocol.Status.DUPLICATED_KEY);
        } catch (DistkvException e) {
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
        }
        break;
      }
      case DICT_GET: {
        Map<String, String> dict = null;
        try {
          dict = storeEngine.dicts().get(key);
        } catch (KeyNotFoundException e) {
          LOG.info("Failed to get dict from store: {1}", e);
        }
        if (dict == null) {
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
        } else {
          DictProtocol.DictGetResponse.Builder responseBuilder =
              DictProtocol.DictGetResponse.newBuilder();
          DictProtocol.DistKVDict.Builder dictBuilder = DictProtocol.DistKVDict.newBuilder();
          for (Map.Entry<String, String> entry : dict.entrySet()) {
            dictBuilder.addKeys(entry.getKey());
            dictBuilder.addValues(entry.getValue());
          }
          responseBuilder.setDict(dictBuilder);
          builder.setResponse(Any.pack(responseBuilder.build()));
        }
        break;
      }
      case DICT_GET_ITEM: {
        DictProtocol.DictGetItemRequest dictGetItemRequest = distkvRequest.getRequest()
            .unpack(DictProtocol.DictGetItemRequest.class);
        final Map<String, String> dict = storeEngine.dicts().get(key);
        builder.setStatus(CommonProtocol.Status.OK);
        if (dict == null) {
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
        } else {
          final String itemValue = dict.get(dictGetItemRequest.getItemKey());
          if (itemValue == null) {
            builder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
          } else {
            DictProtocol.DictGetItemResponse.Builder dictBuilder =
                DictProtocol.DictGetItemResponse.newBuilder();
            dictBuilder.setItemValue(itemValue);
            builder.setResponse(Any.pack(dictBuilder.build()));
          }
        }
        break;
      }
      case DICT_POP_ITEM: {
        DictProtocol.DictPopItemRequest dictPopItemRequest = distkvRequest.getRequest()
            .unpack(DictProtocol.DictPopItemRequest.class);
        builder.setStatus(CommonProtocol.Status.OK);
        final Map<String, String> dict = storeEngine.dicts().get(key);
        if (dict == null) {
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
        } else {
          final String itemValue = dict.remove(dictPopItemRequest.getItemKey());
          if (itemValue == null) {
            builder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
          } else {
            DictProtocol.DictPopItemResponse.Builder dictBuilder =
                DictProtocol.DictPopItemResponse.newBuilder();
            dictBuilder.setItemValue(itemValue);
            builder.setResponse(Any.pack(dictBuilder.build()));
          }
        }
        break;
      }
      case DICT_PUT_ITEM: {
        DictProtocol.DictPutItemRequest dictPutItemRequest = distkvRequest.getRequest()
            .unpack(DictProtocol.DictPutItemRequest.class);
        builder.setStatus(CommonProtocol.Status.OK);
        final Map<String, String> dict = storeEngine.dicts().get(key);
        if (dict == null) {
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
        } else {
          dict.put(dictPutItemRequest.getItemKey(), dictPutItemRequest.getItemValue());
        }
        break;
      }
      case DICT_REMOVE_ITEM: {
        DictProtocol.DictRemoveItemRequest dictRemoveItemRequest = distkvRequest.getRequest()
            .unpack(DictProtocol.DictRemoveItemRequest.class);
        builder.setStatus(CommonProtocol.Status.OK);
        final Map<String, String> dict = storeEngine.dicts().get(key);
        if (dict == null) {
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
        } else {
          final String itemValue = dict.remove(dictRemoveItemRequest.getItemKey());
          if (itemValue == null) {
            builder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
          }
          dict.remove(dictRemoveItemRequest.getItemKey());
        }
        break;
      }
      case SLIST_PUT: {
        SlistProtocol.SlistPutRequest slistPutRequest = distkvRequest.getRequest()
            .unpack(SlistProtocol.SlistPutRequest.class);
        CommonProtocol.Status status;
        try {
          LinkedList<SlistEntity> linkedList = new LinkedList<>();
          for (int i = 0; i < slistPutRequest.getListCount(); i++) {
            linkedList.add(new SlistEntity(slistPutRequest.getList(i).getMember(),
                slistPutRequest.getList(i).getScore()));
          }
          Slist slist = new SlistLinkedImpl();
          slist.put(linkedList);
          storeEngine.sortLists().put(key, slist);
          status = CommonProtocol.Status.OK;
        } catch (DistkvKeyDuplicatedException e) {
          status = CommonProtocol.Status.DUPLICATED_KEY;
        } catch (DistkvException e) {
          LOG.error("Failed to put a slist to store: {1}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case SLIST_TOP: {
        SlistProtocol.SlistTopRequest slistTopRequest = distkvRequest.getRequest()
            .unpack(SlistProtocol.SlistTopRequest.class);
        CommonProtocol.Status status;
        try {
          List<SlistEntity> topList =
              storeEngine.sortLists().top(key, slistTopRequest.getCount());
          ListIterator<SlistEntity> listIterator = topList.listIterator();
          SlistProtocol.SlistTopResponse.Builder slistBuilder =
              SlistProtocol.SlistTopResponse.newBuilder();
          while (listIterator.hasNext()) {
            SlistEntity entity = listIterator.next();
            SlistProtocol.SlistEntity.Builder slistEntity =
                SlistProtocol.SlistEntity.newBuilder();
            slistEntity.setScore(entity.getScore());
            slistEntity.setMember(entity.getMember());
            slistBuilder.addList(slistEntity.build());
          }
          builder.setResponse(Any.pack(slistBuilder.build()));
          status = CommonProtocol.Status.OK;
        } catch (KeyNotFoundException e) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (SlistTopNumIsNonNegativeException e) {
          status = CommonProtocol.Status.SLIST_TOPNUM_BE_POSITIVE;
        } catch (DistkvException e) {
          LOG.error("Failed to get a slist top in store: {1}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case SLIST_INCR_SCORE: {
        SlistProtocol.SlistIncrScoreRequest slistIncrScoreRequest = distkvRequest
            .getRequest()
            .unpack(SlistProtocol.SlistIncrScoreRequest.class);
        CommonProtocol.Status status;
        try {
          storeEngine.sortLists().incrScore(key,
              slistIncrScoreRequest.getMember(), slistIncrScoreRequest.getDelta());
          status = CommonProtocol.Status.OK;
        } catch (KeyNotFoundException e) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (SlistMemberNotFoundException e) {
          status = CommonProtocol.Status.SLIST_MEMBER_NOT_FOUND;
        } catch (DistkvException e) {
          LOG.error("Failed to incr a slist score in store: {1}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case SLIST_PUT_MEMBER: {
        SlistProtocol.SlistPutMemberRequest slistPutMemberRequest =
            distkvRequest.getRequest().unpack(SlistProtocol.SlistPutMemberRequest.class);
        CommonProtocol.Status status;
        try {
          storeEngine.sortLists().putMember(
              key, new SlistEntity(slistPutMemberRequest.getMember(),
                  slistPutMemberRequest.getScore()));
          status = CommonProtocol.Status.OK;
        } catch (KeyNotFoundException e) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (DistkvException e) {
          LOG.error("Failed to put a slist number in store: {1}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case SLIST_REMOVE_MEMBER: {
        SlistProtocol.SlistRemoveMemberRequest slistRemoveMemberRequest =
            distkvRequest.getRequest()
                .unpack(SlistProtocol.SlistRemoveMemberRequest.class);
        CommonProtocol.Status status;
        try {
          storeEngine.sortLists().removeMember(key, slistRemoveMemberRequest.getMember());
          status = CommonProtocol.Status.OK;
        } catch (KeyNotFoundException e) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (SlistMemberNotFoundException e) {
          status = CommonProtocol.Status.SLIST_MEMBER_NOT_FOUND;
        } catch (DistkvException e) {
          LOG.error("Failed to remove slist member in store :{1}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case SLIST_GET_MEMBER: {
        SlistProtocol.SlistGetMemberRequest slistGetMemberRequest =
            distkvRequest.getRequest()
                .unpack(SlistProtocol.SlistGetMemberRequest.class);
        CommonProtocol.Status status;
        try {
          DistkvTuple<Integer, Integer> tuple =
              storeEngine.sortLists().getMember(key, slistGetMemberRequest.getMember());
          SlistProtocol.SlistEntity.Builder slistEntity =
              SlistProtocol.SlistEntity.newBuilder();
          slistEntity.setMember(slistGetMemberRequest.getMember());
          slistEntity.setScore(tuple.getFirst());
          SlistProtocol.SlistGetMemberResponse.Builder slistBuilder =
              SlistProtocol.SlistGetMemberResponse.newBuilder();
          slistBuilder.setEntity(slistEntity);
          slistBuilder.setCount(tuple.getSecond());
          builder.setResponse(Any.pack(slistBuilder.build()));
          status = CommonProtocol.Status.OK;
        } catch (KeyNotFoundException e) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (SlistMemberNotFoundException e) {
          status = CommonProtocol.Status.SLIST_MEMBER_NOT_FOUND;
        } catch (DistkvException e) {
          LOG.error("Failed to get slist member in store :{1}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case INT_PUT: {
        IntProtocol.IntPutRequest intPutRequest = distkvRequest.getRequest()
            .unpack(IntProtocol.IntPutRequest.class);
        try {
          storeEngine.ints().put(key, intPutRequest.getValue());
        } catch (DistkvKeyDuplicatedException e) {
          builder.setStatus(CommonProtocol.Status.DUPLICATED_KEY);
        }
        builder.setStatus(CommonProtocol.Status.OK);
        break;
      }
      case INT_GET: {
        try {
          int value = storeEngine.ints().get(key);
          IntProtocol.IntGetResponse intBuilder = IntProtocol.IntGetResponse
              .newBuilder().setValue(value).build();
          builder.setStatus(CommonProtocol.Status.OK).setResponse(Any.pack(intBuilder));
        } catch (KeyNotFoundException e) {
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
        }
        break;
      }
      case INT_INCR: {
        IntProtocol.IntIncrRequest intIncrRequest = distkvRequest
            .getRequest()
            .unpack(IntProtocol.IntIncrRequest.class);
        CommonProtocol.Status status;
        try {
          storeEngine.ints().incr(key, intIncrRequest.getDelta());
          status = CommonProtocol.Status.OK;
        } catch (KeyNotFoundException e) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (DistkvException e) {
          LOG.error("Failed to incr a int value in ints store: {1}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case DROP: {
        builder.setStatus(drop(key));
        break;
      }
      case EXISTS: {
        try {
          boolean exists = storeEngine.exists(key);
          ExistsResponse existsResponse = ExistsResponse.newBuilder().setExists(exists).build();
          builder.setStatus(CommonProtocol.Status.OK).setResponse(Any.pack(existsResponse));
        } catch (KeyNotFoundException e) {
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
        } catch (DistkvException e) {
          LOG.error("Failed to determine if a key exists in store: {1}", e);
          builder.setStatus(CommonProtocol.Status.UNKNOWN_ERROR);
        }
        break;
      }
      default: {
        break;
      }
    }
  }

  /// A helper method to drop an item by the given key.
  private CommonProtocol.Status drop(String key) {
    CommonProtocol.Status status = null;
    try {
      Status localStatus = storeEngine.sets().drop(key);
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (DistkvException e) {
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    return status;
  }

}
