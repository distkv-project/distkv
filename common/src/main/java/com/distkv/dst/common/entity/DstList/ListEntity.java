package com.distkv.dst.common.entity.DstList;

import com.distkv.dst.common.utils.Status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListEntity {

  private ArrayList<String> thisList;

  public ListEntity nextList;

  public ListEntity () {
    thisList = new ArrayList<>();
  }

  public ListEntity (Collection<String> value) {
    thisList = new ArrayList<>();
    thisList.addAll(value);
  }

  public void setNextList (ListEntity next) {
    nextList = next;
  }

  public boolean hasNext() {
    return (nextList != null);
  }

  public int getSize () {
    return thisList.size();
  }

  public List<String> get() {
    return thisList;
  }

  public String get (int index) {
    return thisList.get(index);
  }

  public List<String> get (int from, int end) {
    return thisList.subList(from, end);
  }

  public Status lput (List<String> value) {
    thisList.addAll(0, value);
    return Status.OK;
  }

  public Status rput (List<String> value) {
    thisList.addAll(value);
    return Status.OK;
  }

  public Status remove (int index) {
    thisList.remove(index);
    return Status.OK;
  }

  public Status remove (int from, int end) {
    thisList.subList(from, end).clear();
    return Status.OK;
  }

  public Status mremove (List<Integer> sortedIndexes) {
    for (int i = (sortedIndexes.size() - 1); i >= 0; i--) {
      thisList.remove(sortedIndexes.get(i).intValue());
    }
    return Status.OK;
  }
}
