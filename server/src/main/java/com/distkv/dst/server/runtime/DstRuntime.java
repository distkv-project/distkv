package com.distkv.dst.server.runtime;

import com.distkv.drpc.api.Client;
import com.distkv.drpc.config.ClientConfig;
import com.distkv.drpc.netty.NettyClient;
import com.distkv.dst.server.DstServerConfig;
import com.distkv.dst.server.runtime.workerpool.WorkerPool;

import java.util.ArrayList;
import java.util.List;

public class DstRuntime {

  private DstServerConfig config;

  private WorkerPool workerPool;

  private List<Client> clients;

  public DstRuntime(DstServerConfig config) {
    this.config = config;

    if (config.isMaster()) {
      clients = new ArrayList<>();
      for(String salverAddr : config.getSlaveAddresses()) {
        ClientConfig clientConfig = ClientConfig.builder()
            .address(salverAddr)
            .build();
        Client client = new NettyClient(clientConfig);
        client.open();
        clients.add(client);
      }
    } else {
      clients = null;
    }

    workerPool = new WorkerPool(config.getShardNum(),
        config.isMaster(),
        clients);
  }

  public WorkerPool getWorkerPool() {
    return workerPool;
  }

}
