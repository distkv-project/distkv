package org.dst.core.table;

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

}
