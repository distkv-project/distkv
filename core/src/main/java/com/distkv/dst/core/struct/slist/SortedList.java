package com.distkv.dst.core.struct.slist;

import com.distkv.dst.common.entity.sortedList.SortedListEntity;

import java.util.List;

/**
 * Basic data structure interface of the Dst SortedList
 */
public interface SortedList {

  /**
   * This method will get SortedList's size.
   *
   * @return the SortedList's size
   */
  int getSize();

  /**
   * This method will put a list into the SortedList initially.
   *
   * @param sortedListEntities the List value
   * @return whether the put operation succeeds
   */
  boolean put(List<SortedListEntity> sortedListEntities);

  /**
   * This method will insert the entity item into the SortedList.
   *
   * @param sortedListEntity the SortedListEntity value
   */
  void putItem(SortedListEntity sortedListEntity);

  /**
   * This method will remove the entity item from the SortedList.
   *
   * @param member the String which value is the removed member name
   * @return whether the removeItem operation succeeds
   */
  boolean removeItem(String member);

  /**
   * This method will increase the score of the specified delta
   * for people whose name is member.
   *
   * @param member the member which needs to increase the score
   * @param delta the count which should increase
   * @return the status of the incrScore operation, if return 1
   * when the operation succeeds, if return -1 when the original
   * score that plus delta outs of integer range, if return 0 when
   * member does not exist in the SortedList
   */
  int incrScore(String member, int delta);

  /**
   * This method will get items descending from start to end.
   *
   * @param start the int value which located in the start index
   * @param end the int value which located in the end index
   * @return some entities which rankings are from start to end
   */
  List<SortedListEntity> subList(int start, int end);

  /**
   * This method will get its score and its ranking by the member
   *
   * @param member the member name
   * @return the list value which the first element is the score
   *  and the second element is the ranking located in this SortedList
   */
  List<Integer> getItem(String member);
}
