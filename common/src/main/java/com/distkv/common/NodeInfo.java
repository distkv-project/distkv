package com.distkv.common;

import java.io.Serializable;

public class NodeInfo implements Serializable {

  private static final long serialVersionUID = -4220017786527146673L;

  private boolean isMaster;

  private String nodeName;

  private String ipAndPort;

  public NodeInfo(boolean isMaster, String nodeName, String ipAndPort) {
    this.nodeName = nodeName;
    this.isMaster = isMaster;
    this.ipAndPort = ipAndPort;
  }

  public boolean isMaster() {
    return isMaster;
  }

  public String getNodeName() {
    return nodeName;
  }

  public void setMaster(boolean isMaster) {
    this.isMaster = isMaster;
  }

  public String getIpAndPort() {
    return ipAndPort;
  }

  public void setIpAndPort(String ipAndPort) {
    this.ipAndPort = ipAndPort;
  }
}
