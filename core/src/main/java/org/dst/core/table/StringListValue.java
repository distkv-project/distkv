package org.dst.core.table;

import java.util.List;

public class StringListValue extends FieldValue {

  private List<String> value = null;

  public StringListValue() {
    this(null);
  }

  public StringListValue(List<String> value) {
    super(-1, ValueTypeEnum.STRING_LIST);
    this.value = value;
  }

  public StringListValue(int index, List<String> value) {
    super(index, ValueTypeEnum.STRING_LIST);
    this.value = value;
  }

  public List<String> getValue() {
    return value;
  }

  public void setValue(List<String> value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "StringListValue{" +
          "value=" + value +
          ", index=" + index +
          ", valueType=" + valueType +
          '}';
  }
}

