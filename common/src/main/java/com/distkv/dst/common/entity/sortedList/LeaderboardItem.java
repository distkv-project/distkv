package com.distkv.dst.common.entity.sortedList;

public class LeaderboardItem {
  private SortedListEntity sortedListEntity;
  private int ranking;

  public LeaderboardItem() {

  }

  public LeaderboardItem(String member, int score, int ranking) {
    this.sortedListEntity = new SortedListEntity(member, score);
    this.ranking = ranking;
  }

  public LeaderboardItem(SortedListEntity sortedListEntity, int ranking) {
    this.sortedListEntity = sortedListEntity;
    this.ranking = ranking;
  }

  public String getMember() {
    return sortedListEntity.getMember();
  }

  public int getScore() {
    return sortedListEntity.getScore();
  }

  public int getRanking() {
    return ranking;
  }

}
