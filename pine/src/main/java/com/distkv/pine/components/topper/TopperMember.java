package com.distkv.pine.components.topper;


/**
 * The class represents a handle of a Topper member.
 */
public class TopperMember {
  private String name;

  private int rankingNum;

  private int score;

  public TopperMember(String name, int rankingNum, int score) {
    this.name = name;
    this.rankingNum = rankingNum;
    this.score = score;
  }



  public int getRankingNum() {
    return rankingNum;
  }

  public String getName() {
    return name;
  }

  public int getScore() {
    return score;
  }

}
