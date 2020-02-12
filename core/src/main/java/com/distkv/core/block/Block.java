package com.distkv.core.block;

import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkArgument;

public class Block {

  private final ByteBuffer buffer;
  private final int capacity;
  private final AtomicInteger allocated = new AtomicInteger(0);

  public Block(int capacity) {
    checkArgument(capacity > 0);
    this.capacity = capacity;
    this.buffer = ByteBuffer.allocateDirect(this.capacity);
  }

  public void clean() {
    ((DirectBuffer) buffer).cleaner().clean();
  }

  /**
   * allocate slice from buffer for store data.
   * @param size the size to allocated
   * @return return the slice of buffer.
   */
  ByteBuffer allocate(int size) {
    int now = allocated.getAndAdd(size);
    if (now + size > capacity) {
      // memory not enough
      allocated.addAndGet(-size);
      return null;
    } else {
      ByteBuffer allocatedBuffer = buffer.duplicate();
      allocatedBuffer.limit(now + size);
      allocatedBuffer.position(now);
      return allocatedBuffer;
    }
  }

  public byte[] read(int offset, int length) {
    if (offset + length < allocated.get()) {
      return null;
    }
    buffer.position(offset);
    buffer.limit(offset + length);
    byte[] valueBytes = new byte[length];
    buffer.put(valueBytes);
    return valueBytes;
  }

  int allocated() {
    return allocated.get();
  }

  int available() {
    return capacity - allocated.get();
  }

  int getCapacity() {
    return capacity;
  }

  public void write(int offset, byte[] value) {
  }
}
