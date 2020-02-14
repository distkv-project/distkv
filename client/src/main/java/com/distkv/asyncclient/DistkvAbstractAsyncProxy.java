package com.distkv.asyncclient;

import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.service.DistkvService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class DistkvAbstractAsyncProxy implements DistkvService {

  private final static String DKV_NAMESPACE_PREFIX = "DKV_NSP";

  private List<Function<DistkvProtocol.DistkvRequest,
      DistkvProtocol.DistkvRequest>> filters = new ArrayList<>();

  /// The rpc service for this proxy.
  private DistkvService service;

  public DistkvAbstractAsyncProxy(DistkvAsyncClient client, DistkvService service) {
    this.service = service;

    // Append the namespace filter to filters.
    appendFilter((DistkvProtocol.DistkvRequest request) -> {
      if (client.getActivedNamespace() == null) {
        return request;
      }

      // If namespace actived, Add the namespace prefix to the key.
      final String key = String.format("%s_%s_%s",
          DKV_NAMESPACE_PREFIX, client.getActivedNamespace(), request.getKey());
      request = DistkvProtocol.DistkvRequest
          .newBuilder()
          .setKey(key)
          .setRequestType(request.getRequestType())
          .setRequest(request.getRequest())
          .build();
      return request;
    });
  }

  /// Append a filter to this proxy.
  private void appendFilter(
      Function<DistkvProtocol.DistkvRequest, DistkvProtocol.DistkvRequest> filter) {
    filters.add(filter);
  }

  /**
   * call rpc service.
   */
  public CompletableFuture<DistkvProtocol.DistkvResponse> call(
      DistkvProtocol.DistkvRequest request) {
    DistkvProtocol.DistkvRequest localRequest = request;
    for (Function<DistkvProtocol.DistkvRequest, DistkvProtocol.DistkvRequest> filter : filters) {
      localRequest = filter.apply(localRequest);
    }
    return service.call(localRequest);
  }


}
