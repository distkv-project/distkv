package com.distkv.pine.components.liker;

/**
 * The `PineLiker` component is used to like the other comment
 */
public interface PineLiker {

  /**
   * The fromPeople will like the PineLiker comment
   * @param fromPeople the liked name
   */
  public void likesFrom(String fromPeople);

  /**
   * The fromPeople will unlike the PineLiker comment
   * @param fromPeople the unLiked name
   */
  public void unLikesFrom(String fromPeople);

  /**
   * Get the PineLiker's likes number
   * @return the likes number
   */
  public int count();

}
