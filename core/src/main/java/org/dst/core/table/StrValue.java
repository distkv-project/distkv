package org.dst.core.table;

public class StrValue extends Value {
  private String value = null;

  protected StrValue() {
    super(ValueType.STRING);
  }

  protected StrValue(String value) {
    super(ValueType.STRING);
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
