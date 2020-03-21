package com.distkv.server.storeserver.runtime.workerpool;

import com.distkv.common.exception.DictKeyNotFoundException;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.DistkvListIndexOutOfBoundsException;
import com.distkv.common.exception.DistkvUnknownRequestException;
import com.distkv.common.exception.DistkvWrongRequestFormatException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.exception.MasterSyncToSlaveException;
import com.distkv.common.exception.SetItemNotFoundException;
import com.distkv.common.exception.SortedListMemberNotFoundException;
import com.distkv.common.exception.SortedListTopNumIsNonNegativeException;
import com.distkv.core.KVStore;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.server.storeserver.runtime.StoreRuntime;
import com.distkv.server.storeserver.runtime.slave.SlaveClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

import static com.distkv.rpc.protobuf.generated.CommonProtocol.Status.DICT_KEY_NOT_FOUND;
import static com.distkv.rpc.protobuf.generated.CommonProtocol.Status.DUPLICATED_KEY;
import static com.distkv.rpc.protobuf.generated.CommonProtocol.Status.KEY_NOT_FOUND;
import static com.distkv.rpc.protobuf.generated.CommonProtocol.Status.LIST_INDEX_OUT_OF_BOUNDS;
import static com.distkv.rpc.protobuf.generated.CommonProtocol.Status.OK;
import static com.distkv.rpc.protobuf.generated.CommonProtocol.Status.SET_ITEM_NOT_FOUND;
import static com.distkv.rpc.protobuf.generated.CommonProtocol.Status.SLIST_MEMBER_NOT_FOUND;
import static com.distkv.rpc.protobuf.generated.CommonProtocol.Status.SLIST_TOPNUM_BE_POSITIVE;
import static com.distkv.rpc.protobuf.generated.CommonProtocol.Status.SYNC_ERROR;
import static com.distkv.rpc.protobuf.generated.CommonProtocol.Status.UNKNOWN_ERROR;
import static com.distkv.rpc.protobuf.generated.CommonProtocol.Status.UNKNOWN_REQUEST_TYPE;
import static com.distkv.rpc.protobuf.generated.CommonProtocol.Status.WRONG_REQUEST_FORMAT;

public class Worker extends Thread {

  private static Logger LOGGER = LoggerFactory.getLogger(Worker.class);

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

        handleExpiration(distkvRequest);
        syncToSlaves(distkvRequest, future);
        storeHandler(distkvRequest, builder);

