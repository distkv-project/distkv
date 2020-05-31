package com.distkv.core.struct.slist;

import com.distkv.common.DistkvTuple;
import com.distkv.common.entity.sortedList.SlistEntity;

import java.util.List;

/**
 * The interface of DistKVSortedList.
 */
public interface Slist {

  /**
   * Get the size of this sorted list.
   *
   * @return The size of this sorted list.
   */
  int size();

  /**
   * Get all SlistEntity list of this sorted list.
   *
   * @return SlistEntity list.
   */
  List<SlistEntity>  get();

  /**
   * Put a list with scores into this sorted list.
   *
   * @param entities The entities of the sorted list to be putted.
   *
   * @return True if we put it successfully, otherwise it's false.
   */
  boolean put(List<SlistEntity> entities);

  /**
   * Insert the entity into this sorted list.
   *
   * @param entity The entity to be putted.
   */
  void putItem(SlistEntity entity);

  /**
   * Remove the entity from the this sorted list.
   *
   * @param member The member to be removed.
   *
   * @return True if we remove it successfully, otherwise it's false.
   */
  boolean removeItem(String member);

  /**
   * Increase `delta` on the score of the given member.
   *
   * @param member The member's score to be increased.
   * @param delta The value that we will increase.
   *
   * @return 1 if we increase it successfully, -1 means it will
   * be out of integer after increasing and 0 means the member
   * doesn't exist in this sorted list.
   */
  int incrScore(String member, int delta);

  /**
   * Get the list of entities from the given start index and end index.
   *
   * @param start The start ranking.
   * @param end The end ranking.
   * @return The list of entities with the given rankings.
   */
  // TODO(qwang): Refine this.
  List<SlistEntity> subList(int start, int end);

  /**
   * Get the entity with the given member.
   *
   * @param member The member to be gotten.
   * @return A tuple whose first element is the score while
   * the second element is the ranking.
   */
  DistkvTuple<Integer, Integer> getItem(String member);
}
