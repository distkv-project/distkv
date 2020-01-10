package com.distkv.dst.core.struct.list;

import com.distkv.dst.common.utils.Status;

import java.util.List;

public interface DstList {

  /**
   * Get the values of the list.
   *
   * @return The values of the list.
   */
  List<String> get();

  /**
   * Get the value of the given index from the list.
   *
   * @param index The index that we want get the value at.
   * @return The value at the given index from the list.
   */
  String get(int index);

  /**
   * Get the values of the given range.
   *
   * @param from The left index of the range.
   * @param end The right index of the range.
   * @return The values of the given range.
   */
  List<String> get(int from, int end);

  /**
   * insert value from the left of list
   *
   * @param value
   * @return
   */
  Status lput(List<String>value);

  /**
   * insert value from the right of list
   *
   * @param value
   * @return
   */
  Status rput(List<String>value);

  /**
   * This method will remove an item from the list.
   *
   * @param index The index that we want remove the item at.
   * @return Whether we succeed to remove the item.
   */
  Status remove(int index);

  /**
   * This method will remove a range of items from the list.
   *
   * @param from The left index of the range.
   * @param end The right index of the range.
   * @return Whether we succeed to remove the range of the items.
   */
  Status remove(int from, int end);

  /**
   * This method will remove multiple items from the list.
   *
   * @param indexes A list of indexes for those items you wanna to remove.
   * @return Whether we succeed to remove the items.
   */
  Status mremove(List<Integer> indexes);
}
