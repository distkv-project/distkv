package org.dst.core.table;

import java.util.ArrayList;
import java.util.List;

public class Record {

  private List<Value> record = new ArrayList<>();

  public Record() {
  }

  public Record(List<Value> record) {
    this.record = record;
  }

  public List<Value> getRecord() {
    return record;
  }

  public void setRecord(List<Value> record) {
    this.record = record;
  }

}
