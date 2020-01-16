package com.distkv.dst.core.struct.slist;

import com.distkv.dst.common.DstTuple;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * The linkedlist implementation of the Dst SortedList.
 */
public final class SortedListLinkedImpl
    extends LinkedList<SortedListEntity>
    implements SortedList, java.io.Serializable {

  private static final long serialVersionUID = -3486672273827013638L;

  private transient Node<SortedListEntity> first;

  private transient Node<SortedListEntity> last;

  private transient int size = 0;

  public SortedListLinkedImpl() {

  }

  public boolean isEmpty() {
    return (first == null || last == null);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean put(
      List<SortedListEntity> sortedListEntities) {
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
      SortedListEntity sortedListEntity) {
    final String nowMember = sortedListEntity.getMember();
    final int nowScore = sortedListEntity.getScore();
    final DstTuple<Node<SortedListEntity>, Integer> tuple =
        this.getItemByMember(nowMember);
    if (tuple != null) {
      // If the member of original SortedList is found, then override the score.
      // Firstly, delete original node whose name is member.
      Node<SortedListEntity> now = tuple.getFirst();
      this.deleteNode(now);
    }
    Node<SortedListEntity> sortedListEntityNode =
        this.getInsertPosition(nowScore, nowMember);
    this.appendNode(sortedListEntityNode, nowMember, nowScore);
  }

  @Override
  public boolean removeItem(
      String member) {
    final DstTuple<Node<SortedListEntity>, Integer> tuple =
        this.getItemByMember(member);
    if (tuple == null) {
      return false;
    }
    Node<SortedListEntity> now = tuple.getFirst();
    this.deleteNode(now);
    return true;
  }

  @Override
  public int incrScore(
      String member, int delta) {
    final DstTuple<Node<SortedListEntity>, Integer> tuple =
        this.getItemByMember(member);
    if (tuple == null) {
      return 0;
    }

    Node<SortedListEntity> now = tuple.getFirst();
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
    Node<SortedListEntity> sortedListEntityNode =
        this.getInsertPosition(afterIncr, member);
    this.appendNode(sortedListEntityNode, member, afterIncr);
    return 1;
  }

  @Override
  public List<SortedListEntity> subList(
      int start, int end) {
    final List<SortedListEntity> topList = new ArrayList<>();
    int index = 1;
    int nowRank = 1;
    int lastRank = 1;
    boolean isFirst = true;

    for (Node<SortedListEntity> cur = first; cur != null; cur = cur.next) {
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
  public DstTuple<Integer, Integer> getItem(
      String member) {
    DstTuple<Node<SortedListEntity>, Integer> tuple = this.getItemByMember(member);
    return tuple == null
        ? null
        : new DstTuple<>(tuple.getFirst().item.getScore(), tuple.getSecond());
  }

  private static class Node<E> {
    E item;
    SortedListLinkedImpl.Node<E> next;
    SortedListLinkedImpl.Node<E> prev;

    Node(SortedListLinkedImpl.Node<E> prev,
         E element, SortedListLinkedImpl.Node<E> next) {
      this.item = element;
      this.next = next;
      this.prev = prev;
    }
  }

  private void addAll(
      List<SortedListEntity> sortedListEntities) {
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

  private DstTuple<Node<SortedListEntity>, Integer> getItemByMember(
      String member) {
    DstTuple<Node<SortedListEntity>, Integer> tuple = null;
    int index = 1;
    int nowRank = 1;
    int lastRank = 1;
    boolean isFirst = true;

    for (Node<SortedListEntity> cur = first; cur != null; cur = cur.next) {
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
        tuple = new DstTuple<>(cur, nowRank);
        break;
      }
      index++;
    }
    return tuple;
  }

  private Node<SortedListEntity> getInsertPosition(
      int insertScore, String insertMember) {
    Node<SortedListEntity> cur = null;
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
      // let it gc
      node.prev = null;
      node.next = null;
      node.item = null;
      size--;
    }
  }

  private int compares(
      String insertMember, int insertScore, SortedListEntity entity) {
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
