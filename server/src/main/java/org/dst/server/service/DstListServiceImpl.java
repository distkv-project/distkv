package org.dst.server.service;

import java.util.List;
import org.dst.core.KVStore;
import org.dst.server.generated.ListProtocol;
import org.dst.utils.enums.Status;

public class DstListServiceImpl implements DstListService {

  private KVStore store;

  public DstListServiceImpl(KVStore kvStore) {
    store = kvStore;
  }

  @Override
  public ListProtocol.ListPutResponse listPut(ListProtocol.ListPutRequest request) {
    ListProtocol.ListPutResponse.Builder responseBuilder =
            ListProtocol.ListPutResponse.newBuilder();
    String result;
    try {
      Status status = store.list().put(request.getKey(), request.getValuesList());
      result = status.toString();
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here.
      result = e.getMessage();
    }
    responseBuilder.setStatus(result);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListGetResponse listGet(ListProtocol.ListGetRequest request) {
    ListProtocol.ListGetResponse.Builder responseBuilder =
            ListProtocol.ListGetResponse.newBuilder();

    List<String> values = store.list().get(request.getKey());
    values.forEach(value -> responseBuilder.addValues(value));

    responseBuilder.setStatus("ok");
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListDelResponse listDel(ListProtocol.ListDelRequest request) {
    ListProtocol.ListDelResponse.Builder responseBuilder =
            ListProtocol.ListDelResponse.newBuilder();
    String result;
    try {
      Status status = store.list().del(request.getKey());
      result = status.toString();
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here.
      result = e.getMessage();
    }
    responseBuilder.setStatus(result);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListLPutResponse listLPut(ListProtocol.ListLPutRequest request) {
    ListProtocol.ListLPutResponse.Builder responseBuilder =
            ListProtocol.ListLPutResponse.newBuilder();
    String result;
    try {
      Status status = store.list().lput(request.getKey(), request.getValuesList());
      result = status.toString();
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here  .
      result = e.getMessage();
    }
    responseBuilder.setStatus(result);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListRPutResponse listRPut(ListProtocol.ListRPutRequest request) {
    ListProtocol.ListRPutResponse.Builder responseBuilder =
            ListProtocol.ListRPutResponse.newBuilder();
    String result;
    try {
      Status status = store.list().rput(request.getKey(), request.getValuesList());
      result = status.toString();
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here .
      result = e.getMessage();
    }
    responseBuilder.setStatus(result);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListLDelResponse listLDel(ListProtocol.ListLDelRequest request) {
    ListProtocol.ListLDelResponse.Builder responseBuilder =
            ListProtocol.ListLDelResponse.newBuilder();
    String result;
    try {
      Status status = store.list().ldel(request.getKey(), request.getValues());
      result = status.toString();
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here .
      result = e.getMessage();
    }
    responseBuilder.setStatus(result);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListRDelResponse listRDel(ListProtocol.ListRDelRequest request) {
    ListProtocol.ListRDelResponse.Builder responseBuilder =
            ListProtocol.ListRDelResponse.newBuilder();
    String result;
    try {
      Status status = store.list().rdel(request.getKey(), request.getValues());
      result = status.toString();
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here .
      result = e.getMessage();
    }
    responseBuilder.setStatus(result);
    return responseBuilder.build();
  }
}
