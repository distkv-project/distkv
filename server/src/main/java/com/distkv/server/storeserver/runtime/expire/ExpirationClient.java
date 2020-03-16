package com.distkv.server.storeserver.runtime.expire;

import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.DROP;

import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.service.DistkvService;
import com.distkv.server.storeserver.StoreConfig;
import org.dousi.Proxy;
import org.dousi.api.Client;
import org.dousi.config.ClientConfig;
import org.dousi.exception.DousiConnectionRefusedException;
import org.dousi.netty.DousiClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpirationClient {

  private static Logger LOGGER = LoggerFactory.getLogger(ExpirationClient.class);

  /// The expire client.
  private Client expireClient;
  /// The rpc service for this proxy.
  private DistkvService service;

  private boolean isConnected;

  private StoreConfig storeConfig;

  public ExpirationClient(StoreConfig storeConfig) {
    this.storeConfig = storeConfig;
  }

  public void connect() {
    String localAddress = String.format("distkv://127.0.0.1:%d", storeConfig.getPort());
    ClientConfig clientConfig = ClientConfig.builder()
        .address(localAddress)
        .build();
    expireClient = new DousiClient(clientConfig);
    try {
      expireClient.open();
    } catch (DousiConnectionRefusedException connectFail) {
      throw new DistkvException("Failed to connect to Distkv Server:" + connectFail);
    } finally {
      isConnected = true;
    }
    Proxy<DistkvService> distkvRpcProxy = new Proxy<>();
    distkvRpcProxy.setInterfaceClass(DistkvService.class);
    service = distkvRpcProxy.getService(expireClient);
  }

  public boolean isConnected() {
    return isConnected;
  }

  public boolean disconnect() {
    try {
      expireClient.close();
      isConnected = false;
      return true;
    } catch (DistkvException ex) {
      throw new DistkvException(String.format("Failed to close the clients : %s", ex.getMessage()));
    }
  }

  public void drop(String key, RequestType requestType) {
    RequestType dropType = DROP;

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(dropType)
        .build();
    service.call(request);
  }
}
