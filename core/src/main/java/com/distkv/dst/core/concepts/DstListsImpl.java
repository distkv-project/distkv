package com.distkv.dst.core.concepts;

import com.distkv.dst.common.exception.DstListIndexOutOfBoundsException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.utils.Status;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    try {
      List<String> list = this.dstKeyValueMap.get(key);
      ArrayList<Integer> thisIndex = new ArrayList<>();
      thisIndex.addAll(indexes);
      Collections.sort(thisIndex);
      for (int i = (thisIndex.size() - 1); i >= 0; i--) {
        list.remove(thisIndex.get(i).intValue());
      }
      return Status.OK;
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DstListIndexOutOfBoundsException(key, e);
    }
  }
}
