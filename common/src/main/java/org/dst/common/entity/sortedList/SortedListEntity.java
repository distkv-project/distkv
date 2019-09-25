package org.dst.common.entity.sortedList;

public class SortedListEntity implements Comparable<SortedListEntity> {
  private int score;

  private String info;

  public SortedListEntity(String info, int score) {
    this.score = score;
    this.info = info;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  @Override
  public int compareTo(SortedListEntity o) {
    return o.getScore() - getScore();
  }
}

