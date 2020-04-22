package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.DistkvListIndexOutOfBoundsException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.Status;
import com.distkv.core.DistkvMapInterface;
import com.distkv.core.concepts.DistkvValue.TYPE;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DistkvListsImpl extends DistkvConcepts<ArrayList<String>> implements DistkvLists {

  public DistkvListsImpl(
      DistkvMapInterface<String, DistkvValue> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }


  @Override
  public void put(String key, ArrayList<String> value) {
    if (distkvKeyValueMap.containsKey(key)) {
      throw new DistkvKeyDuplicatedException(key);
    }
    distkvKeyValueMap.put(key, new DistkvValue<>(TYPE.LIST.ordinal(),value));
    Object o = distkvKeyValueMap.get(key);
  }

  @Override
  public String get(String key, int index) throws KeyNotFoundException, IndexOutOfBoundsException {
    try {
      final List<String> list = get(key).getValue();
      return list.get(index);
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DistkvListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public DistkvValue<List<String>> get(String key, int from, int end)
      throws KeyNotFoundException, IndexOutOfBoundsException {
    try {
      DistkvValue<ArrayList<String>> value = get(key);
      List<String> subValues = value.getValue().subList(from, end);
      return new DistkvValue<>(value.getType(),subValues);
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DistkvListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public Status lput(String key, List<String> value) {
    if (!this.distkvKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }
    get(key).getValue().addAll(0, value);
    return Status.OK;
  }

  @Override
  public Status rput(String key, List<String> value) {
    if (!this.distkvKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }
    get(key).getValue().addAll(value);
    return Status.OK;
  }

  @Override
  public Status remove(String key, int index)
          throws KeyNotFoundException, DistkvListIndexOutOfBoundsException {
    try {
      List<String> list = get(key).getValue();
      list.remove(index);
      return Status.OK;
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DistkvListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public Status remove(String key, int from, int end)
          throws KeyNotFoundException, DistkvListIndexOutOfBoundsException {
    try {
      List<String> list = get(key).getValue();
      list.subList(from, end).clear();
      return Status.OK;
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DistkvListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public Status mremove(String key, List<Integer> indexes)
      throws KeyNotFoundException, DistkvListIndexOutOfBoundsException {
    final List<String> list = get(key).getValue();
    if (list == null) {
      throw new KeyNotFoundException(key);
    }

    final Set<Integer> set = new HashSet<>();
    for (final Integer index : indexes) {
      if (index >= list.size()) {
        throw new DistkvListIndexOutOfBoundsException(key);
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
