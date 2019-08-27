package org.dst.core;

import java.util.List;

public class RecordEntry {

  public TableSpecification tableSpec;

  public List<List<FieldValue>> fieldValues;


  public TableSpecification getTableSpec() {
    return this.tableSpec;
  }

  public void setTableSpec(TableSpecification tableSpec) {
    this.tableSpec = tableSpec;
  }

  public List<List<FieldValue>> getFieldValues() {
    return this.fieldValues;
  }

  public void setFieldValues(List<List<FieldValue>> fieldValues) {
    this.fieldValues = fieldValues;
  }

  @Override
  public String toString() {
    return "RecordEntry{" +
          "tableSpec=" + tableSpec +
          ", fieldValues=" + fieldValues +
          '}';
  }
}
