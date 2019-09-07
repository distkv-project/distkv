package org.dst.core.table;

public class DoubleValue extends Value {

  private double value = 0.0;

  public DoubleValue() {
    super(ValueType.DOUBLE);
    this.value = value;
  }

  public DoubleValue(double value) {
    super(ValueType.DOUBLE);
    this.value = value;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }
}
