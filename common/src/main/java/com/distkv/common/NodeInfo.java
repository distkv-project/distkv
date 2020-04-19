package com.distkv.common;

import com.distkv.common.id.NodeId;

import java.io.Serializable;

public class NodeInfo implements Serializable {
  /**
   * Whether this node is a master.
   * True if this node is master node, false if this node is a slave node.
   */
  private boolean isMaster;

  private static final long serialVersionUID = -4220017786527146673L;

  private NodeId nodeId;

  private String address;

  private long lastHeartbeatTimestamp;

  private NodeState state;

  public static Builder newBuilder() {
    return new Builder();
  }

  private NodeInfo(Builder builder) {
    this.address = builder.getAddress();
    this.nodeId = builder.getNodeId();
    this.isMaster = builder.isMaster();
    //TODO(kairbon) The initial state is running by default,
    // and it can be corrected after subsequent changes.
    this.state = NodeState.RUNNING;
    this.lastHeartbeatTimestamp = 0;
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

  public void setIsMaster(Boolean master) {
    isMaster = master;
  }

  public NodeState getState() {
    return state;
  }

  public void setState(NodeState state) {
    this.state = state;
  }

  public long getLastHeartbeatTimestamp() {
    return lastHeartbeatTimestamp;
  }

  public void setLastHeartbeatTimestamp(long lastHeartbeatTimestamp) {
    this.lastHeartbeatTimestamp = lastHeartbeatTimestamp;
  }

  public static class Builder {
    private NodeId nodeId;

    private String address;

    private Boolean isMaster;

    public Boolean isMaster() {
      return isMaster;
    }

    public Builder setIsMaster(Boolean master) {
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

