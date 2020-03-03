package com.distkv.pine.components.liker;

public abstract class AbstractPineLiker {
  protected PineLikerKey pineLikerKey;

  protected AbstractPineLiker(
      String people, String content) {
    pineLikerKey = new PineLikerKey.Builder()
        .setPeople(people)
        .setContent(content)
        .setLocalTime()
        .build();
  }

  protected String getKey() {
    return String.format("%s_%s", getComponentType(), pineLikerKey);
  }

  protected abstract String getComponentType();
}
