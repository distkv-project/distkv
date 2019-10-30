package org.dst.rpc.transport.api;


/**
 * 传输层获得数据后，交给具体的处理方法
 */
public interface Handler {

  /**
   * 标志这个Handler服务的服务
   */
  String getServerName();

  /**
   * 处理接口
   */
  Object handle(Object message);

}
