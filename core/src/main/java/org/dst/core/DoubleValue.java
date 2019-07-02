package org.dst.core;

public class DoubleValue extends FieldValue {

  private double value = 0.0;

  public DoubleValue() {
    this(0.0);
  }

  public DoubleValue(Double value) {
    super(-1, ValueTypeEnum.DOUBLE);
    this.value = value;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

}
