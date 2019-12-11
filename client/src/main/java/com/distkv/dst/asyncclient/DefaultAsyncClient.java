package com.distkv.dst.asyncclient;

import com.distkv.drpc.Proxy;
import com.distkv.drpc.api.Client;
import com.distkv.drpc.config.ClientConfig;
import com.distkv.drpc.netty.NettyClient;
import com.distkv.dst.rpc.service.DstListService;
import com.distkv.dst.rpc.service.DstSetService;
import com.distkv.dst.rpc.service.DstStringService;

public class DefaultAsyncClient implements DstAsyncClient {

  private DstStringProxy stringProxy;

  private DstListProxy listProxy;

  private DstSetProxy setProxy;

  private DstDictProxy dictProxy;

  private DstSortedListProxy sortedListProxy;

  public DefaultAsyncClient(String serverAddress) {
  	ClientConfig clientConfig = ClientConfig.builder()
			.address(serverAddress)
			.build();

  	// Setup string proxy.
	Client strRpcClient = new NettyClient(clientConfig);
	strRpcClient.open();
	Proxy<DstStringService> strRpcProxy = new Proxy<>();
	strRpcProxy.setInterfaceClass(DstStringService.class);
	stringProxy = new DstStringProxy(strRpcProxy.getService(strRpcClient));

	//Setup list proxy.
//	Client listRpcClient = new NettyClient(clientConfig);
//	listRpcClient.open();
//	Proxy<DstListService> listRpcProxy = new Proxy<>();
//	listRpcProxy.setInterfaceClass(DstListService.class);
//	listProxy = new DstListProxy(listRpcProxy.getService(listRpcClient));

	//Setup set proxy.
//	Client setRpcClient = new NettyClient(clientConfig);
//	setRpcClient.open();
//	Proxy<DstSetService> setRpcProxy = new Proxy<>();
//	setRpcProxy.setInterfaceClass(DstSetService.class);
//	setProxy = new DstSetProxy(setRpcProxy.getService(setRpcClient));

  }

  @Override
  public boolean connect() { return true; }

  @Override
  public boolean isConnect() { return true; }

  @Override
  public boolean disConnect() {
  	return true;
  }

  @Override
  public DstStringProxy strs() { return stringProxy; }
}
