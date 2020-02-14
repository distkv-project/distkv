package com.distkv.asyncclient;

import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.service.DistkvService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class DistkvAbstractAsyncProxy implements DistkvService {

  private List<Function<DistkvProtocol.DistkvRequest,
      DistkvProtocol.DistkvRequest>> filters = new ArrayList<>();

  private DistkvService service;

  public DistkvAbstractAsyncProxy(DistkvAsyncClient client, DistkvService service) {
    this.service = service;

    // The namespace filter.
    appendFilter((DistkvProtocol.DistkvRequest request) -> {
      if (client.getActivedNamespace() == null) {
        return request;
      }

      final String key = String.format(
          "DKV_NSP_%s_%s", client.getActivedNamespace(), request.getKey());
      request = DistkvProtocol.DistkvRequest
          .newBuilder()
          .setKey(key)
          .setRequestType(request.getRequestType())
          .setRequest(request.getRequest())
          .build();
      return request;
    });
  }

  private void appendFilter(
      Function<DistkvProtocol.DistkvRequest, DistkvProtocol.DistkvRequest> filter) {
    filters.add(filter);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> call(
      DistkvProtocol.DistkvRequest request) {
    DistkvProtocol.DistkvRequest localRequest = request;
    for (Function<DistkvProtocol.DistkvRequest, DistkvProtocol.DistkvRequest> filter : filters) {
      localRequest = filter.apply(localRequest);
    }
    return service.call(localRequest);
  }


}
