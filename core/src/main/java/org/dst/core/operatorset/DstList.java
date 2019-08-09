package org.dst.core.operatorset;

import org.dst.core.exception.NotImplementException;
import java.util.List;

public interface DstList {

  /**
   * This method will put a key-value pair to map
   *
   * @param key   the key to store
   * @param value the list value to store
   */
  public void put(String key, List<String> value);

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
  public boolean del(String key);

  //insert value from the left of list
  public boolean lput(String key, List<String> value) throws NotImplementException;

  //insert value from the right of list
  public boolean rput(String key, List<String> value) throws NotImplementException;

  //delete n values from the left of list
  public boolean ldel(String key, int n) throws NotImplementException;

  //delete n values from the right of list
  public boolean rdel(String key, int n) throws NotImplementException;

}
