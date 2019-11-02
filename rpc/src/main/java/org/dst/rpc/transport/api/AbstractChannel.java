package org.dst.rpc.transport.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public abstract class AbstractChannel implements Channel {

  private Map<String, Object> attributes;

  public AbstractChannel() {
    this.attributes = new ConcurrentHashMap<>();
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
