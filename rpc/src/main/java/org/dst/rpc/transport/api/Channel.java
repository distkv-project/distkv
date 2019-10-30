package org.dst.rpc.transport.api;

import org.dst.rpc.core.codec.Codec;

/**
 * 一个链接，或者说一个可通信的信道
 */
public interface Channel {

  /**
   * 检查一个信道是否打开。
   */
  boolean isOpen();

  void open();

  void close();


  void send(Object message);

  void receive(Object message);

  /**
   * 该信道上的编解码器
   */
  Codec getCodec();

  void setCodec(Codec codec);

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
