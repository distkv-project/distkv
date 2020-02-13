package com.distkv.core.block;

import com.distkv.common.utils.ByteUtil;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Block it's mainly used to store the data. such as key, value, metadata and so on.
 */
public class Block {

  private final ByteBuffer buffer;
  private final int capacity;
  private int size;
  private long blockId; // may be needed.

  public Block(int capacity) {
    checkArgument(capacity > 0);
    this.capacity = capacity;
    this.buffer = ByteBuffer.allocateDirect(this.capacity);
  }

  public void clean() {
    ((DirectBuffer) buffer).cleaner().clean();
  }

  public byte[] readNonFixedValue(int pointer) {
    checkArgument(pointer <= size);
    // read the value start offset.
    int pointerOffset = pointer << 2;
    buffer.position(pointerOffset);
    int valueStartOffset = buffer.getInt();
    // read the value end offset.
    int valueEndOffset = capacity - 1;
    if (pointer > 0) {
      buffer.position(pointerOffset - ByteUtil.SIZE_OF_INT);
      valueEndOffset = buffer.getInt() - 1;
    }
    // read the value.
    byte[] result = new byte[valueEndOffset - valueStartOffset + 1];
    buffer.position(valueStartOffset);
    buffer.get(result);
    return result;
  }

  public int addNonFixedValue(byte[] value) {
    int pointerOffset = size << 2;
    int valueEndOffset = capacity - 1;
    if (size > 0) {
      buffer.position(pointerOffset - ByteUtil.SIZE_OF_INT);
      valueEndOffset = buffer.getInt() - 1;
    }
    int valueStartOffset = valueEndOffset - value.length + 1;

    if (valueStartOffset > (size + 1) * ByteUtil.SIZE_OF_INT) {
      // write the pointer information
      buffer.position(pointerOffset);
      buffer.putInt(valueStartOffset);
      // write the value.
      buffer.position(valueStartOffset);
      buffer.put(value);
      // adjust the size
      size++;
      // return available size for write.
      return valueStartOffset - pointerOffset + ByteUtil.SIZE_OF_INT;
    } else {
      return -1;
    }
  }

  public int addTwoNonFixedValue(byte[] firstValue, byte[] secondValue) {
    int pointerOffset = size << 2;
    return 0;
  }

  public byte[][] getTwoNonFixedValue(int pointer) {
    return null;
  }

  public byte[] read(int offset, int length) {
    buffer.position(offset);
    byte[] valueBytes = new byte[length];
    buffer.get(valueBytes);
    return valueBytes;
  }

  public void write(int offset, byte[] value) {
    buffer.position(offset);
    buffer.put(value);
  }

  public int getCapacity() {
    return capacity;
  }

  public int getSize() {
    return size;
  }

  public static void main(String[] args) {
    Block block = new Block(20);
    block.addNonFixedValue(new byte[] {123, 23});
    block.addNonFixedValue(new byte[] {12, 23});
    byte[] result1 = block.readNonFixedValue(0);
    System.out.println(result1[0]);
    System.out.println(result1[1]);

    byte[] result2 = block.readNonFixedValue(1);
    System.out.println(result2[0]);
    System.out.println(result2[1]);
  }
}
