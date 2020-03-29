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
  private GroupId groupId;

  /**
   * The index of this node.
   */
  private int index;

  public GroupId getGroupId() {
    return groupId;
  }

  public void setGroupId(GroupId groupId) {
    this.groupId = groupId;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int i) {
    this.index = i;
  }

  private NodeId(int index, GroupId groupId) {
    this.index = index;
    this.groupId = groupId;
  }

  public static NodeId from(int index, GroupId groupId) {
    return new NodeId(index, groupId);
  }

  public static NodeId nil() {
    return new NodeId(-1, null);
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
    return (r.index == index && r.groupId == groupId);
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + Integer.hashCode(index);
    result = 31 * result + groupId.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return String.format("NodeId{%d-%d}", index, groupId.getIndex());
  }

}
