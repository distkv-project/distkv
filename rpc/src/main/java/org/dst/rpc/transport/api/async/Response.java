package org.dst.rpc.transport.api.async;


/**
 * fixme : 如果异步实现需要将Response暴露给业务方的话，需要考虑支持泛型。
 * @author zrj CreateDate: 2019/10/29
 */
public interface Response {

  long getRequestId();

  void setRequestId(long requestId);

  Object getValue();

  void setValue(Object value);

  Exception getException();

  void setException(Exception e);

  /**
   * has attribute.
   *
   * @param key key.
   * @return has or has not.
   */
  boolean hasAttribute(String key);

  /**
   * get attribute.
   *
   * @param key key.
   * @return value.
   */
  Object getAttribute(String key);

  /**
   * set attribute.
   *
   * @param key key.
   * @param value value.
   */
  void setAttribute(String key, Object value);

  /**
   * remove attribute.
   *
   * @param key key.
   */
  void removeAttribute(String key);

}
