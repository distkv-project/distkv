package com.distkv.dst.core.table;

public class StrValue extends Value {
  private String value = null;

  public StrValue() {
    super(ValueType.STRING);
  }

  public StrValue(String value) {
    super(ValueType.STRING);
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    StrValue strValue = (StrValue) o;

    return value != null ? value.equals(strValue.value) : strValue.value == null;
  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }
}
