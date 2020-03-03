package com.distkv.pine.components.liker;

import java.time.LocalTime;

public class PineLikerKey {
  private String people;

  private String content;

  private LocalTime localTime;

  public String getPeople() {
    return people;
  }

  public void setPeople(String people) {
    this.people = people;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public LocalTime getLocalTime() {
    return localTime;
  }

  public void setLocalTime(LocalTime localTime) {
    this.localTime = localTime;
  }

  @Override
  public String toString() {
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(people);
    stringBuilder.append("_");
    stringBuilder.append(content);
    stringBuilder.append("_");
    stringBuilder.append(localTime);
    return stringBuilder.toString();
  }

  static class Builder {
    private PineLikerKey pineLikerKey;

    public Builder() {
      pineLikerKey = new PineLikerKey();
    }

    public Builder setPeople(String people) {
      pineLikerKey.setPeople(people);
      return this;
    }

    public Builder setContent(String content) {
      pineLikerKey.setContent(content);
      return this;
    }

    public Builder setLocalTime() {
      pineLikerKey.setLocalTime(LocalTime.now());
      return this;
    }

    public PineLikerKey build() {
      return pineLikerKey;
    }
  }
}
