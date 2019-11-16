package org.dst.core.operatorset;

import org.dst.common.exception.KeyNotFoundException;
import org.dst.common.utils.Status;
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
   * @return the list value.
   */
  List<String> get(String key) throws KeyNotFoundException;

  /**
   * Get the value of the given index from the list.
   *
   * @param key The key of the list.
   * @param index The index that we want get the value at.
   * @return The value at the given index from the list.
   */
  String get(String key, int index)
      throws KeyNotFoundException, IndexOutOfBoundsException;

  /**
   * Get the values of the given range.
   *
   * @param key The key of the list.
   * @param from The left index of the range.
   * @param end The right index of the range.
   * @return The values of the given range.
   */
  List<String> get(String key, int from, int end)
      throws KeyNotFoundException, IndexOutOfBoundsException;

  /**
   * This method will delete a list value based on the key
   *
   * @param key delete a key-value pair based on the key
   * @return true or false, indicates that the deletion succeeded or failed.
   */
  Status drop(String key);

  //insert value from the left of list
  Status lput(String key, List<String> value);

  //insert value from the right of list
  Status rput(String key, List<String> value);

  /**
   * This method will delete a element of the list which based on the key
   *
   * @param key delete a element of a list which based on the key
   * @param index The index that we want delete the element at.
   * @return true or false, indicates that the deletion succeeded or failed.
   */
  Status delete(String key, int index)
          throws KeyNotFoundException, IndexOutOfBoundsException;

  /**
   * This method will delete a range of elements of the list which based on the key
   *
   * @param key delete a range of elements of a list which based on the key
   * @param from The left index of the range.
   * @param end The right index of the range.
   * @return true or false, indicates that the deletion succeeded or failed.
   */
  Status delete(String key, int from, int end)
          throws KeyNotFoundException, IndexOutOfBoundsException;

  /**
   * This method will delete a series of elements of the list which based on the key
   *
   * @param key delete a series of elements of a list which based on the key
   * @param index A list of index for those elements you wanna to delete
   * @return true or false, indicates that the deletion succeeded or failed.
   */
  Status mdelete(String key, List<Integer> index)
          throws KeyNotFoundException, IndexOutOfBoundsException;

}
