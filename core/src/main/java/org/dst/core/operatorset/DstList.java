package org.dst.core.operatorset;

import org.dst.core.exception.NotImplementException;
import org.dst.utils.enums.Status;

import java.util.List;

public interface DstList {

  /**
   * This method will put a key-value pair to map
   *
   * @param key   the key to store
   * @param value the list value to store
   */
  public Status put(String key, List<String> value);

  /**
   * This method will query a list value based on the key
   *
   * @param key Obtain a list value based on the key
   * @return the list value
   */
  public List<String> get(String key);

  /**
   * This method will delete a list value based on the key
   *
   * @param key delete a key-value pair based on the key
   * @return true or false, indicates that the deletion succeeded or failed.
   */
  public Status del(String key);

  //insert value from the left of list
  public Status lput(String key, List<String> value) throws NotImplementException;

  //insert value from the right of list
  public Status rput(String key, List<String> value) throws NotImplementException;

  //delete n values from the left of list
  public Status ldel(String key, int n) throws NotImplementException;

  //delete n values from the right of list
  public Status rdel(String key, int n) throws NotImplementException;

}
