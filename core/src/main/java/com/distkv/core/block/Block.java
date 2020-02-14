package com.distkv.core.block;

import com.distkv.common.utils.ByteUtil;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Block it's mainly used to store the data. such as key, value, metadata and so on.
 *
 * for non-fixed length data. stored as blew format. the pointer is integer type.
 * | pointer1 | pointer2 | ...... | data2 | data1 |
 * the pointer be used to quick find the data start offset and length.
 *
 * for non-fixed length data with key and value. stored as blew format.
 * | key1 pointer | value1 pointer |  ...... | value1 data | key1 data |
 *
 * for fixed length data
 * | data1 | data2 | data3 | data4 | ...... |
 *
 */
public class Block {

  private final ByteBuffer buffer;
  private final int capacity;
  private int size; // only used by non fixed value.
  private long blockId; // may be needed.

  public Block(int capacity) {
    checkArgument(capacity > 0);
    this.capacity = capacity;
    this.buffer = ByteBuffer.allocateDirect(this.capacity);
  }

  public void clean() {
    ((DirectBuffer) buffer).cleaner().clean();
  }

  public byte[] read(int offset, int length) {
    buffer.position(offset);
    byte[] valueBytes = new byte[length];
    buffer.get(valueBytes);
    return valueBytes;
  }

  public byte[] readNonFixedValue(int pointer) {
    checkArgument(pointer < size);
    // read the value start offset.
    int pointerOffset = pointer << 2;
    buffer.position(pointerOffset);
    int valueStartOffset = buffer.getInt();

    // read the value end offset.
    int valueEndOffset = calcValueEndOffset(pointerOffset);

    // read the value.
    byte[] result = new byte[valueEndOffset - valueStartOffset + 1];
    buffer.position(valueStartOffset);
    buffer.get(result);
    return result;
  }

  public byte[][] readTwoNonFixedValues(int pointer) {
    checkArgument(pointer <= size / 2);
    byte[][] keyValue = new byte[2][];
    // read  the key start offset
    int keyPointerOffset = pointer << 2;
    buffer.position(keyPointerOffset);
    int keyStartOffset = buffer.getInt();
    int valueStartOffset = buffer.getInt();

    // read the key and value
    int keyEndOffset = calcNonFixedValueEndOffset(keyPointerOffset);
    keyValue[0] = new byte[keyEndOffset - keyStartOffset + 1];
    buffer.position(keyStartOffset);
    buffer.get(keyValue[0]);
    keyValue[1] = new byte[keyStartOffset - valueStartOffset];
    buffer.position(valueStartOffset);
    buffer.get(keyValue[1]);
    return keyValue;
  }

  public void write(int offset, byte[] value) {
    buffer.position(offset);
    buffer.put(value);
  }


  public int addNonFixedValue(byte[] value) {
    int pointerOffset = size << 2;
    int valueEndOffset = calcValueEndOffset(pointerOffset);
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
      return valueStartOffset - (pointerOffset + ByteUtil.SIZE_OF_INT);
    } else {
      return -1;
    }
  }

  public int addTwoNonFixedValue(byte[] firstValue, byte[] secondValue) {
    int keyPointerOffset = size << 2;
    int keyEndOffset = calcNonFixedValueEndOffset(keyPointerOffset);
    int valueStartOffset = keyEndOffset - firstValue.length - secondValue.length + 1;
    if (valueStartOffset > (size + 1) * ByteUtil.SIZE_OF_LONG) {
      // write the pointer information.
      buffer.position(keyPointerOffset);
      buffer.putInt(valueStartOffset + secondValue.length);
      buffer.putInt(valueStartOffset);

      // write the second value and first value
      buffer.position(valueStartOffset);
      buffer.put(secondValue);
      buffer.put(firstValue);
      size = size + 2;
      return valueStartOffset - keyPointerOffset - (ByteUtil.SIZE_OF_LONG);
    } else {
      return -1;
    }

  }

  private int calcValueEndOffset(int pointerOffset) {
    int valueEndOffset = capacity - 1;
    if (pointerOffset > 0) {
      buffer.position(pointerOffset - ByteUtil.SIZE_OF_INT);
      valueEndOffset = buffer.getInt() - 1;
    }
    return valueEndOffset;
  }

  private int calcNonFixedValueEndOffset(int pointerOffset) {
    int valueEndOffset = capacity - 1;
    if (pointerOffset > 0) {
      buffer.position(pointerOffset - ByteUtil.SIZE_OF_LONG);
      valueEndOffset = buffer.getInt() - 1;
    }
    return valueEndOffset;
  }

  public int getCapacity() {
    return capacity;
  }

  public int getSize() {
    return size;
  }

  public static void main(String[] args) {

  }
}
