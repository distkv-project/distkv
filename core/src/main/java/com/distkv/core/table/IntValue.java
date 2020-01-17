package com.distkv.core.table;

public class IntValue extends Value {

  private int value = 0;

  public IntValue() {
    super(ValueType.INT);
  }

  public IntValue(int value) {
    super(ValueType.INT);
    this.value = value;
  }


  public int getValue() {
    return value;
  }

  public void setValue(int value) {
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

    IntValue intValue = (IntValue) o;

    return value == intValue.value;
  }

  @Override
  public int hashCode() {
    return value;
  }
}
