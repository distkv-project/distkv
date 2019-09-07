package org.dst.core.table;

public class Value {

  public final ValueType type;

  protected Value(ValueType type) {
    this.type = type;
  }

  public ValueType getType() {
    return type;
  }
}