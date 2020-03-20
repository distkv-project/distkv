package com.distkv.server.storeserver.runtime.workerpool;

import com.distkv.core.KVStore;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.server.storeserver.runtime.StoreRuntime;
import com.distkv.server.storeserver.runtime.slave.SlaveClient;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

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
              if (response.getStatus() != CommonProtocol.Status.OK) {
                future.complete(DistkvResponse.newBuilder()
                    .setStatus(CommonProtocol.Status.SYNC_ERROR).build());
              }
            } catch (ExecutionException | InterruptedException e) {
              future.complete(DistkvResponse.newBuilder()
                  .setStatus(CommonProtocol.Status.SYNC_ERROR).build());
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

  private void storeHandler(
      DistkvRequest distkvRequest, DistkvResponse.Builder builder)
      throws InvalidProtocolBufferException {
    RequestType requestType = distkvRequest.getRequestType();
    String key = distkvRequest.getKey();
    // warning: Need to cover the exception of each case, otherwise the server will crash.
    switch (requestType) {
      case STR_PUT: {
        storeEngine.strs().put(key, distkvRequest.getRequest(), builder);
        break;
      }
      case STR_DROP: {
        storeEngine.strs().drop(key, builder);
        break;
      }
      case STR_GET: {
        storeEngine.strs().get(key, builder);
        break;
      }
      case SET_PUT: {
        storeEngine.sets().put(key, distkvRequest.getRequest(), builder);
        break;
      }
      case SET_GET: {
        storeEngine.sets().get(key, builder);
        break;
      }
      case SET_PUT_ITEM: {
        storeEngine.sets().putItem(key, distkvRequest.getRequest(), builder);
        break;
      }
      case SET_REMOVE_ITEM: {
        storeEngine.sets().removeItem(key, distkvRequest.getRequest(), builder);
        break;
      }
      case SET_EXISTS: {
        storeEngine.sets().exists(key, distkvRequest.getRequest(), builder);
        break;
      }
      case SET_DROP: {
        storeEngine.sets().drop(key, builder);
        break;
      }
      case LIST_PUT: {
        storeEngine.lists().put(key, distkvRequest.getRequest(), builder);
        break;
      }
      case LIST_GET: {
        storeEngine.lists().get(key, distkvRequest.getRequest(), builder);
        break;
      }
      case LIST_LPUT: {
        storeEngine.lists().lput(key, distkvRequest.getRequest(), builder);
        break;
      }
      case LIST_RPUT: {
        storeEngine.lists().rput(key, distkvRequest.getRequest(), builder);
        break;
      }
      case LIST_DROP: {
        storeEngine.lists().drop(key, builder);
        break;
      }
      case LIST_MREMOVE: {
        storeEngine.lists().mremove(key, distkvRequest.getRequest(), builder);
        break;
      }
      case LIST_REMOVE: {
        storeEngine.lists().remove(key, distkvRequest.getRequest(), builder);
        break;
      }
      case DICT_PUT: {
        storeEngine.dicts().put(key, distkvRequest.getRequest(), builder);
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
        storeEngine.dicts().putItem(key, distkvRequest.getRequest(), builder);
        break;
      }
      case DICT_REMOVE_ITEM: {
        storeEngine.dicts().removeItem(key, distkvRequest.getRequest(), builder);
        break;
      }
      case DICT_DROP: {
        storeEngine.dicts().drop(key, builder);
        break;
      }
      case SORTED_LIST_PUT: {
        storeEngine.sortLists().put(key, distkvRequest.getRequest(), builder);
        break;
      }
      case SORTED_LIST_TOP: {
        storeEngine.sortLists().top(key, distkvRequest.getRequest(), builder);
        break;
      }
      case SORTED_LIST_DROP: {
        storeEngine.sortLists().drop(key, builder);
        break;
      }
      case SORTED_LIST_INCR_SCORE: {
        storeEngine.sortLists().incrScore(key, distkvRequest.getRequest(), builder);
        break;
      }
      case SORTED_LIST_PUT_MEMBER: {
        storeEngine.sortLists().putMember(key, distkvRequest.getRequest(), builder);
        break;
      }
      case SORTED_LIST_REMOVE_MEMBER: {
        storeEngine.sortLists().removeMember(key, distkvRequest.getRequest(), builder);
        break;
      }
      case SORTED_LIST_GET_MEMBER: {
        storeEngine.sortLists().getMember(key, distkvRequest.getRequest(), builder);
        break;
      }
      case INT_PUT: {
        storeEngine.ints().put(key, distkvRequest.getRequest(), builder);
        break;
      }
      case INT_DROP: {
        storeEngine.ints().drop(key, builder);
        break;
      }
      case INT_GET: {
        storeEngine.ints().get(key, builder);
        break;
      }
      case INT_INCR: {
        storeEngine.ints().incr(key, distkvRequest.getRequest(), builder);
        break;
      }
      default: {
        break;
      }
    }
  }

}
