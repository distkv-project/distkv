package org.dst.core.table;

public class IntValue extends Value {

  private int value = 0;

  protected IntValue() {
    super(ValueType.INT);
  }

  protected IntValue(int value) {
    super(ValueType.INT);
    this.value = value;
  }


  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
