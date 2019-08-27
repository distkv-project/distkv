package org.dst.core.table;

import java.util.Map;

public class IndexEntry {

  private int fieldIndex;
  private Map<FieldValue, Integer> indexs;


  public int getFieldIndex() {
    return fieldIndex;
  }

  public void setFieldIndex(int fieldIndex) {
    this.fieldIndex = fieldIndex;
  }

  public Map<FieldValue, Integer> getIndexs() {
    return indexs;
  }

  public void setIndexs(Map<FieldValue, Integer> indexs) {
    this.indexs = indexs;
  }
}
