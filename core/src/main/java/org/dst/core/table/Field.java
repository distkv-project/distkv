package org.dst.core.table;


public class Field {

  private String name;

  private ValueType type = ValueType.NONE;

  private boolean isPrimary = false;

  private boolean isIndex = false;

  public Field() {
  }

  public Field(String name, ValueType type) {
    this.name = name;
    this.type = type;
  }

  public Field(String name, ValueType type, boolean primary) {
    this.name = name;
    this.type = type;
    this.isPrimary = primary;
  }

  public Field(String name, ValueType type, boolean primary, boolean index) {
    this.name = name;
    this.type = type;
    this.isPrimary = primary;
    this.isIndex = index;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ValueType getType() {
    return type;
  }

  public void setType(ValueType type) {
    this.type = type;
  }

  public boolean isPrimary() {
    return isPrimary;
  }

  public void setPrimary(boolean primary) {
    isPrimary = primary;
  }

  public boolean isIndex() {
    return isIndex;
  }

  public void setIndex(boolean index) {
    isIndex = index;
  }

  public static class Builder {

    private String name;

    private ValueType type = ValueType.NONE;

    private boolean primary = false;

    private boolean index = false;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder type(ValueType type) {
      this.type = type;
      return this;
    }

    public Builder primary(boolean primary) {
      this.primary = primary;
      return this;
    }

    public Builder index(boolean index) {
      this.index = index;
      return this;
    }

    public Field build() {
      return new Field(name, type, primary, index);
    }

  }
}