        future.complete(builder.setRequestType(distkvRequest.getRequestType()).build());
      } catch (Throwable e) {
        LOGGER.error("Failed to execute event loop:" + e);
        // TODO(tuowang): Clean up some resource associated with StoreRuntime
        storeRuntime.shutdown();
        Runtime.getRuntime().exit(-1);
      }
    }
  }

  // Add expire request to ExpireCycle.
  private void handleExpiration(DistkvRequest request) {
    if (needExpire(request)) {
      storeRuntime.getExpirationManager().addToCycle(request);
    }
  }

  private void syncToSlaves(DistkvRequest request, CompletableFuture<DistkvResponse> future) {
    if (needToSync(request)) {
      boolean isMaster = storeRuntime.getConfig().isMaster();
      List<SlaveClient> slaveClients = storeRuntime.getAllSlaveClients();
      if (isMaster) {
        for (SlaveClient client : slaveClients) {
          synchronized (client) {
            try {
              DistkvResponse response =
                  client.getDistkvService().call(request).get();
              if (response.getStatus() != OK) {
                future.complete(DistkvResponse.newBuilder()
                    .setStatus(SYNC_ERROR).build());
              }
            } catch (ExecutionException | InterruptedException e) {
              future.complete(DistkvResponse.newBuilder()
                  .setStatus(SYNC_ERROR).build());
              LOGGER.error("Process terminated because write to salve failed");
              Runtime.getRuntime().exit(-1);
            }
          }
        }
      }
    }
  }

  // A helper method to check if it's a request with expiration.
  private static boolean needExpire(DistkvRequest distkvRequest) {
    RequestType requestType = distkvRequest.getRequestType();
    switch (requestType) {
      case EXPIRED_STR:
      case EXPIRED_LIST:
      case EXPIRED_SET:
      case EXPIRED_DICT:
      case EXPIRED_INT:
      case EXPIRED_SLIST: {
        return true;
      }
      default: {
        break;
      }
    }
    return false;
  }

  // A helper method to query if we need sync the request to slaves.
  private static boolean needToSync(DistkvRequest distkvRequest) {
    RequestType requestType = distkvRequest.getRequestType();
    switch (requestType) {
      case STR_PUT:
      case STR_DROP:
      case LIST_PUT:
      case LIST_DROP:
      case LIST_LPUT:
      case LIST_RPUT:
      case LIST_REMOVE:
      case LIST_MREMOVE:
      case SET_PUT:
      case SET_DROP:
      case SET_PUT_ITEM:
      case SET_REMOVE_ITEM:
      case DICT_PUT:
      case DICT_DROP:
      case DICT_PUT_ITEM:
      case DICT_REMOVE_ITEM:
      case SORTED_LIST_PUT:
      case SORTED_LIST_DROP:
      case SORTED_LIST_PUT_MEMBER:
      case SORTED_LIST_INCR_SCORE:
      case SORTED_LIST_REMOVE_MEMBER:
      case INT_PUT:
      case INT_INCR:
      case INT_DROP: {
        return true;
      }
      default: {
        break;
      }
    }
    return false;
  }

  private void storeHandler(DistkvRequest distkvRequest, DistkvResponse.Builder builder) {
    RequestType requestType = distkvRequest.getRequestType();
    String key = distkvRequest.getKey();
    // warning: Need to cover the exception of each case, otherwise the server will crash.
    try {
      switch (requestType) {
        case STR_PUT: {
          storeEngine.strs().put(key, distkvRequest.getRequest());
          break;
        }
        case STR_DROP: {
          storeEngine.strs().drop(key);
          break;
        }
        case STR_GET: {
          storeEngine.strs().get(key, builder);
          break;
        }
        case SET_PUT: {
          storeEngine.sets().put(key, distkvRequest.getRequest());
          break;
        }
        case SET_GET: {
          storeEngine.sets().get(key, builder);
          break;
        }
        case SET_PUT_ITEM: {
          storeEngine.sets().putItem(key, distkvRequest.getRequest());
          break;
        }
        case SET_REMOVE_ITEM: {
          storeEngine.sets().removeItem(key, distkvRequest.getRequest());
          break;
        }
        case SET_EXISTS: {
          storeEngine.sets().exists(key, distkvRequest.getRequest(), builder);
          break;
        }
        case SET_DROP: {
          storeEngine.sets().drop(key);
          break;
        }
        case LIST_PUT: {
          storeEngine.lists().put(key, distkvRequest.getRequest());
          break;
        }
        case LIST_GET: {
          storeEngine.lists().get(key, distkvRequest.getRequest(), builder);
          break;
        }
        case LIST_LPUT: {
          storeEngine.lists().lput(key, distkvRequest.getRequest());
          break;
        }
        case LIST_RPUT: {
          storeEngine.lists().rput(key, distkvRequest.getRequest());
          break;
        }
        case LIST_DROP: {
          storeEngine.lists().drop(key);
          break;
        }
        case LIST_MREMOVE: {
          storeEngine.lists().mremove(key, distkvRequest.getRequest());
          break;
        }
        case LIST_REMOVE: {
          storeEngine.lists().remove(key, distkvRequest.getRequest());
          break;
        }
        case DICT_PUT: {
          storeEngine.dicts().put(key, distkvRequest.getRequest());
          break;
        }
        case DICT_GET: {
          storeEngine.dicts().get(key, builder);
          break;
        }
        case DICT_GET_ITEM: {
          storeEngine.dicts().getItem(key, distkvRequest.getRequest(), builder);
          break;
        }
        case DICT_POP_ITEM: {
          storeEngine.dicts().popItem(key, distkvRequest.getRequest(), builder);
          break;
        }
        case DICT_PUT_ITEM: {
          storeEngine.dicts().putItem(key, distkvRequest.getRequest());
          break;
        }
        case DICT_REMOVE_ITEM: {
          storeEngine.dicts().removeItem(key, distkvRequest.getRequest());
          break;
        }
        case DICT_DROP: {
          storeEngine.dicts().drop(key);
          break;
        }
        case SORTED_LIST_PUT: {
          storeEngine.sortLists().put(key, distkvRequest.getRequest());
          break;
        }
        case SORTED_LIST_TOP: {
          storeEngine.sortLists().top(key, distkvRequest.getRequest(), builder);
          break;
        }
        case SORTED_LIST_DROP: {
          storeEngine.sortLists().drop(key);
          break;
        }
        case SORTED_LIST_INCR_SCORE: {
          storeEngine.sortLists().incrScore(key, distkvRequest.getRequest());
          break;
        }
        case SORTED_LIST_PUT_MEMBER: {
          storeEngine.sortLists().putMember(key, distkvRequest.getRequest());
          break;
        }
        case SORTED_LIST_REMOVE_MEMBER: {
          storeEngine.sortLists().removeMember(key, distkvRequest.getRequest());
          break;
        }
        case SORTED_LIST_GET_MEMBER: {
          storeEngine.sortLists().getMember(key, distkvRequest.getRequest(), builder);
          break;
        }
        case INT_PUT: {
          storeEngine.ints().put(key, distkvRequest.getRequest());
          break;
        }
        case INT_DROP: {
          storeEngine.ints().drop(key);
          break;
        }
        case INT_GET: {
          storeEngine.ints().get(key, builder);
          break;
        }
        case INT_INCR: {
          storeEngine.ints().incr(key, distkvRequest.getRequest());
          break;
        }
        default: {
          break;
        }
      }
      builder.setStatus(OK);
    } catch (DistkvException e) {
      handleDistkvException(e, builder);
    }
  }

  private void handleDistkvException(DistkvException e, DistkvResponse.Builder builder) {
    if (e instanceof DistkvWrongRequestFormatException) {
      builder.setStatus(WRONG_REQUEST_FORMAT);
    } else if (e instanceof KeyNotFoundException) {
      builder.setStatus(KEY_NOT_FOUND);
    } else if (e instanceof DistkvUnknownRequestException) {
      builder.setStatus(UNKNOWN_REQUEST_TYPE);
    } else if (e instanceof DistkvListIndexOutOfBoundsException) {
      builder.setStatus(LIST_INDEX_OUT_OF_BOUNDS);
    } else if (e instanceof DictKeyNotFoundException) {
      builder.setStatus(DICT_KEY_NOT_FOUND);
    } else if (e instanceof SortedListMemberNotFoundException) {
      builder.setStatus(SLIST_MEMBER_NOT_FOUND);
    } else if (e instanceof SortedListTopNumIsNonNegativeException) {
      builder.setStatus(SLIST_TOPNUM_BE_POSITIVE);
    } else if (e instanceof MasterSyncToSlaveException) {
      builder.setStatus(SYNC_ERROR);
    } else if (e instanceof DistkvKeyDuplicatedException) {
      builder.setStatus(DUPLICATED_KEY);
    } else if (e instanceof SetItemNotFoundException) {
      builder.setStatus(SET_ITEM_NOT_FOUND);
    } else {
      builder.setStatus(UNKNOWN_ERROR);
    }
  }

}
