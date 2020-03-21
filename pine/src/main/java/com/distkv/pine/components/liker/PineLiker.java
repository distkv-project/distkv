package com.distkv.pine.components.liker;

/**
 * The `PineLiker` component is used to like the other comment.
 */
public interface PineLiker {

  /**
   * Get the people set who is liking the topic.
   * @param topic The topic name.
   * @return The PipeLikerTopic object.
   */
  public PineLikerTopic topic(String topic);

}
