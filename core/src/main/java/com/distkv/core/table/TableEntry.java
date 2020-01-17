package com.distkv.dst.core.table;

import java.util.ArrayList;
import java.util.List;

public class TableEntry {

  private TableSpecification tableSpec;

  private List<Record> records = new ArrayList<>();

  private Index index = new Index();

  public TableEntry() {
  }

  public TableEntry(TableSpecification tableSpec) {
    this.tableSpec = tableSpec;
  }

  public TableEntry(TableSpecification tableSpec, List<Record> records, Index index) {
    this.tableSpec = tableSpec;
    this.records = records;
    this.index = index;
  }

  public TableSpecification getTableSpec() {
    return tableSpec;
  }

  public void setTableSpec(TableSpecification tableSpec) {
    this.tableSpec = tableSpec;
  }

  public List<Record> getRecords() {
    return records;
  }

  public void setRecords(List<Record> records) {
    this.records = records;
  }

  public Index getIndex() {
    return index;
  }

  public void setIndex(Index index) {
    this.index = index;
  }

  public static class Builder {
    private TableSpecification tableSpec;

    private List<Record> records = new ArrayList<>();

    private Index index = new Index();

    public Builder tableSpec(TableSpecification tableSpec) {
      this.tableSpec = tableSpec;
      return this;
    }

    public Builder records(List<Record> records) {
      this.records = records;
      return this;
    }

    public Builder index(Index index) {
      this.index = index;
      return this;
    }

    public TableEntry builder() {
      return new TableEntry(tableSpec, records, index);
    }
  }
}
