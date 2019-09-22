package org.dst.common.exception;

/**
 * The exception class to indicates that the key of a dict not found.
 */
public class DictKeyNotFoundException extends KeyNotFoundException {
  protected String key;

  public DictKeyNotFoundException(String key) {
    super(String.format("The dict key %s not found in the dicts.", key));
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
