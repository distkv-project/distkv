package org.dst.core;

public class FieldValue {

  public final int index;

  public final ValueTypeEnum valueType;

  protected FieldValue(int index, ValueTypeEnum valueType) {
    this.index = index;
    this.valueType = valueType;
  }

}
