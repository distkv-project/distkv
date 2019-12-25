package com.distkv.dst.core.struct.slist;

import com.distkv.dst.common.entity.sortedList.SortedListEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class SortedListLinkedListImpl
    extends LinkedList<SortedListEntity>
    implements SortedList, java.io.Serializable {

  private static final long serialVersionUID = -3486672273827013638L;

  private transient Node<SortedListEntity> first;

  private transient Node<SortedListEntity> last;

  private transient int size = 0;

  public SortedListLinkedListImpl() {

  }

  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public boolean put(List<SortedListEntity> sortedListEntities) {
    if (hasDuplicatedMembers(sortedListEntities)) {
      return false;
    }
    ArrayList<SortedListEntity> entities = new ArrayList<>(sortedListEntities);
    Collections.sort(entities);
    this.addAll(entities);
    return true;
  }

  @Override
  public void putItem(SortedListEntity sortedListEntity) {
    final LeaderboardItem leaderboardItem =
        this.getItemByMember(sortedListEntity.getMember());
    final String nowMember = sortedListEntity.getMember();
    final int nowScore = sortedListEntity.getScore();
    Node<SortedListEntity> node;
    if ((node = leaderboardItem.entityNode) != null) {
      node.item.setScore(nowScore);
    } else {
      Node<SortedListEntity> now = this.getInsertPosition(nowScore);
      this.appendNode(now, nowMember, nowScore);
    }
  }

  @Override
  public boolean removeItem(String member) {
    LeaderboardItem leaderboardItem = getItemByMember(member);
    if (leaderboardItem == null) {
      return false;
    }
    Node<SortedListEntity> now = leaderboardItem.entityNode;
    this.deleteNode(now);
    return true;
  }

  @Override
  public int incrScore(String member, int delta) {
    LeaderboardItem leaderboardItem = getItemByMember(member);
    if (leaderboardItem == null) {
      return 0;
    }

    Node<SortedListEntity> now = leaderboardItem.entityNode;
    final int nowScore = now.item.getScore();
    // Check if there is outing of range after increase the score:
    //     Case 1: The score will more than Integer.MAX_VALUE when delta is positive
    //     Case 2: The score will less than Integer.MIN_VALUE when delta is negative
    if ((delta > 0 && nowScore > Integer.MAX_VALUE - delta) ||
        (delta < 0 && nowScore < Integer.MIN_VALUE - delta)) {
      return -1;
    }

    final int afterIncr = nowScore + delta;
    this.deleteNode(now);
    Node<SortedListEntity> sortedListEntityNode = this.getInsertPosition(afterIncr);
    this.appendNode(sortedListEntityNode, member, afterIncr);
    return 1;
  }

  @Override
  public List<SortedListEntity> subList(int start, int end) {
    int nowRanking = 1;
    Node<SortedListEntity> cur = first;
    List<SortedListEntity> topList = new ArrayList<>();
    while (cur != null) {
      if (nowRanking >= start && nowRanking <= end) {
        topList.add(cur.item);
      }
      if (nowRanking > end) {
        break;
      }
      nowRanking++;
      cur = cur.next;
    }
    return topList;
  }

  @Override
  public List<Integer> getItem(String member) {
    LeaderboardItem leaderboardItem = this.getItemByMember(member);
    return leaderboardItem == null
        ? null
        : Arrays.asList(leaderboardItem.entityNode.item.getScore(), leaderboardItem.ranking);
  }

  private static class Node<E> {
    E item;
    SortedListLinkedListImpl.Node<E> next;
    SortedListLinkedListImpl.Node<E> prev;

    Node(SortedListLinkedListImpl.Node<E> prev,
         E element, SortedListLinkedListImpl.Node<E> next) {
      this.item = element;
      this.next = next;
      this.prev = prev;
    }
  }

  private static class LeaderboardItem {
    Node<SortedListEntity> entityNode;
    int ranking;

    public LeaderboardItem(Node<SortedListEntity> sortedListEntity,
                           int ranking) {
      this.entityNode = sortedListEntity;
      this.ranking = ranking;
    }
  }

  private void addAll(List<SortedListEntity> sortedListEntities) {
    for (final SortedListEntity e : sortedListEntities) {
      Node<SortedListEntity> temp = last;
      Node<SortedListEntity> newNode = new Node<>(last, e, null);
      if (first == null) {
        first = newNode;
      } else {
        temp.next = newNode;
      }
      last = newNode;
      size++;
    }
  }

  private LeaderboardItem getItemByMember(String member) {
    LeaderboardItem leaderboardItem = null;
    int ranking = 1;
    for (Node<SortedListEntity> cur = first;
         cur != null; cur = cur.next) {
      if (cur.item.getMember().equals(member)) {
        leaderboardItem = new LeaderboardItem(cur, ranking);
        break;
      }
      ranking++;
    }
    return leaderboardItem;
  }

  private Node<SortedListEntity> getInsertPosition(
      int insertScore) {
    Node<SortedListEntity> cur = null;
    if (!isEmpty()) {
      if (insertScore < last.item.getScore()) {
        return null;
      }
      cur = first;
      while (cur != null && cur.next != null) {
        if (cur.next.item.getScore() <= insertScore) {
          break;
        }
        cur = cur.next;
      }
    }
    return cur;
  }

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

  private void appendNode(
      Node<SortedListEntity> insertPos, String member, int score) {
    final SortedListEntity appendElement = new SortedListEntity(member, score);
    Node<SortedListEntity> newNode = null;
    if (isEmpty()) {
      newNode = new Node<>(null, appendElement, null);
      first = newNode;
      last = newNode;
    } else if (insertPos == null) {
      Node<SortedListEntity> temp = last;
      last = new Node<>(temp, appendElement, null);
      temp.next = last;
    } else {
      newNode = new Node<>(insertPos.prev, appendElement, insertPos);
      if (insertPos.prev == null) {
        first = newNode;
        newNode.next = insertPos;
      } else {
        insertPos.prev.next = newNode;
      }
      insertPos.prev = newNode;
    }
    size++;
  }

  private void deleteNode(
      Node<SortedListEntity> node) {
    if (!isEmpty()) {
      if (node == first) {
        first = node.next;
        first.prev = null;
      } else if (node == last) {
        last = node.prev;
        last.next = null;
      } else {
        node.prev.next = node.next;
        node.next.prev = node.prev;
      }
      node.prev = null;
      node.next = null;
      node.item = null;
      size--;
    }
  }
}
