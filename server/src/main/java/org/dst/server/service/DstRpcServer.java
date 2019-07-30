package org.dst.server.service;

import com.baidu.brpc.server.RpcServer;
import com.baidu.brpc.server.RpcServerOptions;

public class DstRpcServer {
  public static void main(String[] args) {
    int port = 8082;

    RpcServerOptions options = new RpcServerOptions();
    options.setReceiveBufferSize(64 * 1024 * 1024);
    options.setSendBufferSize(64 * 1024 * 1024);
    options.setKeepAliveTime(20);

    RpcServer server = new RpcServer(port, options);
    // Register service.
    server.registerService(new EchoServiceImpl());
    server.start();

    // make server keep running
    synchronized (DstRpcServer.class) {
      try {
        DstRpcServer.class.wait();
      } catch (Throwable e) {
      }
    }

  }
}
