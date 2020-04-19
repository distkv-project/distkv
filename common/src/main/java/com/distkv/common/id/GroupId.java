package com.distkv.common.id;

import java.io.Serializable;

/**
 * The class that represents the Id of a partition.
 */
public class GroupId implements Serializable {

  private static final long serialVersionUID = -5170144234860018298L;

  /**
   * The index of the partition in the Dst cluster.
   */
  private short index;

  private GroupId(short index) {
    this.index = index;
  }

  public short getIndex() {
    return index;
  }

  public void setIndex(short i) {
    index = i;
  }

  public static GroupId fromIndex(short index) {
    return new GroupId(index);
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
    return (((GroupId) obj).index == index);
  }

  @Override
  public String toString() {
    return String.format("PartitionId{%d}", index);
  }

}
