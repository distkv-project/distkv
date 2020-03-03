package com.distkv.pine.components.liker;

import java.time.LocalTime;

/**
 * The `PineLiker` component is used to like the other comment
 */
public interface PineLiker {

  public void likesTo(String people, String content, LocalTime localTime);

  public PineLiker getfromPeople(String people);

  public void unLikesTo(String people, String content, LocalTime localTime);

}
