package com.distkv.asyncclient;

import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.DROP;
import com.distkv.namespace.NamespaceInterceptor;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.ExpireProtocol.ExpireRequest;
import com.google.protobuf.Any;
import java.util.concurrent.CompletableFuture;
import org.dousi.Proxy;
import org.dousi.api.Client;
import org.dousi.config.ClientConfig;
import org.dousi.exception.DousiConnectionRefusedException;
import org.dousi.netty.DousiClient;
import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.service.DistkvService;

public class DefaultAsyncClient implements DistkvAsyncClient {

  private DistkvAsyncStringProxy stringAsyncProxy;

  private DistkvAsyncListProxy listAsyncProxy;

  private DistkvAsyncSetProxy setAsyncProxy;

  private DistkvAsyncDictProxy dictAsyncProxy;

  private DistkvAsyncSortedListProxy sortedListAsyncProxy;

  private DistkvAsyncIntProxy intAsyncProxy;

  private DistkvService distkvService;

  /// The rpc client.
  private Client rpcClient;

  /// The interceptor to resolve the conflicts of keys in different clients.
  private NamespaceInterceptor namespaceInterceptor = new NamespaceInterceptor();

  public DefaultAsyncClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
        .address(serverAddress)
        .build();

    rpcClient = new DousiClient(clientConfig);
    try {
      rpcClient.open();
    } catch (DousiConnectionRefusedException connectFail) {
      throw new DistkvException("Failed to connect to Distkv Server:" + connectFail);
    }
    Proxy<DistkvService> distkvRpcProxy = new Proxy<>();
    distkvRpcProxy.setInterfaceClass(DistkvService.class);
    distkvService = distkvRpcProxy.getService(rpcClient);
    stringAsyncProxy = new DistkvAsyncStringProxy(this, distkvRpcProxy.getService(rpcClient));
    listAsyncProxy = new DistkvAsyncListProxy(this, distkvRpcProxy.getService(rpcClient));
    setAsyncProxy = new DistkvAsyncSetProxy(this, distkvRpcProxy.getService(rpcClient));
    dictAsyncProxy = new DistkvAsyncDictProxy(this, distkvRpcProxy.getService(rpcClient));
    sortedListAsyncProxy = new DistkvAsyncSortedListProxy(this,
        distkvRpcProxy.getService(rpcClient));
    intAsyncProxy = new DistkvAsyncIntProxy(this, distkvRpcProxy.getService(rpcClient));
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
  public CompletableFuture<DistkvResponse> drop(String key) {
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(DROP)
        .build();
    return distkvService.call(request);
  }

  @Override
  public CompletableFuture<DistkvResponse> expire(String key, long expireTime) {
    ExpireRequest expireRequest = ExpireRequest
        .newBuilder()
        .setExpireTime(expireTime)
        .build();
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.EXPIRE)
        .setRequest(Any.pack(expireRequest))
        .build();
    return distkvService.call(request);
  }

  @Override
  public CompletableFuture<DistkvResponse> ttl(String key) {
    //TODO (senyer)
    return null;
  }

  @Override
  public DistkvAsyncStringProxy strs() {
    return stringAsyncProxy;
  }

  @Override
  public DistkvAsyncListProxy lists() {
    return listAsyncProxy;
  }

  @Override
  public DistkvAsyncSetProxy sets() {
    return setAsyncProxy;
  }

  @Override
  public DistkvAsyncDictProxy dicts() {
    return dictAsyncProxy;
  }

  @Override
  public DistkvAsyncSortedListProxy sortedLists() {
    return sortedListAsyncProxy;
  }

  @Override
  public DistkvAsyncIntProxy ints() {
    return intAsyncProxy;
  }

}
