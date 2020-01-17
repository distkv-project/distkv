package com.distkv.core.table;

import java.util.Arrays;

public class RawValue extends Value {
  private byte[] value = null;

  public RawValue() {
    super(ValueType.RAW_DATA);
  }

  public RawValue(byte[] value) {
    super(ValueType.RAW_DATA);
    this.value = value;
  }

  public byte[] getValue() {
    return value;
  }

  public void setValue(byte[] value) {
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

    RawValue rawValue = (RawValue) o;

    return Arrays.equals(value, rawValue.value);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(value);
  }
}
