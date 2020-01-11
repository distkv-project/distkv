package com.distkv.dst.core.concepts;

import com.distkv.dst.common.exception.DstListIndexOutOfBoundsException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.utils.Status;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DstListsImpl extends DstConcepts<ArrayList<String>> implements DstLists {

  public DstListsImpl() {
  }

  @Override
  public String get(String key, int index) throws KeyNotFoundException, IndexOutOfBoundsException {
    try {
      final List<String> list = this.dstKeyValueMap.get(key);
      return list.get(index);
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DstListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public List<String> get(String key, int from, int end)
      throws KeyNotFoundException, IndexOutOfBoundsException {
    try {
      final List<String> list = this.dstKeyValueMap.get(key);
      return list.subList(from, end);
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DstListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public Status lput(String key, List<String> value) {
    if (!this.dstKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }
    this.dstKeyValueMap.get(key).addAll(0, value);
    return Status.OK;
  }

  @Override
  public Status rput(String key, List<String> value) {
    if (!this.dstKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }
    this.dstKeyValueMap.get(key).addAll(value);
    return Status.OK;
  }

  @Override
  public Status remove(String key, int index)
          throws KeyNotFoundException, DstListIndexOutOfBoundsException {
    try {
      List<String> list = this.dstKeyValueMap.get(key);
      list.remove(index);
      return Status.OK;
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DstListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public Status remove(String key, int from, int end)
          throws KeyNotFoundException, DstListIndexOutOfBoundsException {
    try {
      List<String> list = this.dstKeyValueMap.get(key);
      list.subList(from, end).clear();
      return Status.OK;
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DstListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public Status mremove(String key, List<Integer> indexes)
          throws KeyNotFoundException, DstListIndexOutOfBoundsException {
    final List<String> list = this.dstKeyValueMap.get(key);
    if (list == null) {
      throw new KeyNotFoundException(key);
    }

    final Set<Integer> set = new HashSet<>();
    for (final Integer index : indexes) {
      if (index >= list.size()) {
        throw new DstListIndexOutOfBoundsException(key);
      }
      set.add(index);
    }

    final ArrayList<String> tempList = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      if (!set.contains(i)) {
        tempList.add(list.get(i));
      }
    }
    list.clear();
    list.addAll(tempList);
    return Status.OK;
  }
}
