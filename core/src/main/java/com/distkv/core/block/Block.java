package com.distkv.core.block;

import com.distkv.common.utils.ByteUtil;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Block it's mainly used to store the data. such as key, value, metadata and so on.
 * <p>
 * for non-fixed length data. stored as blew format. the pointer is integer type.
 * | pointer1 | pointer2 | ...... | data2 | data1 |
 * the pointer be used to quick find the data start offset and length.
 * <p>
 * for non-fixed length data with key and value. stored as blew format.
 * | key1 pointer | value1 pointer |  ...... | value1 data | key1 data |
 * <p>
 * for fixed length data
 * | data1 | data2 | data3 | data4 | ...... |
 */
public class Block {
  // the direct memory buffer is used to store data.
  private final ByteBuffer buffer;
  private final int capacity;
  // the number of value, only used by non fixed value.
  private int size;
  private int nextWriteOffset;
  // may be needed.
  private long blockId;

  public Block(int capacity) {
    checkArgument(capacity > 0);
    this.capacity = capacity;
    this.buffer = ByteBuffer.allocateDirect(this.capacity);
  }

  public void clean() {
    ((DirectBuffer) buffer).cleaner().clean();
  }

  public byte[] read(int offset, int length) {
    byte[] valueBytes = new byte[length];
    buffer.position(offset);
    buffer.get(valueBytes);
    return valueBytes;
  }

  /**
   * Read the value by offset and length.
   *
   * @param offset where begin to read.
   * @param length the length to read
   * @return the result readed.
   */
  public byte[] readValue(int offset, int length) {
    byte[] value = new byte[length];
    buffer.position(offset);
    buffer.get(value);
    return value;
  }

  /**
   * read short value from given index.
   * need to used with {@link Block#write(int, short)}
   *
   * @param offset the index of short value.
   * @return the read value.
   */
  public short readShort(int offset) {
    // convert short index to byte index of buffer for bytes where to read.
    offset = offset << 1;
    return buffer.getShort(offset);
  }

  /**
   * read int value from given index.
   * need to used with {@link Block#write(int, int)}
   *
   * @param offset the index of int value.
   * @return the read value.
   */
  public int readInt(int offset) {
    // convert int index to byte index of buffer for bytes where to read.
    offset = offset << 2;
    return buffer.getInt(offset);
  }

  /**
   * read long value from given index.
   * need to used with {@link Block#write(int, long)}
   *
   * @param offset the index of long value.
   * @return the read value.
   */
  public long readLong(int offset) {
    // convert long index to byte index of buffer for bytes where to read.
    offset = offset << 4;
    return buffer.getLong(offset);
  }

  /**
   * read double value from given index.
   * need to used with {@link Block#write(int, double)}
   *
   * @param offset the index of double value.
   * @return the read value.
   */
  public double readDouble(int offset) {
    // convert double index to byte index of buffer for bytes where to read.
    offset = offset << 4;
    return buffer.getDouble(offset);
  }

  /**
   * read float value from given index.
   * need to used with {@link Block#write(int, float)}
   *
   * @param offset the index of float value.
   * @return the read value.
   */
  public float readFloat(int offset) {
    // convert float index to byte index of buffer for bytes where to read.
    offset = offset << 4;
    return buffer.getFloat(offset);
  }

  /**
   * read the non fixed value from given pointer.
   * need to used with {@link Block#writeNonFixedValue(byte[])}
   *
   * @param pointer indexed of the value to read.
   * @return the read value.
   */
  public byte[] readNonFixedValue(int pointer) {
    checkArgument(pointer < size);
    // read the value start offset.
    int pointerOffset = pointer << 2;
    int valueStartOffset = buffer.getInt(pointerOffset);

    // read the value end offset.
    int valueEndOffset = calcValueEndOffset(pointerOffset);

    // read the value.
    byte[] result = new byte[valueEndOffset - valueStartOffset + 1];
    buffer.position(valueStartOffset);
    buffer.get(result);

    return result;
  }

  /**
   * read the two non fixed values(such as key value user case.) from given pointer.
   * need to used with {@link Block#writeTwoNonFixedValue(byte[], byte[])}
   *
   * @param pointer indexed of to the value to read.
   * @return the read value.
   */
  public byte[][] readTwoNonFixedValues(int pointer) {
    checkArgument(pointer <= size / 2);
    byte[][] keyValue = new byte[2][];
    // read  the key start offset
    int keyPointerOffset = pointer << 2;
    buffer.position(keyPointerOffset);
    int keyStartOffset = buffer.getInt();
    int valueStartOffset = buffer.getInt();

    // read the key.
    int keyEndOffset = calcValueEndOffset(keyPointerOffset);
    keyValue[0] = new byte[keyEndOffset - keyStartOffset + 1];
    buffer.position(keyStartOffset);
    buffer.get(keyValue[0]);

    // read the value
    keyValue[1] = new byte[keyStartOffset - valueStartOffset];
    buffer.position(valueStartOffset);
    buffer.get(keyValue[1]);

    // return the result.
    return keyValue;
  }

  public void write(int offset, byte[] value) {
    buffer.position(offset);
    buffer.put(value);
  }


