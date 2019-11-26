package com.distkv.dst.core.table;

import java.util.ArrayList;
import java.util.List;

public class StrListValue extends Value {
  private List<String> value = new ArrayList<>();

  public StrListValue() {
    super(ValueType.STRING_LIST);
  }

  public StrListValue(List<String> value) {
    super(ValueType.STRING_LIST);
    this.value = value;
  }

  public List<String> getValue() {
    return value;
  }

  public void setValue(List<String> value) {
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

    StrListValue that = (StrListValue) o;

    return value != null ? value.equals(that.value) : that.value == null;
  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }
}
