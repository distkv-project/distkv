package com.distkv.dst.common.entity.sortedList;

public class SortedListEntity implements Comparable<SortedListEntity> {
  private int score;

  private String member;

  public SortedListEntity(String member, int score) {
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
  public int compareTo(SortedListEntity o) {
    return o.getScore() - getScore();
  }
}

