package com.distkv.dst.common.entity.DstList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.Set;

public class DstListImpl
      implements java.io.Serializable, RandomAccess {

  private static final String[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

  private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

  private int size = 0;

  transient String[] elementData;

  public DstListImpl(){
    this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
  }

  public List<String> get() {
    return new ArrayList<>(Arrays.asList(Arrays.copyOf(elementData, size)));
  }

  public String get(int index) {
    return elementData[index];
  }

  public String set(int index, String element) {
    String oldValue = this.elementData[index];
    this.elementData[index] = element;
    return oldValue;
  }

  public boolean lput(Collection<String> value) {
    String[] values = (String[]) value.toArray();
    int oldCapacity = size;
    int increment = values.length;
    if (increment == 0) return false;
    Object[] elementData = this.elementData;
    int minCapacity = oldCapacity + increment;
    if (minCapacity > elementData.length) {
      elementData = grow(minCapacity);
    }
    System.arraycopy(values, 0, elementData, oldCapacity, increment);
    this.size = size + increment;
    return true;
  }

  public boolean rput(List<String> value) {
    String[] values = (String[]) value.toArray();
    int oldCapacity = size;
    int increment = values.length;
    if (increment == 0) return false;
    Object[] elementData = this.elementData;
    int minCapacity = oldCapacity + increment;
    if (minCapacity > elementData.length) {
      elementData = grow(minCapacity);
    }
    System.arraycopy(elementData, 0, elementData, increment, oldCapacity);
    System.arraycopy(values, 0, elementData, 0, increment);
    this.size = size + increment;
    return true;
  }

  public List<String> subList(int fromIndex, int toIndex) {
    return new ArrayList<>(Arrays.asList(Arrays.copyOfRange(elementData, fromIndex, toIndex)));
  }

  public String remove(int index) {
    String[] elements = this.elementData;
    String oldValue = elements[index];
    fastRemove(elements, index);
    return oldValue;
  }
  public boolean removeRange(int from, int end) {
    String[] elements = this.elementData;
    fastRemove(elements, from, end);
    return true;
  }

  public boolean mremove(Set<Integer> indexes) {
    int i = 0;
    for (int j = 0; j < size; j++) {
      if (!indexes.contains(j)) {
        elementData[i++] = elementData[j];
      }
    }
    return true;
  }

  private void fastRemove(Object[] elements, int index) {
    final int newSize = size - 1;
    if (newSize > index) {
      System.arraycopy(elements, index + 1, elements, index, newSize - index);
    }
    elements[size = newSize] = null;
  }

  private void fastRemove(Object[] elements, int from, int to) {
    final int newSize = size - (to - from);
    System.arraycopy(elements, to, elements, from, size - to);
    for(int end = size, start = newSize; start < end; start ++){
      elements[start] = null;
    }
  }

  public int size() {
    return size;
  }

  public void clear() {
    for (int i = 0; i < size; i++) {
      elementData[i] = null;
    }
    this.size = 0;
  }

  private String[] grow(int minCapacity) {
    return elementData = Arrays.copyOf(elementData, newCapacity(minCapacity));
  }

  private int newCapacity(int minCapacity) {
    int oldCapacity = this.elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    while(newCapacity < minCapacity) {
      newCapacity = Math.min(newCapacity + newCapacity >> 1, MAX_ARRAY_SIZE);
    }
    return newCapacity;
  }

  private boolean checkIndexLegality(int index) {
    return (index <= size || index > 0);
  }

  private boolean checkRangeLegality(int valueSize) {
    return (valueSize < MAX_ARRAY_SIZE - size);
  }


}
