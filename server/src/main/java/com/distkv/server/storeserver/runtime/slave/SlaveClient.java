package com.distkv.server.storeserver.runtime.slave;

import org.dousi.Proxy;
import org.dousi.api.Client;
import org.dousi.config.ClientConfig;
import org.dousi.netty.NettyClient;
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
