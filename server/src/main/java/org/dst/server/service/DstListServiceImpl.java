package org.dst.server.service;

import java.util.List;
import java.util.Optional;

import org.dst.core.KVStore;
import org.dst.exception.KeyNotFoundException;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.ListProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstListServiceImpl implements DstListService {

  private static Logger logger = LoggerFactory.getLogger(DstListServiceImpl.class);

  private KVStore store;

  public DstListServiceImpl(KVStore kvStore) {
    store = kvStore;
  }

  @Override
  public ListProtocol.ListPutResponse listPut(ListProtocol.ListPutRequest request) {
    ListProtocol.ListPutResponse.Builder responseBuilder =
            ListProtocol.ListPutResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      store.lists().put(request.getKey(), request.getValuesList());
      status = CommonProtocol.Status.OK;
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here.
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListGetResponse listGet(ListProtocol.ListGetRequest request) {
    ListProtocol.ListGetResponse.Builder responseBuilder =
            ListProtocol.ListGetResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      List<String> values = store.lists().get(request.getKey());
      Optional.ofNullable(values)
              .ifPresent(v -> {
                values.forEach(value -> responseBuilder.addValues(value));
              });

      responseBuilder.setStatus(CommonProtocol.Status.OK);
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here.
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListDelResponse listDel(ListProtocol.ListDelRequest request) {
    ListProtocol.ListDelResponse.Builder responseBuilder =
            ListProtocol.ListDelResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      store.lists().del(request.getKey());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      logger.error(e.getMessage());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (Exception e) {
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListLPutResponse listLPut(ListProtocol.ListLPutRequest request) {
    ListProtocol.ListLPutResponse.Builder responseBuilder =
            ListProtocol.ListLPutResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      store.lists().lput(request.getKey(), request.getValuesList());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      logger.error(e.getMessage());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (Exception e) {
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListRPutResponse listRPut(ListProtocol.ListRPutRequest request) {
    ListProtocol.ListRPutResponse.Builder responseBuilder =
            ListProtocol.ListRPutResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      store.lists().rput(request.getKey(), request.getValuesList());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      logger.error(e.getMessage());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (Exception e) {
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListLDelResponse listLDel(ListProtocol.ListLDelRequest request) {
    ListProtocol.ListLDelResponse.Builder responseBuilder =
            ListProtocol.ListLDelResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      store.lists().ldel(request.getKey(), request.getValues());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      logger.error(e.getMessage());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (Exception e) {
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListRDelResponse listRDel(ListProtocol.ListRDelRequest request) {
    ListProtocol.ListRDelResponse.Builder responseBuilder =
            ListProtocol.ListRDelResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      store.lists().rdel(request.getKey(), request.getValues());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      logger.error(e.getMessage());
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (Exception e) {
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }
}
