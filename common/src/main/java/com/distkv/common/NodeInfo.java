package com.distkv.common;

import com.distkv.common.id.NodeId;

import java.io.Serializable;

public class NodeInfo implements Serializable {
  /**
   * Whether this node is a master.
   *
   * True if this node is master node, false if this node is a slave node.
   */
  private boolean isMaster;

  private static final long serialVersionUID = -4220017786527146673L;

  private NodeId nodeId;

  private String address;

  public static Builder newBuilder() {
    return new Builder();
  }

  private NodeInfo(Builder builder) {
    this.address = builder.getAddress();
    this.nodeId = builder.getNodeId();
    this.isMaster = builder.isMaster();
  }

  public NodeId getNodeId() {
    return nodeId;
  }

  public void setNodeId(NodeId nodeId) {
    this.nodeId = nodeId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Boolean isMaster() {
    return isMaster;
  }

  public void setMaster(Boolean master) {
    isMaster = master;
  }

  public static class Builder {
    private NodeId nodeId;

    private String address;

    private Boolean isMaster;

    public Boolean isMaster() {
      return isMaster;
    }

    public Builder setMaster(Boolean master) {
      isMaster = master;
      return this;
    }

    public NodeId getNodeId() {
      return nodeId;
    }

    public Builder setNodeId(NodeId nodeId) {
      this.nodeId = nodeId;
      return this;
    }

    public String getAddress() {
      return address;
    }

    public Builder setAddress(String address) {
      this.address = address;
      return this;
    }

    public NodeInfo build() {
      return new NodeInfo(this);
    }

  }
}

