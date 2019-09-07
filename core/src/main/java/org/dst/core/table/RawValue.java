package org.dst.core.table;

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
}
