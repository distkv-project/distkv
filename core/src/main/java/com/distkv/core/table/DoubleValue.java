package com.distkv.core.table;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    DoubleValue that = (DoubleValue) o;

    return Double.compare(that.value, value) == 0;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    long temp;
    temp = Double.doubleToLongBits(value);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
}
