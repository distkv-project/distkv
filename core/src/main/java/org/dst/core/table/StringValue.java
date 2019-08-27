package org.dst.core.table;

public class StringValue extends FieldValue {

  private String value = null;

  public StringValue() {
    this(null);
  }

  // TODO(qwang): Should we pass by value?
  public StringValue(String value) {
    super(-1, ValueTypeEnum.STRING);
    this.value = value;
  }

  public StringValue(int index, String value) {
    super(index, ValueTypeEnum.STRING);
    this.value = value;
  }

  public String getValue() {
    return value;
  }


  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "StringValue{" +
          "value='" + value + '\'' +
          ", index=" + index +
          ", valueType=" + valueType +
          '}';
  }
}
