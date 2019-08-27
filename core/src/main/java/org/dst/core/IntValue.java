package org.dst.core;

public class IntValue extends FieldValue {

  private int value = 0;

  public IntValue() {
    this(0);
  }

  public IntValue(int value) {
    super(-1, ValueTypeEnum.INT);
    this.setValue(value);
  }
  public IntValue(int index,int value) {
    super(index, ValueTypeEnum.INT);
    this.setValue(value);
  }
  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "IntValue{" +
          "value=" + value +
          ", index=" + index +
          ", valueType=" + valueType +
          '}';
  }
}
