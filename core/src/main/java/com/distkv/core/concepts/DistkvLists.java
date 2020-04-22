package com.distkv.core.concepts;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.Status;
import java.util.ArrayList;
import java.util.List;

public interface DistkvLists {

  /**
   * This method will put a key-value pair to map
   *
   * @param key   the key to store
   * @param value the list value to store
   */
  void put(String key, ArrayList<String> value);

  /**
   * This method will query a list value based on the key
   *
   * @param key Obtain a list value based on the key
   * @return the list value.
   */
  ArrayList<String> get(String key) throws KeyNotFoundException;

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
   * @return The status of the drop operation.
   */
  Status drop(String key);

  //insert value from the left of list
  Status lput(String key, List<String> value);

  //insert value from the right of list
  Status rput(String key, List<String> value);

  /**
   * This method will remove an item from the list.
   *
   * @param key The name of the list in store.
   * @param index The index that we want remove the item at.
   * @return Whether we succeed to remove the item.
   */
  Status remove(String key, int index)
          throws KeyNotFoundException, IndexOutOfBoundsException;

  /**
   * This method will remove a range of items from the list.
   *
   * @param key The name of the list in store.
   * @param from The left index of the range.
   * @param end The right index of the range.
   * @return Whether we succeed to remove the range of the items.
   */
  Status remove(String key, int from, int end)
          throws KeyNotFoundException, IndexOutOfBoundsException;

  /**
   * This method will remove multiple items from the list.
   *
   * @param key The name of the list in store.
   * @param indexes A list of indexes for those items you wanna to remove.
   * @return Whether we succeed to remove the items.
   */
  Status mremove(String key, List<Integer> indexes)
          throws KeyNotFoundException, IndexOutOfBoundsException;

}
