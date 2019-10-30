package org.dst.rpc.transport.api.async;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.dst.rpc.core.exception.DstException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zrj CreateDate: 2019/10/29
 */
public class DefaultAsyncResponse extends CompletableFuture<Response> implements AsyncResponse {

  private static final Logger logger = LoggerFactory.getLogger(DefaultAsyncResponse.class);

  /**
   * 用来暂存CompletableFuture中的value，方便一些不触发complete的set操作，例如setRequestId。
   */
  private Response response;

  public DefaultAsyncResponse() {
    this.response = new DefaultResponse();
  }

  public DefaultAsyncResponse(long requestId) {
    this.response = new DefaultResponse(requestId);
  }

  @Override
  public long getRequestId() {
    return response.getRequestId();
  }

  @Override
  public void setRequestId(long requestId) {
    response.setRequestId(requestId);
  }


  /**
   * 调用之前需要检查isDone否则会抛出异常
   */
  @Override
  public Object getValue() {
    return getDefaultResponse().getValue();
  }


  /**
   * 只有setValue和setException会触发complete操作
   *
   * @see {@link DefaultAsyncResponse#setException(Exception)}
   */
  @Override
  public void setValue(Object value) {
    try {
      if (this.isDone()) {
        this.get().setValue(value);
      } else {
        response.setValue(value);
        super.complete(response);
      }
    } catch (Exception e) {
      // This should not happen
      throw new DstException(e);
    }
  }


  /**
   * 调用之前需要检查isDone否则会抛出异常
   */
  @Override
  public Exception getException() {
    return getDefaultResponse().getException();
  }


  /**
   * 只有setValue和setException会触发complete操作
   *
   * @see {@link DefaultAsyncResponse#setValue(Object)}
   */
  @Override
  public void setException(Exception exception) {
    try {
      if (this.isDone()) {
        this.get().setException(exception);
      } else {
        response.setException(exception);
        super.complete(response);
      }
    } catch (Exception e) {
      // This should not happen
      throw new DstException(e);
    }
  }

  @Override
  public boolean hasAttribute(String key) {
    return response.hasAttribute(key);
  }

  @Override
  public Object getAttribute(String key) {
    return response.getAttribute(key);
  }

  @Override
  public void setAttribute(String key, Object value) {
    response.setAttribute(key, value);
  }

  @Override
  public void removeAttribute(String key) {
    response.removeAttribute(key);
  }

  @Override
  public void await() throws InterruptedException, ExecutionException {
    get();
  }

  @Override
  public void await(long timeout, TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    get(timeout, unit);
  }

  @Override
  public boolean complete(Response value) {
    // 封印这个方法
    throw new UnsupportedOperationException();
  }

  private void checkDone() {
    if (!isDone()) {
      throw new DstException("Must check 'isDone()' first");
    }
  }

  /**
   * AsyncResponse 异步Response 操作必须先检查isDone
   */
  public boolean isDone() {
    return super.isDone();
  }

  private Response getDefaultResponse() {
    checkDone();
    try {
      return get();
    } catch (Exception e) {
      // This should not happen
      throw new DstException(e);
    }
  }


}
