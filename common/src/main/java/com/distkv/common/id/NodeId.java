package com.distkv.common.id;

import java.io.Serializable;

/**
 * The class that represents the Id of a node.
 */
public class NodeId implements Serializable {

  private static final long serialVersionUID = -2216426913363839949L;

  /**
   * The Id of the partition that this node belongs to.
   */
  private PartitionId partitionId;

  /**
   * The index of this node.
   */
  private int index;

  public PartitionId getPartitionId() {
    return partitionId;
  }

  public int getIndex() {
    return index;
  }

  private NodeId(int index, PartitionId partitionId) {
    this.index = index;
    this.partitionId = partitionId;
  }

  public static NodeId from(int index, PartitionId partitionId) {
    return new NodeId(index, partitionId);
  }


  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!this.getClass().equals(obj.getClass())) {
      return false;
    }
    NodeId r = (NodeId) obj;
    return (r.index == index && r.partitionId == partitionId);
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + Integer.hashCode(index);
    result = 31 * result + partitionId.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return String.format("NodeId{%d-%d}", index, partitionId.getIndex());
  }

}
