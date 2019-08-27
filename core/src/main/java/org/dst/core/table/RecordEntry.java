package org.dst.core.table;

import java.util.List;
import java.util.Map;

public class RecordEntry {

  /**
   * store description of table
   */
  public TableSpecification tableSpec;

  /**
   * store all records
   */
  public List<List<FieldValue>> fieldValues;

  /**
   * store all primarys
   */
  public Map<FieldValue, Integer> primarys;

  /**
   * store all index
   */
  public IndexEntry indexEntry;


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

  public Map<FieldValue, Integer> getPrimarys() {
    return primarys;
  }

  public void setPrimarys(Map<FieldValue, Integer> primarys) {
    this.primarys = primarys;
  }

  public IndexEntry getIndexEntry() {
    return indexEntry;
  }

  public void setIndexEntry(IndexEntry indexEntry) {
    this.indexEntry = indexEntry;
  }
}
