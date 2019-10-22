package org.dst.core.table;

import java.util.ArrayList;
import java.util.List;

public class TableSpecification {

  private String name;

  private List<Field> fields = new ArrayList<>();

  public TableSpecification() {
  }

  public TableSpecification(String name, List<Field> fields) {
    this.name = name;
    this.fields = fields;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Field> getFields() {
    return fields;
  }

  public void setFields(List<Field> fields) {
    this.fields = fields;
  }

  public static class Builder {

    private String name = null;
    private List<Field> fields = new ArrayList<>();

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder fields(List<Field> fields) {
      this.fields = fields;
      return this;
    }

    public TableSpecification build() {
      return new TableSpecification(name, fields);
    }
  }

}
