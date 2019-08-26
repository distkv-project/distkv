package org.dst.exception;

public class DictKeyNotFoundException extends DstException {
  protected String key;

  public DictKeyNotFoundException(String key) {
    super(String.format("The dict key %s doesn't exist in the dicts.", key));
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
