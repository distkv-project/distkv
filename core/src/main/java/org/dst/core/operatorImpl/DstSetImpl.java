package org.dst.core.operatorImpl;

import org.dst.core.DstConcurrentHashMap;
import org.dst.core.DstConcurrentHashMapImpl;
import org.dst.core.operatorset.DstSet;
import org.dst.common.exception.KeyNotFoundException;
import org.dst.common.utils.Status;
import java.util.Set;

public class DstSetImpl implements DstSet {

  private DstConcurrentHashMap<String, Set<String>> setMap;

  public DstSetImpl() {
    this.setMap = new DstConcurrentHashMapImpl<>();
  }

  @Override
  public void put(String key, Set<String> value) {
    setMap.put(key, value);
  }

  @Override
  public Set<String> get(String key) {
    if (!setMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    return setMap.get(key);
  }

  @Override
  public Status dropByKey(String key) {
    if (!setMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    setMap.remove(key);
    return Status.OK;
  }

  @Override
  public Status del(String key, String value) {
    if (!setMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    setMap.get(key).remove(value);
    return Status.OK;
  }

  @Override
  public boolean exists(String key, String value) throws KeyNotFoundException {
    if (!setMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    return setMap.get(key).contains(value);
  }

}
