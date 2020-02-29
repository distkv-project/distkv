package com.distkv.server;

/**
 * the location of node.
 */
public class Location {

  private String rack;
  private String region;
  private String machine;

  public String getRack() {
    return rack;
  }

  public void setRack(String rack) {
    this.rack = rack;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getMachine() {
    return machine;
  }

  public void setMachine(String machine) {
    this.machine = machine;
  }
}
