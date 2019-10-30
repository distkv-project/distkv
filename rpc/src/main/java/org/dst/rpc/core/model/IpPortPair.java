package org.dst.rpc.core.model;

import java.util.Objects;


public class IpPortPair {

  private String ip;
  private int port;

  public IpPortPair(String ip, int port) {
    this.ip = ip;
    this.port = port;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof IpPortPair)) {
      return false;
    }
    IpPortPair other = (IpPortPair) obj;
    if (!Objects.equals(ip, other.ip)) {
      return false;
    }
    if (!Objects.equals(port, other.port)) {
      return false;
    }
    return true;
  }
}
