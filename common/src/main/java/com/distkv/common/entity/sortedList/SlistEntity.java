package com.distkv.common.entity.sortedList;

public class SlistEntity implements Comparable<SlistEntity> {
  private int score;

  private String member;

  public SlistEntity(String member, int score) {
    this.score = score;
    this.member = member;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String getMember() {
    return member;
  }

  public void setMember(String info) {
    this.member = info;
  }

  @Override
  public int compareTo(SlistEntity o) {
    if (o.getScore() != getScore()) {
      return o.getScore() - getScore();
    }
    return getMember().compareTo(o.getMember());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SlistEntity anotherEntity = (SlistEntity) o;
    return member.equals(anotherEntity.getMember());
  }

  @Override
  public int hashCode() {
    return member.hashCode();
  }
}
