package com.distkv.core.concepts;

public class DistkvValue<T> {

  private int type;
  private T value;

  enum TYPE {
    STRING,
    LIST,
    DICT,
    INT,
    SET,
    SLIST
  }


  public DistkvValue(int type, T value) {
    this.type = type;
    this.value = value;
  }


  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }
}
