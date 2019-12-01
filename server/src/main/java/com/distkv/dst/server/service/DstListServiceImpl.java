package com.distkv.dst.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import com.google.common.base.Preconditions;
import com.distkv.dst.common.exception.DstListIndexOutOfBoundsException;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.ListProtocol;
import com.distkv.dst.rpc.service.DstListService;
import com.distkv.dst.server.base.DstBaseService;
import com.distkv.dst.common.utils.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstListServiceImpl extends DstBaseService implements DstListService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstListServiceImpl.class);

  public DstListServiceImpl(KVStore store) {
    super(store);
  }

  @Override
  public CompletableFuture<ListProtocol.PutResponse> put(ListProtocol.PutRequest request) {
    ListProtocol.PutResponse.Builder responseBuilder =
            ListProtocol.PutResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      getStore().lists().put(request.getKey(),new ArrayList<>(request.getValuesList()));
      status = CommonProtocol.Status.OK;
    } catch (DstException e) {
      LOGGER.error("Failed to put a list to store: {1}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    CompletableFuture<ListProtocol.PutResponse> responseFuture = new CompletableFuture<>();
    responseFuture.complete(responseBuilder.build());
    return responseFuture;
  }

  @Override
  public CompletableFuture<ListProtocol.GetResponse> get(ListProtocol.GetRequest request) {
    ListProtocol.GetResponse.Builder responseBuilder =
            ListProtocol.GetResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.OK;

    final String key = request.getKey();
    final ListProtocol.GetType type = request.getType();
    try {
      if (type == ListProtocol.GetType.GET_ALL) {
        final List<String> values = getStore().lists().get(key);
        Optional.ofNullable(values).ifPresent(v -> responseBuilder.addAllValues(values));
      } else if (type == ListProtocol.GetType.GET_ONE) {
        Preconditions.checkState(request.getIndex() >= 0);
        responseBuilder.addValues(getStore().lists().get(key, request.getIndex()));
      } else if (type == ListProtocol.GetType.GET_RANGE) {
        final List<String> values = getStore().lists().get(
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
    CompletableFuture<ListProtocol.GetResponse> responseFuture = new CompletableFuture<>();
    responseFuture.complete(responseBuilder.build());
    return responseFuture;
  }

  @Override
  public CompletableFuture<CommonProtocol.DropResponse> drop(CommonProtocol.DropRequest request) {
    CommonProtocol.DropResponse.Builder responseBuilder =
            CommonProtocol.DropResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = getStore().lists().drop(request.getKey());
      if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      } else if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      }
    } catch (DstException e) {
      LOGGER.error("Failed to del a list from store: {1}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    CompletableFuture<CommonProtocol.DropResponse> responseFuture = new CompletableFuture<>();
    responseFuture.complete(responseBuilder.build());
    return responseFuture;
  }

  @Override
  public CompletableFuture<ListProtocol.LPutResponse> lput(ListProtocol.LPutRequest request) {
    ListProtocol.LPutResponse.Builder responseBuilder =
            ListProtocol.LPutResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = getStore().lists().lput(request.getKey(), request.getValuesList());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (DstException e) {
      LOGGER.error("Failed to lput a list to store: {1}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    CompletableFuture<ListProtocol.LPutResponse> responseFuture = new CompletableFuture<>();
    responseFuture.complete(responseBuilder.build());
    return responseFuture;
  }

  @Override
  public CompletableFuture<ListProtocol.RPutResponse> rput(ListProtocol.RPutRequest request) {
    ListProtocol.RPutResponse.Builder responseBuilder =
            ListProtocol.RPutResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = getStore().lists().rput(request.getKey(), request.getValuesList());
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
    CompletableFuture<ListProtocol.RPutResponse> responseFuture = new CompletableFuture<>();
    responseFuture.complete(responseBuilder.build());
    return responseFuture;
  }

  @Override
  public CompletableFuture<ListProtocol.RemoveResponse> remove(ListProtocol.RemoveRequest request) {
    ListProtocol.RemoveResponse.Builder responseBuilder =
            ListProtocol.RemoveResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.OK;

    final String key = request.getKey();
    final ListProtocol.RemoveType type = request.getType();
    try {
      Status localStatus = null;
      if (type == ListProtocol.RemoveType.RemoveOne) {
        localStatus = getStore().lists().remove(key, request.getIndex());
        if (localStatus == Status.OK) {
          status = CommonProtocol.Status.OK;
        } else if (localStatus == Status.KEY_NOT_FOUND) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        }
      } else if (type == ListProtocol.RemoveType.RemoveRange) {
        localStatus = getStore().lists().remove(key, request.getFrom(), request.getEnd());
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
    CompletableFuture<ListProtocol.RemoveResponse> responseFuture = new CompletableFuture<>();
    responseFuture.complete(responseBuilder.build());
    return responseFuture;
  }

  @Override
  public CompletableFuture<ListProtocol.MRemoveResponse> multipleRemove(
      ListProtocol.MRemoveRequest request) {
    ListProtocol.MRemoveResponse.Builder responseBuilder =
            ListProtocol.MRemoveResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus =
              getStore().lists().multipleRemove(request.getKey(), request.getIndexesList());
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
    CompletableFuture<ListProtocol.MRemoveResponse> responseFuture = new CompletableFuture<>();
    responseFuture.complete(responseBuilder.build());
    return responseFuture;
  }
}