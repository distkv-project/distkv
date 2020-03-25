package com.distkv.asyncclient;

import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class DistkvAbstractAsyncProxy implements DistkvService {

  private static final String DKV_NAMESPACE_PREFIX = "DKV_NSP";

  private List<Function<DistkvRequest, DistkvRequest>> filters = new ArrayList<>();

  /// The rpc service for this proxy.
  private DistkvService service;

  public DistkvAbstractAsyncProxy(DistkvAsyncClient client, DistkvService service) {
    this.service = service;

    // Append the namespace filter to filters.
    appendFilter((DistkvRequest request) -> {
      if (client.getActivedNamespace() == null) {
        return request;
      }

      // If namespace actived, Add the namespace prefix to the key.
      final String key = String.format("%s_%s_%s",
          DKV_NAMESPACE_PREFIX, client.getActivedNamespace(), request.getKey());
      request = DistkvRequest
          .newBuilder()
          .setKey(key)
          .setRequestType(request.getRequestType())
          .setRequest(request.getRequest())
          .build();
      return request;
    });
  }

  /// Append a filter to this proxy.
  private void appendFilter(Function<DistkvRequest, DistkvRequest> filter) {
    filters.add(filter);
  }

  /**
   * call rpc service.
   */
  public CompletableFuture<DistkvResponse> call(DistkvRequest request) {
    DistkvRequest localRequest = request;
    for (Function<DistkvRequest, DistkvRequest> filter : filters) {
      localRequest = filter.apply(localRequest);
    }
    return service.call(localRequest);
  }


  protected CompletableFuture<DistkvResponse> get(String key, RequestType type) {
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(type)
        .build();
    return call(request);
  }

  protected CompletableFuture<DistkvResponse> put(
      String key, RequestType type, Any requestBody) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(type)
        .setRequest(requestBody)
        .build();
    return call(request);
  }

}
