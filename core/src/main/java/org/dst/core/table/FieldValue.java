package org.dst.core.table;

public class FieldValue {

  public final int index;

  public final ValueTypeEnum valueType;

  protected FieldValue(int index, ValueTypeEnum valueType) {
    this.index = index;
    this.valueType = valueType;
  }

  public int getIndex() {
    return index;
  }

  public ValueTypeEnum getValueType() {
    return valueType;
  }

  @Override
  public String toString() {
    return "FieldValue{" +
          "index=" + index +
          ", valueType=" + valueType +
          '}';
  }
}
