package com.distkv.pine.components;

import javafx.util.Pair;
import java.util.List;

/**
 * The `PineTopper` component is used to do a xxxx.
 */
public interface PineTopper {

  /**
   * Add a member to this topper.
   *
   * @param memberName The name of the member that will be added.
   * @param memberScore The score of the member.
   */
  public void addMember(String memberName, int memberScore);

  /**
   * Remove a member from this topper.
   *
   * @param memberName The name of the member that will be removed.
   */
  public void removeMember(String memberName);

  /**
   * Get the topper members.
   *
   * @param num The top number that will be returned.
   */
  // TODO(qwang): The `Pair` should be refined with our implementation.
  public List<Pair<String, Integer>> top(int num);

}
