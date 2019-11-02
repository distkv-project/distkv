package org.dst.rpc.transport.api.async.facade;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.dst.rpc.transport.api.async.DefaultAsyncResponse;

/**
 * @author zrj CreateDate: 2019/11/1
 */
public class ResponseFutureFacade implements Future {

  private DefaultAsyncResponse asyncResponse;

  public ResponseFutureFacade(DefaultAsyncResponse asyncResponse) {
    this.asyncResponse = asyncResponse;
  }

  @Override
  public boolean cancel(boolean mayInterruptIfRunning) {
    return asyncResponse.cancel(mayInterruptIfRunning);
  }

  @Override
  public boolean isCancelled() {
    return asyncResponse.isCancelled();
  }

  @Override
  public boolean isDone() {
    return asyncResponse.isDone();
  }

  @Override
  public Object get() throws InterruptedException, ExecutionException {
    return asyncResponse.get();
  }

  @Override
  public Object get(long timeout, TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    return asyncResponse.get(timeout, unit);
  }
}
