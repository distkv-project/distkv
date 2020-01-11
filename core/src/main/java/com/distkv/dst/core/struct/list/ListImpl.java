package com.distkv.dst.core.struct.list;

import com.distkv.dst.common.entity.DstList.ListEntity;
import com.distkv.dst.common.utils.Status;

import java.util.ArrayList;
import java.util.List;

public final class ListImpl implements DstList, java.io.Serializable {


  private final int NODE_LIST_Critical_SIZE = 100000;

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
    while (thisList.hasNext()){
      thisList = thisList.nextList;
      list.addAll(thisList.get());
    }
    return list;
  }

  @Override
  public String get(int index) {
    ListEntity thisList = left;
    while (index > thisList.getSize()) {
      index = index - thisList.getSize();
      thisList = thisList.nextList;
    }
    return thisList.get(index);
  }

  @Override
  public List<String> get(int from, int end) {
    ListEntity thisList = left;
    while (from > thisList.getSize()) {
      from = from - thisList.getSize();
      end = end - thisList.getSize();
      thisList = thisList.nextList;
    }
    List<String> list = thisList.get(from, Math.min(thisList.getSize(), end));
    end -= thisList.getSize();
    thisList = thisList.nextList;
    while (end > thisList.getSize()) {
      list.addAll(thisList.get());
      end -= thisList.getSize();
      thisList = thisList.nextList;
    }
    list = thisList.get(0, end);
    return list;
  }

  @Override
  public Status lput(List<String> value) {
    ListEntity thisList = left;
    if (thisList.getSize() + value.size() > NODE_LIST_Critical_SIZE) {
      ListEntity newLeft = new ListEntity(value);
      newLeft.setNextList(thisList);
      left = newLeft;
    } else {
      thisList.lput(value);
    }
    return Status.OK;
  }

  @Override
  public Status rput(List<String> value) {
    ListEntity thisList = right;
    thisList.rput(value);
    return Status.OK;
  }

  @Override
  public Status remove(int index) {
    ListEntity thisList = left;
    while (index > thisList.getSize()) {
      index = index - thisList.getSize();
      thisList = thisList.nextList;
    }
    thisList.remove(index);
    return Status.OK;
  }

  @Override
  public Status remove(int from, int end) {
    ListEntity thisList = left;
    while (from > thisList.getSize()) {
      from = from - thisList.getSize();
      end = end - thisList.getSize();
      thisList = thisList.nextList;
    }
    thisList.remove(from, Math.min(thisList.getSize(), end));
    end -= thisList.getSize();
    thisList = thisList.nextList;
    while (end > thisList.getSize()) {
      end -= thisList.getSize();
      thisList = thisList.nextList;
    }
    thisList.get(0, end);
    return null;
  }

  @Override
  public Status mremove(List<Integer> indexes) {
    return null;
  }
}