  /**
   * write the short value to given index.
   * need to used with {@link Block#readShort(int)}
   *
   * @param offset the index of short value.
   */
  public void write(int offset, short value) {
    // convert short index to byte index of buffer for bytes where to read.
    offset = offset << 1;
    buffer.putShort(offset, value);
  }

  /**
   * write the int value to given index.
   * need to used with {@link Block#readInt(int)}
   *
   * @param offset the index of int value.
   */
  public void write(int offset, int value) {
    // convert int index to byte index of buffer for bytes where to read.
    offset = offset << 2;
    buffer.putInt(offset, value);
  }

  /**
   * write the long value to given index.
   * need to used with {@link Block#readLong(int)}
   *
   * @param offset the index of long value.
   */
  public void write(int offset, long value) {
    // convert long index to byte index of buffer for bytes where to read.
    offset = offset << 4;
    buffer.putLong(offset, value);
  }

  /**
   * write the double value to given index.
   * need to used with {@link Block#readDouble(int)}
   *
   * @param offset the index of double value.
   */
  public void write(int offset, double value) {
    // convert double index to byte index of buffer for bytes where to read.
    offset = offset << 4;
    buffer.putDouble(offset, value);
  }

  /**
   * write the float value to given index.
   * need to used with {@link Block#readFloat(int)}
   *
   * @param offset the index of float value.
   */
  public void write(int offset, float value) {
    // convert float index to byte index of buffer for bytes where to read.
    offset = offset << 4;
    buffer.putFloat(offset, value);
  }

  public int writeValue(byte[] value) {
    return writeValue(value, 0);
  }

  /**
   * write the value to block
   *
   * @param value  the value to write
   * @param offset the offset of the value to write.
   * @return the remaining byte to write.
   */
  public int writeValue(byte[] value, int offset) {
    checkArgument(nextWriteOffset < this.capacity);
    // not support for now
    checkArgument(value.length < this.capacity);

    int remaining = this.capacity - nextWriteOffset;
    int length = value.length - offset;
    buffer.position(nextWriteOffset);
    if (remaining < length) {
      // no enough remaining space to store the value.
      buffer.put(value, offset, remaining);
      nextWriteOffset = this.capacity;
      return length - remaining;
    } else {
      buffer.put(value, offset, length);
      nextWriteOffset = nextWriteOffset + length;
      return 0;
    }
  }

  /**
   * write the non fixed value
   * need to used with {@link Block#readLong(int)}
   *
   * @return available space to write.
   */
  public int writeNonFixedValue(byte[] value) {
    // convert int index to byte index of buffer for bytes where to read,
    // because key pointer is integer type
    int pointerOffset = size << 2;

    // for non-fixed length data. stored as blew format. the pointer is integer type.
    // | pointer1 | pointer2 | ...... | data2 | data1 |
    int valueEndOffset = calcValueEndOffset(pointerOffset);
    int valueStartOffset = valueEndOffset - value.length + 1;

    if (valueStartOffset >= (size + 1) * ByteUtil.SIZE_OF_INT) {
      // write the pointer information
      buffer.putInt(pointerOffset, valueStartOffset);
      // write the value.
      buffer.position(valueStartOffset);
      buffer.put(value);
      // adjust the size
      size++;
      // return available size for write.
      return valueStartOffset - pointerOffset - ByteUtil.SIZE_OF_INT;
    } else {
      // no available space to store two non fixed value.
      return -1;
    }
  }

  /**
   * write the two non fixed values (such as key value user case).
   * need to used with {@link Block#readTwoNonFixedValues(int)}
   *
   * @return available space to write.
   */
  public int writeTwoNonFixedValue(byte[] firstValue, byte[] secondValue) {
    // convert int index to byte index of buffer for bytes where to read,
    // because key pointer is integer type
    int keyPointerOffset = size << 2;

    // for non-fixed length data with key and value. stored as blew format.
    // | key1 pointer | value1 pointer |  ...... | value1 data | key1 data |
    int valueEndOffset = calcValueEndOffset(keyPointerOffset);
    int valueStartOffset = valueEndOffset - firstValue.length - secondValue.length + 1;

    // Does it have available space to store firstValue, secondValue and their pointers
    if (valueStartOffset >= size * ByteUtil.SIZE_OF_INT + ByteUtil.SIZE_OF_LONG) {

      // write the pointer information.
      buffer.position(keyPointerOffset);
      // key pointer
      buffer.putInt(valueStartOffset + secondValue.length);
      // value pointer
      buffer.putInt(valueStartOffset);

      // write the second value and first value
      buffer.position(valueStartOffset);
      buffer.put(secondValue);
      buffer.put(firstValue);

      // adjust the size;
      size = size + 2;
      // return available space.
      return valueStartOffset - keyPointerOffset - ByteUtil.SIZE_OF_LONG;
    } else {
      // no available space to store two non fixed value.
      return -1;
    }
  }

  private int calcValueEndOffset(int pointerOffset) {
    int valueEndOffset = capacity - 1;
    if (pointerOffset > 0) {
      valueEndOffset = buffer.getInt(pointerOffset - ByteUtil.SIZE_OF_INT) - 1;
    }
    return valueEndOffset;
  }

  public int getCapacity() {
    return capacity;
  }

  public int getSize() {
    return size;
  }

  public int getNextWriteOffset() {
    return nextWriteOffset;
  }
}
