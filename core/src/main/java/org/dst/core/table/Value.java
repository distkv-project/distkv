package org.dst.core.table;

public class Value {

  public final ValueType type;

  public Value(ValueType type) {
    this.type = type;
  }

  public ValueType getType() {
    return type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Value value = (Value) o;

    return type == value.type;
  }

  @Override
  public int hashCode() {
    return type != null ? type.hashCode() : 0;
  }
}
