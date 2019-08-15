package org.dst.server.service;

import com.baidu.brpc.server.RpcServer;
import com.baidu.brpc.server.RpcServerOptions;
import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;

public class DstRpcServer {

  private KVStore kvStore;

  private DstRpcServer() {
    kvStore = new KVStoreImpl();
  }

  public static void main(String[] args) {

    DstRpcServer rpcServer = new DstRpcServer();

    int port = 8082;

    if (args.length == 1) {
      // TODO(qwang): This may throw exception.
      port = Integer.valueOf(args[0]);
    }

    RpcServerOptions options = new RpcServerOptions();
    // TODO(qwang): This should be configurable.
    options.setReceiveBufferSize(64 * 1024 * 1024);
    options.setSendBufferSize(64 * 1024 * 1024);
    options.setKeepAliveTime(20);

    RpcServer server = new RpcServer(port, options);
    // Register service.
    server.registerService(new DstStringServiceImpl(rpcServer.kvStore));
    server.registerService(new DstSetServiceImpl(rpcServer.kvStore));
    server.start();

    // make server keep running
    synchronized (DstRpcServer.class) {
      try {
        DstRpcServer.class.wait();
      } catch (Throwable e) {
        // TODO(qwang): Add log and do clean up.
      }
    }

  }
}
