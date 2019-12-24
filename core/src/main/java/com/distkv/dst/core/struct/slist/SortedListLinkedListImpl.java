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
    return (first == null && last == null);
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
    Node<SortedListEntity> node;
    if ((node = isExistByMember(sortedListEntity.getMember())) != null) {
      node.item.setScore(sortedListEntity.getScore());
    } else {
      size++;
      Node<SortedListEntity> now = this.getInsertPosition(sortedListEntity);
      this.appendNode(now, sortedListEntity);
    }
  }

  @Override
  public boolean removeItem(String member) {
    Leaderboard leaderboard = getNodeByMember(member);
    if (leaderboard == null) {
      return false;
    }
    size--;
    Node<SortedListEntity> now = leaderboard.entityNode;
    this.deleteNode(now);
    return true;
  }

  @Override
  public int incrScore(String member, int delta) {
    Leaderboard leaderboard = getNodeByMember(member);
    if (leaderboard == null) {
      return 0;
    }

    Node<SortedListEntity> now = leaderboard.entityNode;
    int nowScore = now.item.getScore();
    // Check if there is outing of range after increase the score:
    //     Case 1: The score will more than Integer.MAX_VALUE when delta is positive
    //     Case 2: The score will less than Integer.MIN_VALUE when delta is negative
    if ((delta > 0 && nowScore > Integer.MAX_VALUE - delta) ||
        (delta < 0 && nowScore < Integer.MIN_VALUE - delta)) {
      return -1;
    }

    now.item.setScore(nowScore + delta);
    this.deleteNode(now);
    Node<SortedListEntity> sortedListEntityNode = this.getInsertPosition(now.item);
    this.appendNode(sortedListEntityNode, now.item);
    return 1;
  }

  @Override
  public List<SortedListEntity> subList(int start, int end) {
    int index = 0;
    Node<SortedListEntity> cur = first;
    List<SortedListEntity> topList = new ArrayList<>();
    while (cur != null) {
      if (index >= start && index <= end) {
        topList.add(cur.item);
      }
      if (index > end) {
        break;
      }
      index++;
      cur = cur.next;
    }
    return topList;
  }

  @Override
  public List<Integer> getItem(String member) {
    Leaderboard leaderboard = this.getNodeByMember(member);
    return leaderboard == null
        ? null
        : Arrays.asList(leaderboard.entityNode.item.getScore(), leaderboard.ranking);
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

  private static class Leaderboard {
    Node<SortedListEntity> entityNode;
    int ranking;

    public Leaderboard(Node<SortedListEntity> sortedListEntity,
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

  private Leaderboard getNodeByMember(String member) {
    Leaderboard leaderboard = null;
    int ranking = 1;
    for (Node<SortedListEntity> cur = first;
         cur != null; cur = cur.next) {
      if (cur.item.getMember().equals(member)) {
        leaderboard = new Leaderboard(cur, ranking);
        break;
      }
      ranking++;
    }
    return leaderboard;
  }

  private Node<SortedListEntity> getInsertPosition(
      SortedListEntity insertElement) {
    Node<SortedListEntity> cur = null;
    if (!isEmpty()) {
      final int insertScore = insertElement.getScore();
      if (insertScore < last.item.getScore()) {
        return last;
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

  private Node<SortedListEntity> isExistByMember(
      String member) {
    Node<SortedListEntity> cur;
    for (cur = first; cur != null; cur = cur.next) {
      if (cur.item.getMember().equals(member)) {
        break;
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
      Node<SortedListEntity> insertPos, SortedListEntity appendElement) {
    Node<SortedListEntity> newNode = null;
    if (isEmpty()) {
      newNode = new Node<>(null, appendElement, null);
      first = newNode;
      last = newNode;
    } else {
      newNode = new Node<>(insertPos.prev, appendElement, insertPos);
      if (insertPos.prev == null) {
        first = newNode;
      } else {
        insertPos.prev.next = newNode;
      }
      insertPos.prev = newNode;
    }
  }

  private void deleteNode(
      Node<SortedListEntity> node) {
    if (!isEmpty()) {
      if (node == first) {
        first = node;
        node.prev = null;
      } else if (node == last) {
        last = node;
        node.next = null;
      } else {
        node.prev.next = node.next;
        node.next.prev = node.prev;
      }
    }
  }
}
