package com.distkv.server.storeserver.runtime.expire;

import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.service.DistkvService;
import com.distkv.server.storeserver.StoreConfig;
import org.dousi.Proxy;
import org.dousi.api.Client;
import org.dousi.config.ClientConfig;
import org.dousi.exception.DousiConnectionRefusedException;
import org.dousi.netty.DousiClient;

public class DefaultExpireClient implements ExpireClient {

  /// The expire client.
  private Client expireClient;
  /// The rpc service for this proxy.
  private DistkvService service;

  private boolean isConnected;

  private StoreConfig storeConfig;

  public DefaultExpireClient(StoreConfig storeConfig) {
    this.storeConfig = storeConfig;
  }

  @Override
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

  @Override
  public boolean isConnected() {
    return isConnected;
  }

  @Override
  public boolean disconnect() {
    try {
      expireClient.close();
      isConnected = false;
      return true;
    } catch (DistkvException ex) {
      throw new DistkvException(String.format("Failed close the clients : %s", ex.getMessage()));
    }
  }

  @Override
  public void strDrop(String key) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.STR_DROP)
        .build();
    service.call(request);
  }

  @Override
  public void listDrop(String key) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.LIST_DROP)
        .build();
    service.call(request);
  }

  @Override
  public void setDrop(String key) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SET_DROP)
        .build();
    service.call(request);
  }

  @Override
  public void dictDrop(String key) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.DICT_DROP)
        .build();
    service.call(request);
  }

  @Override
  public void slistDrop(String key) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SORTED_LIST_DROP)
        .build();
    service.call(request);
  }

  @Override
  public void intDrop(String key) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.INT_DROP)
        .build();
    service.call(request);
  }

}
