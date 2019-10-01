package org.dst.core.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is mainly used to store the index of the corresponding field of the table,
 * including the primary key and index index. The primary key index is uniquely restricted.
 * The Map collection is used to store the primary key index information,
 * and the value corresponding to the field is stored as a key,
 * and the subscript of the record is stored as a set of values.
 */
public class Index {
  private Map<Value, List<Integer>> indexs = new HashMap<>();

  public Index() {
  }

  public Index(Map<Value, List<Integer>> indexs) {
    this.indexs = indexs;
  }

  public Map<Value, List<Integer>> getIndexs() {
    return indexs;
  }

  public void setIndexs(Map<Value, List<Integer>> indexs) {
    this.indexs = indexs;
  }

}
