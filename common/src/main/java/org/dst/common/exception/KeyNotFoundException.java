package org.dst.common.exception;

/**
 * The exception class to indicates that the key not found.
 */
public class KeyNotFoundException extends DstException {
  protected String key;

  public KeyNotFoundException(String key) {
    super(String.format("The key %s is not found in the store.", key));
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
