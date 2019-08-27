package org.dst.core.table;

public class RawDataValue extends FieldValue {

  private byte[] value = null;

  public RawDataValue() {
    this(null);
  }

  public RawDataValue(byte[] value) {
    super(-1, ValueTypeEnum.RAW_DATA);
    this.value = value;
  }

  public byte[] getValue() {
    return value;
  }

  public void setValue(byte[] value) {
    this.value = value;
  }

}
