package com.distkv.common;

import com.distkv.common.id.NodeId;

import java.io.Serializable;

public class NodeInfo implements Serializable {

  private static final long serialVersionUID = -4220017786527146673L;

  private NodeId nodeId;

  private String address;

  public static Builder newBuilder() {
    return new Builder();
  }

  private NodeInfo(Builder builder) {
    this.address = builder.getAddress();
    this.nodeId = builder.getNodeId();
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

  public static class Builder {
    private NodeId nodeId;

    private String address;

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

