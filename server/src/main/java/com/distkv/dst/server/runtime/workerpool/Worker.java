package com.distkv.dst.server.runtime.workerpool;

import com.distkv.dst.common.NodeInfo;
import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.utils.Status;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.core.KVStoreImpl;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
          case DICT_PUT: {
            DictProtocol.PutRequest request =
                  (DictProtocol.PutRequest) internalRequest.getRequest();
            DictProtocol.PutResponse.Builder responseBuilder =
                  DictProtocol.PutResponse.newBuilder();
            CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
            try {
              final Map<String, String> map = new HashMap<>();
              DictProtocol.DstDict dstDict = request.getDict();
              for (int i = 0; i < dstDict.getKeysCount(); i++) {
                map.put(dstDict.getKeys(i), dstDict.getValues(i));
              }
              storeEngine.dicts().put(request.getKey(), map);
              responseBuilder.setStatus(CommonProtocol.Status.OK);
            } catch (Exception e) {
              // TODO(qwang): Use DstException instead of Exception here.
              responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
            }
            responseBuilder.setStatus(status);
            CompletableFuture<DictProtocol.PutResponse> future =
                  (CompletableFuture<DictProtocol.PutResponse>)
                        internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          case DICT_GET: {
            DictProtocol.GetRequest request =
                  (DictProtocol.GetRequest) internalRequest.getRequest();
            DictProtocol.GetResponse.Builder responseBuilder =
                  DictProtocol.GetResponse.newBuilder();
            responseBuilder.setStatus(CommonProtocol.Status.OK);
            final Map<String, String> dict = storeEngine.dicts().get(request.getKey());
            if (dict == null) {
              responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
            } else {
              DictProtocol.DstDict.Builder builder = DictProtocol.DstDict.newBuilder();
              for (Map.Entry<String, String> entry : dict.entrySet()) {
                builder.addKeys(entry.getKey());
                builder.addValues(entry.getValue());
              }
              responseBuilder.setDict(builder.build());
            }
            CompletableFuture<DictProtocol.GetResponse> future =
                  (CompletableFuture<DictProtocol.GetResponse>)
                        internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          case DICT_GET_ITEM: {
            DictProtocol.GetItemRequest request =
                  (DictProtocol.GetItemRequest) internalRequest.getRequest();
            DictProtocol.GetItemResponse.Builder responseBuilder =
                  DictProtocol.GetItemResponse.newBuilder();
            CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
            final Map<String, String> dict = storeEngine.dicts().get(request.getKey());
            responseBuilder.setStatus(CommonProtocol.Status.OK);
            if (dict == null) {
              responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
            } else {
              final String itemValue = dict.get(request.getItemKey());
              if (itemValue == null) {
                responseBuilder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
              } else {
                responseBuilder.setItemValue(itemValue);
              }
            }
            CompletableFuture<DictProtocol.GetItemResponse> future =
                  (CompletableFuture<DictProtocol.GetItemResponse>)
                        internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          case DICT_POP_ITEM: {
            DictProtocol.PopItemRequest request =
                  (DictProtocol.PopItemRequest) internalRequest.getRequest();
            DictProtocol.PopItemResponse.Builder responseBuilder =
                  DictProtocol.PopItemResponse.newBuilder();
            responseBuilder.setStatus(CommonProtocol.Status.OK);
            final Map<String, String> dict = storeEngine.dicts().get(request.getKey());
            if (dict == null) {
              responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
            } else {
              final String itemValue = dict.remove(request.getItemKey());
              if (itemValue == null) {
                responseBuilder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
              } else {
                responseBuilder.setItemValue(itemValue);
              }
            }
            CompletableFuture<DictProtocol.PopItemResponse> future =
                  (CompletableFuture<DictProtocol.PopItemResponse>)
                        internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          case DICT_PUT_ITEM: {
            DictProtocol.PutItemRequest request =
                  (DictProtocol.PutItemRequest) internalRequest.getRequest();
            DictProtocol.PutItemResponse.Builder responseBuilder =
                  DictProtocol.PutItemResponse.newBuilder();
            responseBuilder.setStatus(CommonProtocol.Status.OK);
            final Map<String, String> dict = storeEngine.dicts().get(request.getKey());
            if (dict == null) {
              responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
            } else {
              dict.put(request.getItemKey(), request.getItemValue());
            }
            CompletableFuture<DictProtocol.PutItemResponse> future =
                  (CompletableFuture<DictProtocol.PutItemResponse>)
                        internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          case DICT_REMOVE_ITEM: {
            DictProtocol.RemoveItemRequest request =
                  (DictProtocol.RemoveItemRequest) internalRequest.getRequest();
            DictProtocol.RemoveItemResponse.Builder responseBuilder =
                  DictProtocol.RemoveItemResponse.newBuilder();
            responseBuilder.setStatus(CommonProtocol.Status.OK);
            final Map<String, String> dict = storeEngine.dicts().get(request.getKey());
            if (dict == null) {
              responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
            } else {
              final String itemValue = dict.remove(request.getItemKey());
              if (itemValue == null) {
                responseBuilder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
              }
              dict.remove(request.getItemKey());
            }
            CompletableFuture<DictProtocol.RemoveItemResponse> future =
                  (CompletableFuture<DictProtocol.RemoveItemResponse>)
                        internalRequest.getCompletableFuture();
            future.complete(responseBuilder.build());
            break;
          }
          case DICT_DROP: {
            CommonProtocol.DropRequest request =
                  (CommonProtocol.DropRequest) internalRequest.getRequest();
            CommonProtocol.DropResponse.Builder responseBuilder =
                  CommonProtocol.DropResponse.newBuilder();
            responseBuilder.setStatus(CommonProtocol.Status.OK);
            if (!storeEngine.dicts().drop(request.getKey())) {
              responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
            }
            CompletableFuture<CommonProtocol.DropResponse> future =
                  (CompletableFuture<CommonProtocol.DropResponse>)
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
