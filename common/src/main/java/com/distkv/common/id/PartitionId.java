package com.distkv.common.id;

import java.io.Serializable;

/**
 * The class that represents the Id of a partition.
 */
public class PartitionId implements Serializable {

  private static final long serialVersionUID = -5170144234860018298L;

  /**
   * The index of the partition in the Dst cluster.
   */
  private short index;

  private PartitionId(short index) {
    this.index = index;
  }

  public short getIndex() {
    return index;
  }

  public static PartitionId fromShort(short index) {
    return new PartitionId(index);
  }

  @Override
  public int hashCode() {
    return Short.hashCode(index);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!this.getClass().equals(obj.getClass())) {
      return false;
    }
    return (((PartitionId) obj).index == index);
  }

  @Override
  public String toString() {
    return String.format("PartitionId{%d}", index);
  }

}
