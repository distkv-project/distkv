package com.distkv.dst.core.struct.list;

import com.distkv.dst.common.entity.DstList.ListEntity;
import com.distkv.dst.common.utils.Status;

import java.util.ArrayList;
import java.util.List;

public final class ListImpl implements DstList, java.io.Serializable {


  private final int NODE_LIST_MAX_SIZE = 100000;

  private final int size = 0;

  private transient ListEntity left;

  private transient ListEntity right;

  public ListImpl() {

  }

  public boolean isEmpty() {
    return (left == null || right == null);
  }

  public int size() {
    return size;
  }

  @Override
  public List<String> get() {
    ListEntity thisList = left;
    final List<String> list = thisList.get();
    while(thisList.hasNext()){
      thisList = thisList.nextList;
      list.addAll(thisList.get());
    }
    return list;
  }

  @Override
  public String get(int index) {
    ListEntity thisList = left;
    int length = 0;
    while((index - length) > thisList.getSize()) {
      length = length + thisList.getSize();
      thisList = thisList.nextList;
    }
    return thisList.get(index - length);
  }

  @Override
  public List<String> get(int from, int end) {
    return null;
  }

  @Override
  public Status lput(List<String> value) {
    return null;
  }

  @Override
  public Status rput(List<String> value) {
    return null;
  }

  @Override
  public Status remove(int index) {
    return null;
  }

  @Override
  public Status remove(int from, int end) {
    return null;
  }

  @Override
  public Status mremove(List<Integer> indexes) {
    return null;
  }
}
