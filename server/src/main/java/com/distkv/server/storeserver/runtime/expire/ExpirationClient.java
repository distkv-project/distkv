package com.distkv.server.storeserver.runtime.expire;

import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.DICT_DROP;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.INT_DROP;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.LIST_DROP;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.SET_DROP;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.SORTED_LIST_DROP;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.STR_DROP;

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
    RequestType dropType = null;
    switch (requestType) {
      case EXPIRED_STR:
        dropType = STR_DROP;
        break;
      case EXPIRED_LIST:
        dropType = LIST_DROP;
        break;
      case EXPIRED_SET:
        dropType = SET_DROP;
        break;
      case EXPIRED_DICT:
        dropType = DICT_DROP;
        break;
      case EXPIRED_INT:
        dropType = INT_DROP;
        break;
      case EXPIRED_SLIST:
        dropType = SORTED_LIST_DROP;
        break;
      default:
        LOGGER.error("Failed drop from store. Unknown request type: {}", requestType);
        return;
    }

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(dropType)
        .build();
    service.call(request);
  }


}
