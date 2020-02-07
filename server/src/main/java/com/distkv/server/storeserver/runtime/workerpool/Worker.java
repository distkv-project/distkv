package com.distkv.server.storeserver.runtime.workerpool;

import com.distkv.common.DistKVTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.common.exception.DistKVException;
import com.distkv.common.exception.DistKVListIndexOutOfBoundsException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.exception.SortedListMemberNotFoundException;
import com.distkv.common.exception.SortedListTopNumIsNonNegativeException;
import com.distkv.common.utils.Status;
import com.distkv.core.KVStore;
import com.distkv.core.KVStoreImpl;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.server.storeserver.runtime.StoreRuntime;
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
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Worker extends Thread {

  private StoreRuntime storeRuntime;

  private static Logger LOGGER = LoggerFactory.getLogger(Worker.class);

  public Worker(StoreRuntime storeRuntime) {
    this.storeRuntime = storeRuntime;
    queue = new LinkedBlockingQueue<>();
  }

  private BlockingQueue<InternalRequest> queue;

  // Note that this method is threading-safe because of the threading-safe blocking queue.
  public void post(InternalRequest internalRequest) throws InterruptedException {
    queue.put(internalRequest);
  }


  /**
   * Store engine.
   */
  private KVStore storeEngine = new KVStoreImpl();

  @SuppressWarnings({"unchecked"})
  @Override
  public void run() {
    while (true) {
      try {
        InternalRequest internalRequest = queue.take();

        DistkvProtocol.DistkvRequest distkvRequest = internalRequest.getRequest();
        CompletableFuture<DistkvProtocol.DistkvResponse> future = internalRequest
            .getCompletableFuture();
        DistkvProtocol.DistkvResponse.Builder builder = DistkvProtocol.DistkvResponse
            .newBuilder();
        handleCenter(distkvRequest, builder);
        future.complete(builder.setRequestType(distkvRequest.getRequestType()).build());
      } catch (Throwable e) {
        LOGGER.error("Failed to execute event loop:" + e);
        // TODO(tuowang): Clean up some resource associated with StoreRuntime
        storeRuntime.shutdown();
        Runtime.getRuntime().exit(-1);
      }
    }
  }

  private void handleCenter(
      DistkvProtocol.DistkvRequest distkvRequest, DistkvProtocol.DistkvResponse.Builder builder)
      throws InvalidProtocolBufferException {
    DistkvProtocol.RequestType requestType = distkvRequest.getRequestType();
    String key = distkvRequest.getKey();
    switch (requestType) {
      case STR_PUT: {
        StringProtocol.StrPutRequest strPutRequest = distkvRequest.getRequest()
            .unpack(StringProtocol.StrPutRequest.class);
        storeEngine.strs().put(key, strPutRequest.getValue());
        builder.setStatus(CommonProtocol.Status.OK);
        break;
      }
      case STR_DROP: {
        CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
        try {
          Status localStatus = storeEngine.strs().drop(key);
          if (localStatus == Status.OK) {
            status = CommonProtocol.Status.OK;
          } else if (localStatus == Status.KEY_NOT_FOUND) {
            status = CommonProtocol.Status.KEY_NOT_FOUND;
          }
        } catch (DistKVException e) {
          LOGGER.error("Failed to drop a string to store :{1}", e);
        }
        builder.setStatus(status);
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
        storeEngine.sets().put(key, new HashSet<>(setPutRequest.getValuesList()));
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
        } catch (DistKVException e) {
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
        } catch (DistKVException e) {
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
        } catch (DistKVException e) {
          builder.setStatus(CommonProtocol.Status.UNKNOWN_ERROR);
        }
        break;
      }
      case SET_DROP: {
        CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
        try {
          Status localStatus = storeEngine.sets().drop(key);
          if (localStatus == Status.OK) {
            status = CommonProtocol.Status.OK;
          } else if (localStatus == Status.KEY_NOT_FOUND) {
            status = CommonProtocol.Status.KEY_NOT_FOUND;
          }
        } catch (DistKVException e) {
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
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
        } catch (DistKVException e) {
          LOGGER.error("Failed to put a list to store: {1}", e);
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
            LOGGER.error("Failed to get a list from store.");
            status = CommonProtocol.Status.UNKNOWN_REQUEST_TYPE;
          }
        } catch (KeyNotFoundException e) {
          LOGGER.info("Failed to get a list from store: {1}", e);
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (DistKVListIndexOutOfBoundsException e) {
          LOGGER.info("Failed to get a list from store: {1}", e);
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
        } catch (DistKVException e) {
          status = CommonProtocol.Status.UNKNOWN_ERROR;
          LOGGER.error("Failed to lput a list to store: {1}", e);
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
        } catch (DistKVException e) {
          status = CommonProtocol.Status.UNKNOWN_ERROR;
          LOGGER.error("Failed to rput a list to store: {1}", e);
        }
        builder.setStatus(status);
        break;
      }
      case LIST_DROP: {
        CommonProtocol.Status status = null;
        try {
          Status localStatus = storeEngine.lists().drop(key);
          if (localStatus == Status.OK) {
            status = CommonProtocol.Status.OK;
          } else if (localStatus == Status.KEY_NOT_FOUND) {
            status = CommonProtocol.Status.KEY_NOT_FOUND;
          }
        } catch (DistKVException e) {
          status = CommonProtocol.Status.UNKNOWN_ERROR;
          LOGGER.error("Failed to drop a list from store: {1}", e);
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
          LOGGER.info("Failed to mRemove item from store: {1}", e);
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (DistKVListIndexOutOfBoundsException e) {
          LOGGER.info("Failed to mRemove item from store: {1}", e);
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
          LOGGER.info("Failed to remove item from store: {1}", e);
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (DistKVListIndexOutOfBoundsException e) {
          LOGGER.info("Failed to remove item from store: {1}", e);
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
        } catch (Exception e) {
          // TODO(qwang): Use DistKVException instead of Exception here.
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
        }
        break;
      }
      case DICT_GET: {
        Map<String, String> dict = null;
        try {
          dict = storeEngine.dicts().get(key);
        } catch (KeyNotFoundException e) {
          LOGGER.info("Failed to get dict from store: {1}", e);
        }
        if (dict == null) {
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
        } else {
          DictProtocol.DistKVDict.Builder dictBuilder = DictProtocol.DistKVDict.newBuilder();
          for (Map.Entry<String, String> entry : dict.entrySet()) {
            dictBuilder.addKeys(entry.getKey());
            dictBuilder.addValues(entry.getValue());
          }
          builder.setResponse(Any.pack(dictBuilder.build()));
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
      case DICT_DROP: {
        builder.setStatus(CommonProtocol.Status.OK);
        Status status = storeEngine.dicts().drop(key);
        if (Status.KEY_NOT_FOUND == status) {
          builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
        } else if (Status.OK != status) {
          builder.setStatus(CommonProtocol.Status.UNKNOWN_ERROR);
        }
        break;
      }
      case SORTED_LIST_PUT: {
        SortedListProtocol.SlistPutRequest slistPutRequest = distkvRequest.getRequest()
            .unpack(SortedListProtocol.SlistPutRequest.class);
        CommonProtocol.Status status = null;
        try {
          LinkedList<SortedListEntity> linkedList = new LinkedList<>();
          for (int i = 0; i < slistPutRequest.getListCount(); i++) {
            linkedList.add(new SortedListEntity(slistPutRequest.getList(i).getMember(),
                slistPutRequest.getList(i).getScore()));
          }
          storeEngine.sortLists().put(key, linkedList);
          status = CommonProtocol.Status.OK;
        } catch (DistKVException e) {
          LOGGER.error("Failed to put a slist to store: {1}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case SORTED_LIST_TOP: {
        SortedListProtocol.SlistTopRequest slistTopRequest = distkvRequest.getRequest()
            .unpack(SortedListProtocol.SlistTopRequest.class);
        CommonProtocol.Status status = null;
        try {
          List<SortedListEntity> topList =
              storeEngine.sortLists().top(key, slistTopRequest.getCount());
          ListIterator<SortedListEntity> listIterator = topList.listIterator();
          while (listIterator.hasNext()) {
            SortedListEntity entity = listIterator.next();
            SortedListProtocol.SortedListEntity.Builder slistEntity =
                SortedListProtocol.SortedListEntity.newBuilder();
            slistEntity.setScore(entity.getScore());
            slistEntity.setMember(entity.getMember());
            SortedListProtocol.SlistTopResponse.Builder slistBuilder =
                SortedListProtocol.SlistTopResponse.newBuilder();
            slistBuilder.addList(slistEntity.build());
            builder.setResponse(Any.pack(slistBuilder.build()));
          }
          status = CommonProtocol.Status.OK;
        } catch (KeyNotFoundException e) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (SortedListTopNumIsNonNegativeException e) {
          status = CommonProtocol.Status.SLIST_TOPNUM_BE_POSITIVE;
        } catch (DistKVException e) {
          LOGGER.error("Failed to get a slist top in store: {1}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case SORTED_LIST_DROP: {
        CommonProtocol.Status status;
        try {
          storeEngine.sortLists().drop(key);
          status = CommonProtocol.Status.OK;
        } catch (KeyNotFoundException e) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (DistKVException e) {
          LOGGER.error("Failed to drop a slist in store: {1}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case SORTED_LIST_INCR_SCORE: {
        SortedListProtocol.SlistIncrScoreRequest slistIncrScoreRequest = distkvRequest
            .getRequest()
            .unpack(SortedListProtocol.SlistIncrScoreRequest.class);
        CommonProtocol.Status status;
        try {
          storeEngine.sortLists().incrScore(key,
              slistIncrScoreRequest.getMember(), slistIncrScoreRequest.getDelta());
          status = CommonProtocol.Status.OK;
        } catch (KeyNotFoundException e) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (SortedListMemberNotFoundException e) {
          status = CommonProtocol.Status.SLIST_MEMBER_NOT_FOUND;
        } catch (DistKVException e) {
          LOGGER.error("Failed to incr a slist score in store: {1}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case SORTED_LIST_PUT_MEMBER: {
        SortedListProtocol.SlistPutMemberRequest slistPutMemberRequest =
            distkvRequest.getRequest().unpack(SortedListProtocol.SlistPutMemberRequest.class);
        CommonProtocol.Status status;
        try {
          storeEngine.sortLists().putMember(
              key, new SortedListEntity(slistPutMemberRequest.getMember(),
                  slistPutMemberRequest.getScore()));
          status = CommonProtocol.Status.OK;
        } catch (KeyNotFoundException e) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (DistKVException e) {
          LOGGER.error("Failed to put a slist number in store: {1}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case SORTED_LIST_REMOVE_MEMBER: {
        SortedListProtocol.SlistRemoveMemberRequest slistRemoveMemberRequest =
            distkvRequest.getRequest()
                .unpack(SortedListProtocol.SlistRemoveMemberRequest.class);
        CommonProtocol.Status status;
        try {
          storeEngine.sortLists().removeMember(key, slistRemoveMemberRequest.getMember());
          status = CommonProtocol.Status.OK;
        } catch (KeyNotFoundException e) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (SortedListMemberNotFoundException e) {
          status = CommonProtocol.Status.SLIST_MEMBER_NOT_FOUND;
        } catch (DistKVException e) {
          LOGGER.error("Failed to remove slist member in store :{1}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      case SORTED_LIST_GET_MEMBER: {
        SortedListProtocol.SlistGetMemberRequest slistGetMemberRequest =
            distkvRequest.getRequest()
                .unpack(SortedListProtocol.SlistGetMemberRequest.class);
        CommonProtocol.Status status;
        try {
          DistKVTuple<Integer, Integer> tuple =
              storeEngine.sortLists().getMember(key, slistGetMemberRequest.getMember());
          SortedListProtocol.SortedListEntity.Builder slistEntity =
              SortedListProtocol.SortedListEntity.newBuilder();
          slistEntity.setMember(slistGetMemberRequest.getMember());
          slistEntity.setScore(tuple.getFirst());
          SortedListProtocol.SlistGetMemberResponse.Builder slistBuilder =
              SortedListProtocol.SlistGetMemberResponse.newBuilder();
          slistBuilder.setEntity(slistEntity);
          slistBuilder.setCount(tuple.getSecond());
          builder.setResponse(Any.pack(slistBuilder.build()));
          status = CommonProtocol.Status.OK;
        } catch (KeyNotFoundException e) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        } catch (SortedListMemberNotFoundException e) {
          status = CommonProtocol.Status.SLIST_MEMBER_NOT_FOUND;
        } catch (DistKVException e) {
          LOGGER.error("Failed to get slist member in store :{}", e);
          status = CommonProtocol.Status.UNKNOWN_ERROR;
        }
        builder.setStatus(status);
        break;
      }
      default: {
        break;
      }
    }
  }

}
