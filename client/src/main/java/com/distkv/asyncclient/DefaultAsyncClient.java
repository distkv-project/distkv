package com.distkv.asyncclient;

import com.distkv.namespace.NamespaceInterceptor;
import org.dousi.Proxy;
import org.dousi.api.Client;
import org.dousi.config.ClientConfig;
import org.dousi.netty.NettyClient;
import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.service.DistkvService;

public class DefaultAsyncClient implements DistkvAsyncClient {

  private DistkvAsyncStringProxy stringProxy;

  private DistkvAsyncListProxy listProxy;

  private DistkvAsyncSetProxy setProxy;

  private DistkvAsyncDictProxy dictProxy;

  private DistkvAsyncSortedListProxy sortedListProxy;

  private DistkvAsyncIntProxy intProxy;

  /// The rpc client.
  private Client rpcClient;

  /// The interceptor to resolve the conflicts of keys in different clients.
  private NamespaceInterceptor namespaceInterceptor = new NamespaceInterceptor();

  public DefaultAsyncClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
            .address(serverAddress)
            .build();

    rpcClient = new NettyClient(clientConfig);
    rpcClient.open();
    Proxy<DistkvService> distkvRpcProxy = new Proxy<>();
    distkvRpcProxy.setInterfaceClass(DistkvService.class);

    stringProxy = new DistkvAsyncStringProxy(this, distkvRpcProxy.getService(rpcClient));
    listProxy = new DistkvAsyncListProxy(this, distkvRpcProxy.getService(rpcClient));
    setProxy = new DistkvAsyncSetProxy(this, distkvRpcProxy.getService(rpcClient));
    dictProxy = new DistkvAsyncDictProxy(this, distkvRpcProxy.getService(rpcClient));
    sortedListProxy = new DistkvAsyncSortedListProxy(this, distkvRpcProxy.getService(rpcClient));
    intProxy = new DistkvAsyncIntProxy(this, distkvRpcProxy.getService(rpcClient));
  }

  @Override
  public boolean connect() {
    return true;
  }

  @Override
  public boolean isConnected() {
    return true;
  }

  @Override
  public boolean disconnect() {
    try {
      rpcClient.close();
      return true;
    } catch (DistkvException ex) {
      throw new DistkvException(String.format("Failed close the clients : %s", ex.getMessage()));
    }
  }

  @Override
  public void activeNamespace(String namespace) {
    namespaceInterceptor.active(namespace);
  }

  @Override
  public void deactiveNamespace() {
    namespaceInterceptor.deactive();
  }

  @Override
  public String getActivedNamespace() {
    return namespaceInterceptor.getNamespace();
  }

  @Override
  public DistkvAsyncStringProxy strs() {
    return stringProxy;
  }

  @Override
  public DistkvAsyncListProxy lists() {
    return listProxy;
  }

  @Override
  public DistkvAsyncSetProxy sets() {
    return setProxy;
  }

  @Override
  public DistkvAsyncDictProxy dicts() {
    return dictProxy;
  }

  @Override
  public DistkvAsyncSortedListProxy sortedLists() {
    return sortedListProxy;
  }

  @Override
  public DistkvAsyncIntProxy ints() {
    return intProxy;
  }

}
