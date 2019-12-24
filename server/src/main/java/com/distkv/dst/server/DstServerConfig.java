package com.distkv.dst.server;

import com.distkv.drpc.config.ServerConfig;

public class DstServerConfig {
  private int listeningPort;
  private int workerThreadNum;
  private boolean isMaster;

  public boolean isMaster() {
    return isMaster;
  }

  public ServerConfig genRpcConfig() {
    ServerConfig serverConfig = ServerConfig.builder()
        .port(listeningPort)
        .workerThreadNum(workerThreadNum)
        .build();
    return serverConfig;
  }

  public int getPort() {
    return  listeningPort;
  }

  DstServerConfig(int listeningPort, int workerThreadNum, boolean isMaster) {
    this.listeningPort = listeningPort;
    this.isMaster = isMaster;
    this.workerThreadNum = workerThreadNum;
  }

  public static DstServerConfig.DstServerConfigBuilder builder() {
    return new DstServerConfig.DstServerConfigBuilder();
  }

  public static class DstServerConfigBuilder {
    private int listeningPort;
    private int workerThreadNum;
    private boolean isMaster;

    public DstServerConfig.DstServerConfigBuilder port(int port) {
      this.listeningPort = port;
      return this;
    }

    public DstServerConfig.DstServerConfigBuilder workerThreadNum(int num) {
      this.workerThreadNum = num;
      return this;
    }

    public DstServerConfig.DstServerConfigBuilder isMaster(boolean isMaster) {
      this.isMaster = isMaster;
      return this;
    }

    public DstServerConfig build() {
      return new DstServerConfig(listeningPort, workerThreadNum, isMaster);
    }
  }
}


