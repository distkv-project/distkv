package com.distkv.dst.common.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class DefaultConcurrentQueue<E> implements DstConcurrentQueue<E> {
  private ConcurrentLinkedQueue<E> concurrentLinkedQueue;

  public DefaultConcurrentQueue() {
    this.concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
  }

  @Override
  public boolean add(E e) {
    return concurrentLinkedQueue.add(e);
  }

  @Override
  public boolean offer(E e) {
    return concurrentLinkedQueue.offer(e);
  }

  @Override
  public E remove() {

    return concurrentLinkedQueue.remove();
  }

  @Override
  public E poll() {
    return concurrentLinkedQueue.poll();
  }

  @Override
  public E peek() {
    return concurrentLinkedQueue.peek();
  }

  @Override
  public boolean isEmpty() {
    return concurrentLinkedQueue.isEmpty();
  }

  @Override
  public int size() {
    return concurrentLinkedQueue.size();
  }
}
