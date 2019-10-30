package org.dst.rpc.transport.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dst.rpc.core.codec.Codec;

/**
 *
 */
public abstract class AbstractChannel implements Channel {

  private static final int NEW = 0;
  private static final int OPENED = 0;
  private static final int CLOSED = 0;

  private Codec codec;
  private int status = NEW;
  private Map<String, Object> attributes;


  public AbstractChannel() {
    attributes = new ConcurrentHashMap<>();
  }

  @Override
  public boolean isOpen() {
    return status == OPENED;
  }

  @Override
  public void open() {
    status = OPENED;
    doOpen();
  }

  @Override
  public void close() {
    status = CLOSED;
    doClose();
  }

  protected abstract void doOpen();

  protected abstract void doClose();

  public void setCodec(Codec codec) {
    this.codec = codec;
  }

  @Override
  public Codec getCodec() {
    return codec;
  }

  @Override
  public boolean hasAttribute(String key) {
    return attributes.containsKey(key);
  }

  @Override
  public Object getAttribute(String key) {
    return attributes.get(key);
  }

  @Override
  public void setAttribute(String key, Object value) {
    if (value == null) {
      attributes.remove(key);
    } else {
      attributes.put(key, value);
    }
  }

  @Override
  public void removeAttribute(String key) {
    attributes.remove(key);
  }
}
