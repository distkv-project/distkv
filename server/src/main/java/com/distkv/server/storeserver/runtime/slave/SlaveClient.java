package com.distkv.server.storeserver.runtime.slave;

import com.distkv.drpc.Proxy;
import com.distkv.drpc.api.Client;
import com.distkv.drpc.config.ClientConfig;
import com.distkv.drpc.netty.NettyClient;
import com.distkv.rpc.service.DistkvService;

public class SlaveClient {

  private DistkvService distkvService;

  private Client rpcClient;

  private boolean isOpen;

  public SlaveClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
        .address(serverAddress)
        .build();

    rpcClient = new NettyClient(clientConfig);
    rpcClient.open();
    isOpen = true;
    // Setup distkvService proxy.
    Proxy<DistkvService> distkvRpcProxy = new Proxy<>();
    distkvRpcProxy.setInterfaceClass(DistkvService.class);
    distkvService = distkvRpcProxy.getService(rpcClient);
  }

  public void closeClient() {
    isOpen = false;
    rpcClient.close();
  }

  public boolean isOpen() {
    return isOpen;
  }

  public DistkvService getDistkvService() {
    if (isOpen) {
      return distkvService;
    } else {
      return null;
    }
  }
}
