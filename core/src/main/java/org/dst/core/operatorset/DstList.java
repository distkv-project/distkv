package org.dst.core.operatorset;

import org.dst.utils.Status;
import java.util.List;

public interface DstList {

  /**
   * This method will put a key-value pair to map
   *
   * @param key   the key to store
   * @param value the list value to store
   */
  Status put(String key, List<String> value);

  /**
   * This method will query a list value based on the key
   *
   * @param key Obtain a list value based on the key
   * @return the list value
   */
  List<String> get(String key);

  /**
   * This method will delete a list value based on the key
   *
   * @param key delete a key-value pair based on the key
   * @return true or false, indicates that the deletion succeeded or failed.
   */
  Status del(String key);

  //insert value from the left of list
  Status lput(String key, List<String> value);

  //insert value from the right of list
  Status rput(String key, List<String> value);

  //delete n values from the left of list
  Status ldel(String key, int n);

  //delete n values from the right of list
  Status rdel(String key, int n);

}
