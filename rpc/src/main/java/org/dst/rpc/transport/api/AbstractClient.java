package org.dst.rpc.transport.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dst.rpc.core.common.URL;
import org.dst.rpc.core.config.ParamConstants;
import org.dst.rpc.transport.api.async.Response;

/**
 * 客户端，保存了当前已经发送的所有请求，并保存了返回的Future
 */
public abstract class AbstractClient extends AbstractEndpoint implements Client {

  private Map<Long, Response> currentTask = new ConcurrentHashMap<>();

  public AbstractClient(URL serverUrl) {
    super(serverUrl);
  }

  protected Response getResponseFuture(long requestId) {
    return currentTask.remove(requestId);
  }

  protected void addCurrentTask(long requestId, Response response) {
    currentTask.putIfAbsent(requestId, response);
  }

  @Override
  public boolean isAsync() {
    return getUrl().getBoolean(ParamConstants.isAsync, true);
  }
}
