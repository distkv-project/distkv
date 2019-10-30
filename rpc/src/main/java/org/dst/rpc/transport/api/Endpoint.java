package org.dst.rpc.transport.api;

import org.dst.rpc.core.common.URL;

/**
 * 表示一个网络连接终端。
 *
 * 这里经过再三思考，觉得Endpoint和Channel的概念是需要分开的。 Endpoint代表一个可以建立连接的实体，Channel代表建立连接之后的信道，等同于connection.
 *
 * 所以Endpoint和Channel的关系是has-a关系而不是is-a关系。
 */
public interface Endpoint {

  /**
   * 该传输层的设置
   */
  URL getUrl();

  boolean isConnected();

  /**
   * 建立连接
   *
   * @return 建立连接之后的信道。
   */
  void init();

  /**
   * 打开建立的通信信道
   *
   * @return 如果isConnected是false，这里返回null
   */
  Channel getChannel();

  /**
   * 断开连接
   */
  void destroy();

}
