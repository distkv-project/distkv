package com.distkv.dst.client;

import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.utils.FutureUtils;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.ListProtocol;
import com.distkv.dst.rpc.service.DstListService;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.distkv.dst.client.CheckStatusUtil.checkStatus;

public class DstListProxy {

  private DstListService service;

  public DstListProxy(DstListService service) {
    this.service = service;
  }

  public void put(String key, List<String> values) throws DstException {
    ListProtocol.PutRequest request = ListProtocol.PutRequest.newBuilder()
          .setKey(key)
          .addAllValues(values)
          .build();
    ListProtocol.PutResponse response = FutureUtils.get(service.put(request));
    checkStatus(response.getStatus(), request.getKey());
  }

  public CompletableFuture<ListProtocol.PutResponse> asyncPut(
          String key, List<String> values) {
    ListProtocol.PutRequest request = ListProtocol.PutRequest.newBuilder()
            .setKey(key)
            .addAllValues(values)
            .build();
    CompletableFuture<ListProtocol.PutResponse> future = service.put(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public List<String> get(String key) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
        .setType(ListProtocol.GetType.GET_ALL)
        .setKey(key)
        .build();
    ListProtocol.GetResponse response = FutureUtils.get(service.get(request));
    checkStatus(response.getStatus(), request.getKey());
    return response.getValuesList();
  }

  public List<String> get(String key, Integer index) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
            .setType(ListProtocol.GetType.GET_ONE)
            .setKey(key)
            .setIndex(index)
            .build();
    ListProtocol.GetResponse response = FutureUtils.get(service.get(request));
    checkStatus(response.getStatus(), request.getKey());
    return response.getValuesList();
  }


  public List<String> get(String key, Integer from, Integer end) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
            .setType(ListProtocol.GetType.GET_RANGE)
            .setKey(key)
            .setFrom(from)
            .setEnd(end)
            .build();
    ListProtocol.GetResponse response = FutureUtils.get(service.get(request));
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d",response.getStatus().getNumber()));
    }
    return response.getValuesList();
  }

  public CompletableFuture<ListProtocol.GetResponse> asyncGet(String key) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
            .setType(ListProtocol.GetType.GET_ALL)
            .setKey(key)
            .build();
    CompletableFuture<ListProtocol.GetResponse> future = service.get(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public CompletableFuture<ListProtocol.GetResponse> asyncGet(
          String key, Integer index) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
            .setType(ListProtocol.GetType.GET_ONE)
            .setKey(key)
            .setIndex(index)
            .build();
    CompletableFuture<ListProtocol.GetResponse> future = service.get(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public CompletableFuture<ListProtocol.GetResponse> asyncGet(
          String key, Integer from, Integer end) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
            .setType(ListProtocol.GetType.GET_RANGE)
            .setKey(key)
            .setFrom(from)
            .setEnd(end)
            .build();
    CompletableFuture<ListProtocol.GetResponse> future = service.get(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public void drop(String key) {
    CommonProtocol.DropRequest request = CommonProtocol.DropRequest.newBuilder()
          .setKey(key)
          .build();
    CommonProtocol.DropResponse response = FutureUtils.get(service.drop(request));
    checkStatus(response.getStatus(), request.getKey());
  }

  public CompletableFuture<CommonProtocol.DropResponse> asyncDrop(String key) {
    CommonProtocol.DropRequest request = CommonProtocol.DropRequest.newBuilder()
            .setKey(key)
            .build();
    CompletableFuture<CommonProtocol.DropResponse> future = service.drop(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public void lput(String key, List<String> values) {
    ListProtocol.LPutRequest request = ListProtocol.LPutRequest.newBuilder()
          .setKey(key)
          .addAllValues(values)
          .build();
    ListProtocol.LPutResponse response = FutureUtils.get(service.lput(request));
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public CompletableFuture<ListProtocol.LPutResponse> asyncLput(
          String key, List<String> values) {
    ListProtocol.LPutRequest request = ListProtocol.LPutRequest.newBuilder()
            .setKey(key)
            .addAllValues(values)
            .build();
    CompletableFuture<ListProtocol.LPutResponse> future = service.lput(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public void rput(String key, List<String> values) {
    ListProtocol.RPutRequest request = ListProtocol.RPutRequest.newBuilder()
          .setKey(key)
          .addAllValues(values)
          .build();
    ListProtocol.RPutResponse response = FutureUtils.get(service.rput(request));
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public CompletableFuture<ListProtocol.RPutResponse> asyncRput(
          String key, List<String> values) {
    ListProtocol.RPutRequest request = ListProtocol.RPutRequest.newBuilder()
            .setKey(key)
            .addAllValues(values)
            .build();
    CompletableFuture<ListProtocol.RPutResponse> future = service.rput(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public void remove(String key, Integer index) {
    ListProtocol.RemoveRequest request = ListProtocol.RemoveRequest.newBuilder()
            .setType(ListProtocol.RemoveType.RemoveOne)
            .setKey(key)
            .setIndex(index)
            .build();
    ListProtocol.RemoveResponse response = FutureUtils.get(service.remove(request));
    checkStatus(response.getStatus(), request.getKey());
  }

  public void remove(String key, Integer from, Integer end) {
    ListProtocol.RemoveRequest request = ListProtocol.RemoveRequest.newBuilder()
            .setType(ListProtocol.RemoveType.RemoveRange)
            .setKey(key)
            .setFrom(from)
            .setEnd(end)
            .build();
    ListProtocol.RemoveResponse response = FutureUtils.get(service.remove(request));
    checkStatus(response.getStatus(), request.getKey());
  }

  public CompletableFuture<ListProtocol.RemoveResponse> asyncRemove(
          String key, Integer index) {
    ListProtocol.RemoveRequest request = ListProtocol.RemoveRequest.newBuilder()
            .setType(ListProtocol.RemoveType.RemoveOne)
            .setKey(key)
            .setIndex(index)
            .build();
    CompletableFuture<ListProtocol.RemoveResponse> future = service.remove(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public CompletableFuture<ListProtocol.RemoveResponse> asyncRemove(
          String key, Integer from, Integer end) {
    ListProtocol.RemoveRequest request = ListProtocol.RemoveRequest.newBuilder()
            .setType(ListProtocol.RemoveType.RemoveRange)
            .setKey(key)
            .setFrom(from)
            .setEnd(end)
            .build();
    CompletableFuture<ListProtocol.RemoveResponse> future = service.remove(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public void multipleRemove(String key, List<Integer> indexes) {
    ListProtocol.MRemoveRequest request = ListProtocol.MRemoveRequest.newBuilder()
          .setKey(key)
          .addAllIndexes(indexes)
          .build();
    ListProtocol.MRemoveResponse response = FutureUtils.get(service.multipleRemove(request));
    checkStatus(response.getStatus(), request.getKey());
  }

  public CompletableFuture<ListProtocol.MRemoveResponse> asyncMutiRemove(
          String key, List<Integer> indexes) {
    ListProtocol.MRemoveRequest request = ListProtocol.MRemoveRequest.newBuilder()
            .setKey(key)
            .addAllIndexes(indexes)
            .build();
    CompletableFuture<ListProtocol.MRemoveResponse> future = service.multipleRemove(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }
}
