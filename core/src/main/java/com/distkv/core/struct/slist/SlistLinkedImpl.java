package com.distkv.core.struct.slist;

import com.distkv.common.DistkvTuple;
import com.distkv.common.entity.sortedList.SlistEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The linkedlist implementation of the DistKV SortedList.
 */
public final class SlistLinkedImpl
    implements Slist, java.io.Serializable {

  private static final long serialVersionUID = -3486672273827013638L;

  private transient Node<SlistEntity> first;

  private transient Node<SlistEntity> last;

  private transient int size = 0;

  public SlistLinkedImpl() {

  }

  public boolean isEmpty() {
    return (first == null || last == null);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public List<SlistEntity> get() {
    final List<SlistEntity> slists = new ArrayList<>();
    for (Node<SlistEntity> cur = first; cur != null; cur = cur.next) {
      slists.add(cur.item);
    }
    return slists;
  }

  @Override
  public boolean put(
      List<SlistEntity> sortedListEntities) {
    if (hasDuplicatedMembers(sortedListEntities)) {
      return false;
    }
    // Merging sort for linkedlist, Time Complexity: O(n Log n)
    Collections.sort(sortedListEntities);
    this.addAll(sortedListEntities);
    return true;
  }

  @Override
  public void putItem(
      SlistEntity slistEntity) {
    final String nowMember = slistEntity.getMember();
    final int nowScore = slistEntity.getScore();
    final DistkvTuple<Node<SlistEntity>, Integer> tuple =
        this.getItemByMember(nowMember);
    if (tuple != null) {
      // If the member of original SortedList is found, then override the score.
      // Firstly, delete original node whose name is member.
      Node<SlistEntity> now = tuple.getFirst();
      this.deleteNode(now);
    }
    Node<SlistEntity> sortedListEntityNode =
        this.getInsertPosition(nowScore, nowMember);
    this.appendNode(sortedListEntityNode, nowMember, nowScore);
  }

  @Override
  public boolean removeItem(
      String member) {
    final DistkvTuple<Node<SlistEntity>, Integer> tuple =
        this.getItemByMember(member);
    if (tuple == null) {
      return false;
    }
    Node<SlistEntity> now = tuple.getFirst();
    this.deleteNode(now);
    return true;
  }

  @Override
  public int incrScore(
      String member, int delta) {
    final DistkvTuple<Node<SlistEntity>, Integer> tuple =
        this.getItemByMember(member);
    if (tuple == null) {
      return 0;
    }

    Node<SlistEntity> now = tuple.getFirst();
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
    Node<SlistEntity> sortedListEntityNode =
        this.getInsertPosition(afterIncr, member);
    this.appendNode(sortedListEntityNode, member, afterIncr);
    return 1;
  }

  @Override
  public List<SlistEntity> subList(
      int start, int end) {
    final List<SlistEntity> topList = new ArrayList<>();
    int index = 1;
    int nowRank = 1;
    int lastRank = 1;
    boolean isFirst = true;

    for (Node<SlistEntity> cur = first; cur != null; cur = cur.next) {
      lastRank = nowRank;
      if (isFirst) {
        isFirst = false;
        nowRank = index;
      } else {
        if (cur.prev.item.getScore() != cur.item.getScore()) {
          nowRank = index;
        } else {
          nowRank = lastRank;
        }
      }
      if (nowRank >= start && nowRank <= end) {
        topList.add(cur.item);
      }
      if (nowRank > end) {
        break;
      }
      index++;
    }
    return topList;
  }

  @Override
  public DistkvTuple<Integer, Integer> getItem(
      String member) {
    DistkvTuple<Node<SlistEntity>, Integer> tuple = this.getItemByMember(member);
    return tuple == null
        ? null
        : new DistkvTuple<>(tuple.getFirst().item.getScore(), tuple.getSecond());
  }

  private static class Node<E> {

    E item;
    SlistLinkedImpl.Node<E> next;
    SlistLinkedImpl.Node<E> prev;

    Node(SlistLinkedImpl.Node<E> prev,
        E element, SlistLinkedImpl.Node<E> next) {
      this.item = element;
      this.next = next;
      this.prev = prev;
    }
  }

  private void addAll(
      List<SlistEntity> sortedListEntities) {
    for (final SlistEntity e : sortedListEntities) {
      Node<SlistEntity> temp = last;
      Node<SlistEntity> newNode = new Node<>(last, e, null);
      if (first == null) {
        first = newNode;
      } else {
        temp.next = newNode;
      }
      last = newNode;
      size++;
    }
  }

  private DistkvTuple<Node<SlistEntity>, Integer> getItemByMember(
      String member) {
    DistkvTuple<Node<SlistEntity>, Integer> tuple = null;
    int index = 1;
    int nowRank = 1;
    int lastRank = 1;
    boolean isFirst = true;

    for (Node<SlistEntity> cur = first; cur != null; cur = cur.next) {
      lastRank = nowRank;
      if (isFirst) {
        isFirst = false;
        nowRank = index;
      } else {
        if (cur.prev.item.getScore() != cur.item.getScore()) {
          nowRank = index;
        } else {
          nowRank = lastRank;
        }
      }
      if (cur.item.getMember().equals(member)) {
        tuple = new DistkvTuple<>(cur, nowRank);
        break;
      }
      index++;
    }
    return tuple;
  }

  private Node<SlistEntity> getInsertPosition(
      int insertScore, String insertMember) {
    Node<SlistEntity> cur = null;
    if (!isEmpty()) {
      if (this.compares(insertMember, insertScore, last.item) > 0) {
        return null;
      }
      cur = first;
      while (cur != null) {
        if (this.compares(insertMember, insertScore, cur.item) < 0) {
          break;
        }
        cur = cur.next;
      }
    }
    return cur;
  }

  private boolean hasDuplicatedMembers(
      List<SlistEntity> sortedListEntities) {
    final Set<SlistEntity> slistEntitySet = new HashSet<>();
    for (final SlistEntity e : sortedListEntities) {
      if (slistEntitySet.contains(e)) {
        return true;
      }
      slistEntitySet.add(e);
    }
    return false;
  }

  private void appendNode(
      Node<SlistEntity> insertPos, String member, int score) {
    final SlistEntity appendElement = new SlistEntity(member, score);
    Node<SlistEntity> newNode = null;
    if (isEmpty()) {
      newNode = new Node<>(null, appendElement, null);
      first = newNode;
      last = newNode;
    } else if (insertPos == null) {
      Node<SlistEntity> temp = last;
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
      Node<SlistEntity> node) {
    if (isEmpty()) {
      return;
    }
    if (node == first) {
      first = node.next;
      if (first != null) {
        first.prev = null;
      }
    } else if (node == last) {
      last = node.prev;
      if (last != null) {
        last.next = null;
      }
    } else {
      // In this branch, node.prev must be non empty, node.next must be non empty
      node.prev.next = node.next;
      node.next.prev = node.prev;
    }
    // let it gc
    node.prev = null;
    node.next = null;
    node.item = null;
    size--;
  }

  private int compares(
      String insertMember, int insertScore, SlistEntity entity) {
    int compareByScore = insertScore - entity.getScore();
    int compareByMember = insertMember.compareTo(entity.getMember());
    if ((compareByScore < 0) ||
        (compareByScore == 0 && compareByMember > 0)) {
      return 1;
    } else if (compareByScore > 0 ||
        (compareByScore == 0 && compareByMember < 0)) {
      return -1;
    } else {
      return 0;
    }
  }

}
