package org.dst.core.operatorset;

import org.dst.core.exception.NotImplementException;
import java.util.List;

public interface DstList {

  /**
   * This method will put a key-value pair to map
   *
   * @param Key   the key to store
   * @param Value the list value to store
   */
  public void put(String Key, List<String> Value);

  /**
   * This method will query a list value based on the key
   *
   * @param Key Obtain a list value based on the key
   * @return the list value
   */
  public List<String> get(String Key);

  /**
   * This method will delete a list value based on the key
   *
   * @param Key delete a key-value pair based on the key
   * @return true or false, indicates that the deletion succeeded or failed.
   */
  public boolean del(String Key);

  //insert value from the left of list
  public boolean lput(String Key, List<String> Value) throws NotImplementException;

  //insert value from the right of list
  public boolean rput(String Key, List<String> Value) throws NotImplementException;

  //delete n values from the left of list
  public boolean ldel(String Key, int n) throws NotImplementException;

  //delete n values from the right of list
  public boolean rdel(String Key, int n) throws NotImplementException;

}
