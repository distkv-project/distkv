package org.dst.server.service;

import java.util.List;
import java.util.Optional;

import org.dst.core.KVStore;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.ListProtocol;
import org.dst.utils.Status;

public class DstListServiceImpl implements DstListService {

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

    List<String> values = store.lists().get(request.getKey());
    // TODO(tansen) change protocol
    Optional.ofNullable(values)
            .ifPresent(v -> {
              values.forEach(value -> responseBuilder.addValues(value));
            });

    responseBuilder.setStatus(CommonProtocol.Status.OK);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListDelResponse listDel(ListProtocol.ListDelRequest request) {
    ListProtocol.ListDelResponse.Builder responseBuilder =
            ListProtocol.ListDelResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = store.lists().del(request.getKey());
      if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      } else if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      }
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here.
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListLPutResponse listLPut(ListProtocol.ListLPutRequest request) {
    ListProtocol.ListLPutResponse.Builder responseBuilder =
            ListProtocol.ListLPutResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = store.lists().lput(request.getKey(), request.getValuesList());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here  .
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListRPutResponse listRPut(ListProtocol.ListRPutRequest request) {
    ListProtocol.ListRPutResponse.Builder responseBuilder =
            ListProtocol.ListRPutResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = store.lists().rput(request.getKey(), request.getValuesList());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here .
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListLDelResponse listLDel(ListProtocol.ListLDelRequest request) {
    ListProtocol.ListLDelResponse.Builder responseBuilder =
            ListProtocol.ListLDelResponse.newBuilder();
    String result;
    try {
      Status status = store.lists().ldel(request.getKey(), request.getValues());
      result = status.toString();
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here .
      result = e.getMessage();
    }
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListRDelResponse listRDel(ListProtocol.ListRDelRequest request) {
    ListProtocol.ListRDelResponse.Builder responseBuilder =
            ListProtocol.ListRDelResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = store.lists().rdel(request.getKey(), request.getValues());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }

    } catch (Exception e) {
      // TODO(qwang): Add error message to protobuf.
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }
}
