package org.dst.exception;

public class KeyNotFoundException extends DstException {
  protected String key;

  public KeyNotFoundException(String key) {
    super(String.format("The key %s doesn't exist in the store.", key));
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
