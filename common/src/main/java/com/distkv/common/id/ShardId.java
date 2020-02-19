package com.distkv.common.id;

import java.io.Serializable;

/**
 * The class that represents the Id of a shard.
 */
public class ShardId implements Serializable {

  private static final long serialVersionUID = 6126218555756018827L;

  /**
   * The index of the shard in the Distkv cluster.
   */
  private int index;

  private ShardId(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  public static ShardId fromInt(int index) {
    return new ShardId(index);
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(index);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!this.getClass().equals(obj.getClass())) {
      return false;
    }
    return (((ShardId) obj).index == index);
  }

  @Override
  public String toString() {
    return String.format("ShardId{%d}", index);
  }

}
