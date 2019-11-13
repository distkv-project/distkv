package org.dst.core.operatorImpl;

import org.dst.common.exception.DstListIndexOutOfBoundsException;
import org.dst.core.DstMapInterface;
import org.dst.core.DstConcurrentHashMapImpl;
import org.dst.core.operatorset.DstList;
import org.dst.common.exception.KeyNotFoundException;
import org.dst.common.utils.Status;
import java.util.List;

public class DstListImpl implements DstList {

  private DstMapInterface<String, List<String>> listMap;

  public DstListImpl() {
    this.listMap = new DstConcurrentHashMapImpl<>();
  }

  @Override
  public Status put(String key, List<String> value) {
    listMap.put(key, value);
    return Status.OK;
  }

  @Override
  public List<String> get(String key) {
    if (!listMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    return listMap.get(key);
  }

  @Override
  public String get(String key, int index) throws KeyNotFoundException, IndexOutOfBoundsException {
    try {
      final List<String> list = listMap.get(key);
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
      final List<String> list = listMap.get(key);
      return list.subList(from, end);
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DstListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public Status del(String key) {
    if (!listMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }
    listMap.remove(key);
    return Status.OK;
  }

  @Override
  public Status lput(String key, List<String> value) {
    if (!listMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }
    listMap.get(key).addAll(0, value);
    return Status.OK;
  }

  @Override
  public Status rput(String key, List<String> value) {
    if (!listMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }
    listMap.get(key).addAll(value);
    return Status.OK;
  }

  @Override
  public Status ldel(String key, int n) {
    if (!listMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }
    List<String> original = listMap.get(key);
    if (original.size() < n) {
      original.clear();
      return Status.OK;
    }
    for (int i = 0; i < n; i++) {
      original.remove(0);
    }
    return Status.OK;
  }

  @Override
  public Status rdel(String key, int n) {
    if (!listMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }
    List<String> original = listMap.get(key);
    int size = original.size();
    if (size <= n) {
      original.clear();
      return Status.OK;
    }
    for (int i = 0; i < n; i++) {
      original.remove(size - 1);
      size--;
    }
    return Status.OK;
  }
}
