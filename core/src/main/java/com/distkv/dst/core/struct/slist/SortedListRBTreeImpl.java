package com.distkv.dst.core.struct.slist;

import com.distkv.dst.common.DstTuple;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;

import com.google.common.collect.TreeMultimap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class SortedListRBTreeImpl
    implements SortedList, java.io.Serializable {

  private static final long serialVersionUID = -7011173289234009149L;

  private TreeMultimap<Integer, String> scoreMemberMap;

  private HashMap<String, Integer> memberScoreMap;

  private transient int size;

  public SortedListRBTreeImpl() {
    this.size = 0;
    this.scoreMemberMap = TreeMultimap.create();
    this.memberScoreMap = new HashMap<>();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean put(List<SortedListEntity> entities) {
    if (hasDuplicatedMembers(entities)) {
      return false;
    }

    for (SortedListEntity entity : entities) {
      this.putItem(entity);
    }
    return true;
  }

  @Override
  public void putItem(SortedListEntity entity) {
    scoreMemberMap.put(entity.getScore(), entity.getMember());
    memberScoreMap.put(entity.getMember(), entity.getScore());
    size++;
  }

  @Override
  public boolean removeItem(String member) {
    if (!this.hasMember(member)) {
      return false;
    }

    final Integer score = memberScoreMap.get(member);
    scoreMemberMap.remove(score, member);
    memberScoreMap.remove(member);
    size--;
    return true;
  }

  @Override
  public int incrScore(String member, int delta) {
    if (!this.hasMember(member)) {
      return 0;
    }

    final int nowScore = memberScoreMap.get(member);
    // Check if there is outing of range after increase the score:
    //     Case 1: The score will more than Integer.MAX_VALUE when delta is positive
    //     Case 2: The score will less than Integer.MIN_VALUE when delta is negative
    if ((delta > 0 && nowScore > Integer.MAX_VALUE - delta) ||
        (delta < 0 && nowScore < Integer.MIN_VALUE - delta)) {
      return -1;
    }

    final int afterIncr = nowScore + delta;
    scoreMemberMap.remove(nowScore, member);
    memberScoreMap.remove(member);

    scoreMemberMap.put(afterIncr, member);
    memberScoreMap.put(member, afterIncr);
    return 1;
  }

  @Override
  public List<SortedListEntity> subList(int start, int end) {
    final List<SortedListEntity> topList = new ArrayList<>();
    int index = 1;
    int nowRank = 1;
    int lastRank = 1;
    int lastScore = 1;
    boolean isFirst = true;

    for (final Entry<Integer, String> entry : scoreMemberMap.entries()) {
      final String nowMember = entry.getValue();
      final int nowScore = entry.getKey();

      if (isFirst) {
        isFirst = false;
        nowRank = index;
      } else {
        lastRank = nowRank;
        if (lastScore == nowScore) {
          nowRank = lastRank;
        } else {
          nowRank = lastRank + 1;
        }
      }
      lastScore = entry.getKey();

      if (nowRank >= start && nowRank <= end) {
        topList.add(new SortedListEntity(nowMember, nowScore));
      }
      if (nowRank > end) {
        break;
      }
    }
    return topList;
  }

  @Override
  public DstTuple<Integer, Integer> getItem(String member) {
    DstTuple<Integer, Integer> dstTuple = null;
    if (hasMember(member)) {
      int index = 1;
      int nowRank = 1;
      int lastRank = 1;
      int lastScore = 1;
      boolean isFirst = true;

      for (final Entry<Integer, String> entry : scoreMemberMap.entries()) {
        final String nowMember = entry.getValue();
        final int nowScore = entry.getKey();

        if (isFirst) {
          isFirst = false;
          nowRank = index;
        } else {
          lastRank = nowRank;
          if (lastScore == nowScore) {
            nowRank = lastRank;
          } else {
            nowRank = lastRank + 1;
          }
        }
        lastScore = nowScore;

        if (nowMember.equals(member)) {
          dstTuple = new DstTuple<>(nowScore, nowRank);
          break;
        }
      }
    }
    return dstTuple;
  }

  private boolean hasMember(String member) {
    return memberScoreMap.containsKey(member);
  }
}
