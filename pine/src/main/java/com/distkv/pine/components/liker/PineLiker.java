package com.distkv.pine.components.liker;

/**
 * The `PineLiker` component is used to like the other comment.
 */
public interface PineLiker {

  /**
   * Get the people set who is liking the topic.
   * @param topic the topic name.
   * @return the PipeLikerTopic object.
   */
  public PineLikerTopic topic(String topic);

}
