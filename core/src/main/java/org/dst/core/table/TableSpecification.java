package org.dst.core.table;

import java.util.List;

public class TableSpecification {

  public final String name;

  public final List<FieldSpecification> fields;

  public TableSpecification(String name, List<FieldSpecification> fields) {
    this.name = name;
    this.fields = fields;
  }

  public static class Builder {

    private String name = null;
    private List<FieldSpecification> fields = null;

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setFields(List<FieldSpecification> fields) {
      this.fields = fields;
      return this;
    }

    public TableSpecification build() {
      return new TableSpecification(name, fields);
    }
  }

  public String getName() {
    return this.name;
  }

  public List<FieldSpecification> getFields() {
    return this.fields;
  }

  @Override
  public String toString() {
    return "TableSpecification{" +
          "name='" + name + '\'' +
          ", fields=" + fields +
          '}';
  }
}
