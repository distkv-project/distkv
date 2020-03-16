package com.distkv.core.map;

import com.distkv.core.segment.ByteSegment;

/**
 * DstOffHeapMapMeta store one byte metadata for every key value pair to support quicker lookup.
 * which makes 16 key value as a group and use sse2 instructions to determine the group is full or
 * the group is deleted or empty. a group metadata contains 16 * 8 = 124 bit, which can be fully
 * put at L1 cache. So it's cache friendly. more information reference to
 * <li>https://abseil.io/about/design/swisstables</li>
 */
public class DistkvOffHeapMapMeta {

  private int size;
  private ByteSegment byteSegment = new ByteSegment(1);
  private HashFunction hashFunction;

  // fist 8 bit 0000_0000 means empty.
  // last 8 bit 1111_1110 means the value is deleted
  private static final short MASK = 0b0000_0000_1111_1110;

  // Group Mask
  private static final long GROUP_EMPTY_MASK = 0b0000_0000_0000_0000_0000_0000_0000_0000;


  // sse instruction https://github.com/rust-lang/hashbrown


  // TODO implemented by SSE instruction,
  public boolean isGroupFull(int hash) {
    return false;
  }

  // TODO implemented by SSE instruction
  public boolean isGroupMatch(long hash) {
    return false;
  }

  // TODO implemented by SSE instruction
  public boolean isGroupEmpty(long hash) {
    return false;
  }

  // TODO implemented by SSE instruction
  public boolean isGroupEmptyOrDeleted(byte b) {
    return false;
  }

  public boolean isEmptyOrDeleted(byte b) {
    return isEmpty(b) || isDeleted(b);
  }

  /**
   * the key related value is empty or not for given key's metadata.
   * it's empty when metadata is 0000_0000, otherwise it's not empty.
   * @param b metadata stored for one key value pair
   * @return related value is empty or not.
   */
  public boolean isEmpty(byte b) {
    return (b | (MASK >> 8)) == 0;
  }

  /**
   * the key related value is deleted or not for given key's metadata.
   * it's deleted when metadata is 1111_1110, otherwise it's not deleted.
   * @param b the metadata stored for one key value pair.
   * @return related value is deleted or not
   */
  public boolean isDeleted(byte b) {
    return (b & MASK) == MASK;
  }

}
