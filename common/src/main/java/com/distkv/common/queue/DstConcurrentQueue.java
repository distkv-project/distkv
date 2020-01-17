package com.distkv.common.queue;

public interface DstConcurrentQueue<E> {

  /**
   * Inserts the specified element into this queue if it is possible to do so
   * immediately without violating capacity restrictions.
   *
   * @param e The element to be added.
   * @return Whether the element is inserted.
   */
  boolean add(E e);

  /**
   * Inserts the specified element into this queue if it is possible to do
   * so immediately without violating capacity restrictions.
   *
   * @param e The element to be added.
   * @return Whether the element is inserted.
   */
  boolean offer(E e);

  /**
   * Retrieves and removes the head of this queue.
   *
   * @return The head of this queue.
   */
  E remove();

  /**
   * Retrieves and removes the head of this queue,
   * or returns  null if this queue is empty.
   *
   * @return The head of this queue, or  null if this queue is empty.
   */
  E poll();

  /**
   * Retrieves, but does not remove, the head of this queue.
   *
   * @return The head of this queue, or null if this queue is empty.
   */
  E peek();

  /**
   * Returns true if this queue contains no elements.
   *
   * @return True if this queue contains no elements.
   */
  boolean isEmpty();

  /**
   * Returns the number of elements in this queue.
   *
   * @return The number of elements in this queue.
   */
  int size();

}
