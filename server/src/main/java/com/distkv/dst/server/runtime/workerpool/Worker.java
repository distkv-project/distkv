package com.distkv.dst.server.runtime.workerpool;

import com.distkv.dst.common.NodeInfo;
import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.exception.DstListIndexOutOfBoundsException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.utils.Status;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.core.KVStoreImpl;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.ListProtocol;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

public class Worker extends Thread {

  private NodeInfo nodeInfo;

  private NodeInstance nodeInstance;

  private static Logger LOGGER = LoggerFactory.getLogger(Worker.class);

  public Worker() {
    queue = new LinkedBlockingQueue<>();
  }

  private BlockingQueue<InternalRequest> queue;

  // Note that this method is threading-safe.
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
        switch (internalRequest.getRequestType()) {
          case STR_PUT: {
            StringProtocol.PutRequest strPutRequest =
                (StringProtocol.PutRequest) internalRequest.getRequest();
            // try put.
            storeEngine.strs().put(strPutRequest.getKey(), strPutRequest.getValue());
            CompletableFuture<StringProtocol.PutResponse> future =
                (CompletableFuture<StringProtocol.PutResponse>)
                    internalRequest.getCompletableFuture();
            future.complete(StringProtocol.PutResponse.newBuilder()
                .setStatus(CommonProtocol.Status.OK).build());
            break;
          }
          case STR_GET: {
            StringProtocol.GetRequest strGetRequest = (StringProtocol.GetRequest)
                internalRequest.getRequest();
            String value = storeEngine.strs().get(strGetRequest.getKey());
            StringProtocol.GetResponse.Builder builder = StringProtocol.GetResponse.newBuilder();
            if (value == null) {
              builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
            } else {
              builder.setStatus(CommonProtocol.Status.OK).setValue(value);
            }
            CompletableFuture<StringProtocol.GetResponse> future =
                (CompletableFuture<StringProtocol.GetResponse>)
                    internalRequest.getCompletableFuture();
            future.complete(builder.build());
            break;
          }
          case SET_PUT: {
            SetProtocol.PutRequest setPutRequest =
                (SetProtocol.PutRequest) internalRequest.getRequest();
            // TODO(qwang): Any thoughts on how to avoid this `new HasSet`.
            storeEngine.sets().put(
                setPutRequest.getKey(), new HashSet<>(setPutRequest.getValuesList()));
            CompletableFuture<SetProtocol.PutResponse> future =
                (CompletableFuture<SetProtocol.PutResponse>)
                    internalRequest.getCompletableFuture();
            future.complete(
                SetProtocol.PutResponse.newBuilder().setStatus(CommonProtocol.Status.OK).build());
            break;
          }
          case SET_GET: {
            SetProtocol.GetRequest setGetRequest =
                (SetProtocol.GetRequest) internalRequest.getRequest();
            SetProtocol.GetResponse.Builder setGetResponseBuilder =
                SetProtocol.GetResponse.newBuilder();
            try {
              Set<String> values = storeEngine.sets().get(setGetRequest.getKey());
              values.forEach(value -> setGetResponseBuilder.addValues(value));
              setGetResponseBuilder.setStatus(CommonProtocol.Status.OK);
            } catch (KeyNotFoundException e) {
              setGetResponseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
            } catch (DstException e) {
              setGetResponseBuilder.setStatus(CommonProtocol.Status.UNKNOWN_ERROR);
            }
            CompletableFuture<SetProtocol.GetResponse> future =
                (CompletableFuture<SetProtocol.GetResponse>)
                    internalRequest.getCompletableFuture();
            future.complete(setGetResponseBuilder.build());
            break;
          }
          case SET_PUT_ITEM: {
            SetProtocol.PutItemRequest request =
                (SetProtocol.PutItemRequest) internalRequest.getRequest();
            CommonProtocol.Status status;
            try {
              storeEngine.sets().putItem(request.getKey(), request.getItemValue());
              status = CommonProtocol.Status.OK;
            } catch (KeyNotFoundException e) {
              status = CommonProtocol.Status.KEY_NOT_FOUND;
            }
            CompletableFuture<SetProtocol.PutItemResponse> future =
                (CompletableFuture<SetProtocol.PutItemResponse>)
                    internalRequest.getCompletableFuture();
            future.complete(SetProtocol.PutItemResponse.newBuilder().setStatus(status).build());
            break;
          }
          case SET_REMOVE_ITEM: {
            SetProtocol.RemoveItemRequest request =
                (SetProtocol.RemoveItemRequest) internalRequest.getRequest();
            CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
            try {
              Status localStatus = storeEngine.sets()
                  .removeItem(request.getKey(), request.getItemValue());
              if (localStatus == Status.OK) {
                status = CommonProtocol.Status.OK;
              } else if (localStatus == Status.KEY_NOT_FOUND) {
                status = CommonProtocol.Status.KEY_NOT_FOUND;
              }
            } catch (DstException e) {
              status = CommonProtocol.Status.UNKNOWN_ERROR;
            }
            CompletableFuture<SetProtocol.RemoveItemResponse> future =
                (CompletableFuture<SetProtocol.RemoveItemResponse>)
                    internalRequest.getCompletableFuture();
            future.complete(
                SetProtocol.RemoveItemResponse.newBuilder().setStatus(status).build());
            break;
          }
          case SET_EXIST: {
            SetProtocol.ExistsRequest request =
                (SetProtocol.ExistsRequest) internalRequest.getRequest();
            SetProtocol.ExistsResponse.Builder responseBuilder =
                SetProtocol.ExistsResponse.newBuilder();
            try {
              boolean result = storeEngine.sets().exists(request.getKey(), request.getEntity());
              responseBuilder.setResult(result);
              responseBuilder.setStatus(CommonProtocol.Status.OK);
            } catch (KeyNotFoundException e) {
              responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
            } catch (DstException e) {
              responseBuilder.setStatus(CommonProtocol.Status.UNKNOWN_ERROR);
            }

            CompletableFuture<SetProtocol.ExistsResponse> future =
                (CompletableFuture<SetProtocol.ExistsResponse>)
                    internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          case SET_DROP: {
            CommonProtocol.DropRequest request =
                (CommonProtocol.DropRequest) internalRequest.getRequest();
            CommonProtocol.DropResponse.Builder responseBuilder =
                CommonProtocol.DropResponse.newBuilder();
            CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
            try {
              Status localStatus = storeEngine.sets().drop(request.getKey());
              if (localStatus == Status.OK) {
                status = CommonProtocol.Status.OK;
              } else if (localStatus == Status.KEY_NOT_FOUND) {
                status = CommonProtocol.Status.KEY_NOT_FOUND;
              }
            } catch (DstException e) {
              status = CommonProtocol.Status.UNKNOWN_ERROR;
            }
            responseBuilder.setStatus(status);
            CompletableFuture<CommonProtocol.DropResponse> future =
                (CompletableFuture<CommonProtocol.DropResponse>)
                    internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          case LIST_PUT: {
            ListProtocol.PutRequest request =
                    (ListProtocol.PutRequest) internalRequest.getRequest();
            ListProtocol.PutResponse.Builder responseBuilder =
                    ListProtocol.PutResponse.newBuilder();
            CommonProtocol.Status status = CommonProtocol.Status.OK;
            try {
              Status localStatus =
                      storeEngine.lists().put(request.getKey(), request.getValuesList());
              if (localStatus == Status.OK) {
                status = CommonProtocol.Status.OK;
              }
            } catch (DstException e) {
              LOGGER.error("Failed to put a list to store: {1}", e);
              status = CommonProtocol.Status.UNKNOWN_ERROR;
            }
            responseBuilder.setStatus(status);
            CompletableFuture<ListProtocol.PutResponse> future =
                    (CompletableFuture<ListProtocol.PutResponse>)
                            internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          case LIST_GET: {
            ListProtocol.GetRequest request =
                    (ListProtocol.GetRequest) internalRequest.getRequest();
            ListProtocol.GetResponse.Builder responseBuilder =
                    ListProtocol.GetResponse.newBuilder();
            CommonProtocol.Status status = CommonProtocol.Status.OK;
            final String key = request.getKey();
            final ListProtocol.GetType type = request.getType();
            try {
              if (type == ListProtocol.GetType.GET_ALL) {
                final List<String> values = storeEngine.lists().get(key);
                Optional.ofNullable(values).ifPresent(v -> responseBuilder.addAllValues(values));
              } else if (type == ListProtocol.GetType.GET_ONE) {
                Preconditions.checkState(request.getIndex() >= 0);
                responseBuilder.addValues(storeEngine.lists().get(key, request.getIndex()));
              } else if (type == ListProtocol.GetType.GET_RANGE) {
                final List<String> values = storeEngine.lists().get(
                        key, request.getFrom(), request.getEnd());
                Optional.ofNullable(values).ifPresent(v -> responseBuilder.addAllValues(values));
              } else {
                LOGGER.error("Failed to get a list from store.");
                status = CommonProtocol.Status.UNKNOWN_REQUEST_TYPE;
              }
            } catch (KeyNotFoundException e) {
              LOGGER.info("Failed to get a list from store: {1}", e);
              status = CommonProtocol.Status.KEY_NOT_FOUND;
            } catch (DstListIndexOutOfBoundsException e) {
              LOGGER.info("Failed to get a list from store: {1}", e);
              status = CommonProtocol.Status.LIST_INDEX_OUT_OF_BOUNDS;
            }
            responseBuilder.setStatus(status);
            CompletableFuture<ListProtocol.GetResponse> future =
                    (CompletableFuture<ListProtocol.GetResponse>)
                            internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          case LIST_LPUT: {
            ListProtocol.LPutRequest request =
                    (ListProtocol.LPutRequest) internalRequest.getRequest();
            ListProtocol.LPutResponse.Builder responseBuilder =
                    ListProtocol.LPutResponse.newBuilder();
            CommonProtocol.Status status = CommonProtocol.Status.OK;
            try {
              Status localStatus =
                      storeEngine.lists().lput(request.getKey(), request.getValuesList());
              if (localStatus == Status.OK) {
                status = CommonProtocol.Status.OK;
              } else if (localStatus == Status.KEY_NOT_FOUND) {
                status = CommonProtocol.Status.KEY_NOT_FOUND;
              }
            } catch (DstException e) {
              LOGGER.error("Failed to rput a list to store: {1}", e);
              status = CommonProtocol.Status.UNKNOWN_ERROR;
            }
            responseBuilder.setStatus(status);
            CompletableFuture<ListProtocol.LPutResponse> future =
                    (CompletableFuture<ListProtocol.LPutResponse>)
                            internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          case LIST_RPUT: {
            ListProtocol.RPutRequest request =
                    (ListProtocol.RPutRequest) internalRequest.getRequest();
            ListProtocol.RPutResponse.Builder responseBuilder =
                    ListProtocol.RPutResponse.newBuilder();
            CommonProtocol.Status status = CommonProtocol.Status.OK;
            try {
              Status localStatus =
                      storeEngine.lists().rput(request.getKey(), request.getValuesList());
              if (localStatus == Status.OK) {
                status = CommonProtocol.Status.OK;
              } else if (localStatus == Status.KEY_NOT_FOUND) {
                status = CommonProtocol.Status.KEY_NOT_FOUND;
              }
            } catch (DstException e) {
              status = CommonProtocol.Status.UNKNOWN_ERROR;
            }
            responseBuilder.setStatus(status);
            CompletableFuture<ListProtocol.RPutResponse> future =
                    (CompletableFuture<ListProtocol.RPutResponse>)
                            internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          case LIST_DROP: {
            CommonProtocol.DropRequest request =
                    (CommonProtocol.DropRequest) internalRequest.getRequest();
            CommonProtocol.DropResponse.Builder responseBuilder =
                    CommonProtocol.DropResponse.newBuilder();
            CommonProtocol.Status status = CommonProtocol.Status.OK;
            try {
              Status localStatus = storeEngine.lists().drop(request.getKey());
              if (localStatus == Status.OK) {
                status = CommonProtocol.Status.OK;
              } else if (localStatus == Status.KEY_NOT_FOUND) {
                status = CommonProtocol.Status.KEY_NOT_FOUND;
              }
            } catch (DstException e) {
              status = CommonProtocol.Status.UNKNOWN_ERROR;
            }
            responseBuilder.setStatus(status);
            CompletableFuture<CommonProtocol.DropResponse> future =
                    (CompletableFuture<CommonProtocol.DropResponse>)
                            internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          case LIST_M_REMOVE: {
            ListProtocol.MRemoveRequest request =
                    (ListProtocol.MRemoveRequest) internalRequest.getRequest();
            ListProtocol.MRemoveResponse.Builder responseBuilder =
                    ListProtocol.MRemoveResponse.newBuilder();
            CommonProtocol.Status status = CommonProtocol.Status.OK;
            try {
              Status localStatus =
                      storeEngine.lists().mremove(request.getKey(), request.getIndexesList());
              if (localStatus == Status.OK) {
                status = CommonProtocol.Status.OK;
              } else if (localStatus == Status.KEY_NOT_FOUND) {
                status = CommonProtocol.Status.KEY_NOT_FOUND;
              }
            } catch (KeyNotFoundException e) {
              LOGGER.info("Failed to mRemove item from store: {1}", e);
              status = CommonProtocol.Status.KEY_NOT_FOUND;
            } catch (DstListIndexOutOfBoundsException e) {
              LOGGER.info("Failed to mRemove item from store: {1}", e);
              status = CommonProtocol.Status.LIST_INDEX_OUT_OF_BOUNDS;
            }
            responseBuilder.setStatus(status);
            CompletableFuture<ListProtocol.MRemoveResponse> future =
                    (CompletableFuture<ListProtocol.MRemoveResponse>)
                            internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          case LIST_REMOVE: {
            ListProtocol.RemoveRequest request =
                    (ListProtocol.RemoveRequest) internalRequest.getRequest();
            ListProtocol.RemoveResponse.Builder responseBuilder =
                    ListProtocol.RemoveResponse.newBuilder();
            CommonProtocol.Status status = CommonProtocol.Status.OK;
            final String key = request.getKey();
            final ListProtocol.RemoveType type = request.getType();
            try {
              Status localStatus = null;
              if (type == ListProtocol.RemoveType.RemoveOne) {
                localStatus = storeEngine.lists().remove(key, request.getIndex());
                if (localStatus == Status.OK) {
                  status = CommonProtocol.Status.OK;
                } else if (localStatus == Status.KEY_NOT_FOUND) {
                  status = CommonProtocol.Status.KEY_NOT_FOUND;
                }
              } else if (type == ListProtocol.RemoveType.RemoveRange) {
                localStatus = storeEngine.lists().remove(key, request.getFrom(), request.getEnd());
              }
              if (localStatus == Status.OK) {
                status = CommonProtocol.Status.OK;
              } else if (localStatus == Status.KEY_NOT_FOUND) {
                status = CommonProtocol.Status.KEY_NOT_FOUND;
              }
            } catch (KeyNotFoundException e) {
              LOGGER.info("Failed to remove item from store: {1}", e);
              status = CommonProtocol.Status.KEY_NOT_FOUND;
            } catch (DstListIndexOutOfBoundsException e) {
              LOGGER.info("Failed to remove item from store: {1}", e);
              status = CommonProtocol.Status.LIST_INDEX_OUT_OF_BOUNDS;
            }
            responseBuilder.setStatus(status);
            CompletableFuture<ListProtocol.RemoveResponse> future =
                    (CompletableFuture<ListProtocol.RemoveResponse> )
                            internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          default: {
          }
        }
      } catch (Exception e) {
        LOGGER.error("Failed to execute event loop:" + e);
      }
    }
  }

}
