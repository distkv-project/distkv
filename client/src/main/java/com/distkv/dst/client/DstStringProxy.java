package com.distkv.dst.client;

import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import com.distkv.dst.rpc.service.DstStringService;
import com.google.common.base.Preconditions;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DstStringProxy {

  private DstStringService service;

  public DstStringProxy(DstStringService service) {
    this.service = service;
  }

  public void put(String key, String value) {
    StringProtocol.PutRequest request =
        StringProtocol.PutRequest.newBuilder()
            .setKey(key)
            .setValue(value)
            .build();

    CompletableFuture<StringProtocol.PutResponse> responseFuture = service.put(request);
    StringProtocol.PutResponse response = null;
    try {
      response = responseFuture.get();
    } catch (ExecutionException e) {
      // TODO(qwang): How to handle this exception?
    } catch (InterruptedException e) {
      // TODO(qwang): How to handle this exception?
    }
    Preconditions.checkNotNull(response);

    if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public String get(String key) throws DstException {
    StringProtocol.GetRequest request =
        StringProtocol.GetRequest.newBuilder()
            .setKey(key)
            .build();

    CompletableFuture<StringProtocol.GetResponse> responseFuture = service.get(request);
    StringProtocol.GetResponse response = null;
    try {
      response = responseFuture.get();
    } catch (InterruptedException e) {
      // TODO(qwang): How to handle this exception?
    } catch (ExecutionException e) {
      // TODO(qwang): How to handle this exception?
    }
    Preconditions.checkNotNull(response);

    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }

    return response.getValue();
  }

}
