package org.dst.core.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
