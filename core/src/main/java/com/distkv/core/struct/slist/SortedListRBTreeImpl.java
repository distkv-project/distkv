package com.distkv.core.struct.slist;

import com.distkv.common.DistKVTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;

import com.google.common.collect.TreeMultimap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class SortedListRBTreeImpl
    implements SortedList, java.io.Serializable {

  private static final long serialVersionUID = -7011173289234009149L;

  private TreeMultimap<Integer, String> scoreMemberMap;

  private HashMap<String, Integer> memberScoreMap;

  private static final Comparator<Integer> scoreComparator = (i1, i2) -> (i2 - i1);

  private transient int size;

  public SortedListRBTreeImpl() {
    this.size = 0;
    this.scoreMemberMap = TreeMultimap.create(scoreComparator, Comparator.naturalOrder());
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
    final Integer score = memberScoreMap.get(member);
    if (score == null) {
      return false;
    }

    scoreMemberMap.remove(score, member);
    memberScoreMap.remove(member);
    size--;
    return true;
  }

  @Override
  public int incrScore(String member, int delta) {
    if (!this.containsMember(member)) {
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
          nowRank = index;
        }
      }
      lastScore = entry.getKey();

      if (nowRank >= start && nowRank <= end) {
        topList.add(new SortedListEntity(nowMember, nowScore));
      }
      if (nowRank > end) {
        break;
      }
      index++;
    }
    return topList;
  }

  @Override
  public DistKVTuple<Integer, Integer> getItem(String member) {
    DistKVTuple<Integer, Integer> distKVTuple = null;
    if (containsMember(member)) {
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
            nowRank = index;
          }
        }
        lastScore = nowScore;

        if (nowMember.equals(member)) {
          distKVTuple = new DistKVTuple<>(nowScore, nowRank);
          break;
        }
        index++;
      }
    }
    return distKVTuple;
  }

  private boolean containsMember(String member) {
    return memberScoreMap.containsKey(member);
  }

  /**
   * Check whether the given list has duplicated members.
   *
   * @param sortedListEntities The list value
   * @return True if the list has duplicated members, otherwise return false.
   */
  private boolean hasDuplicatedMembers(
      List<SortedListEntity> sortedListEntities) {
    final Set<SortedListEntity> sortedListEntitySet = new HashSet<>();
    for (final SortedListEntity e : sortedListEntities) {
      if (sortedListEntitySet.contains(e)) {
        return true;
      }
      sortedListEntitySet.add(e);
    }
    return false;
  }
}
