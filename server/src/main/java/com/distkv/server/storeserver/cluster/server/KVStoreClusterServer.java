package com.distkv.server.storeserver.cluster.server;

import com.distkv.rpc.service.DistkvService;
import com.distkv.server.storeserver.StoreConfig;
import com.distkv.server.storeserver.cluster.ClusterServer;
import com.distkv.server.storeserver.services.DistkvServiceImpl;
import org.drpc.DrpcServer;
import org.drpc.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : kairbon
 * @date : 2021/3/20
 */
public class KVStoreClusterServer {
  private static final Logger LOG = LoggerFactory.getLogger(KVStoreClusterServer.class);

  private DrpcServer drpcServer;

  private StoreConfig storeConfig;

  private ClusterServer clusterServer;

  public KVStoreClusterServer(StoreConfig storeConfig, ClusterServer clusterServer) {
    this.storeConfig = storeConfig;
    this.clusterServer = clusterServer;
    ServerConfig drpcServerConfig = ServerConfig.builder()
        /// Note: This is a very important flag for `StoreServer` because it
        /// affects the threading model of `StoreServer`.
        /// For a `StoreServer`, it should have the rigorous threading model
        /// for the performance requirements. `Drpc` RPC has its own threading
        /// model with multiple worker threads, and `StoreServer` should have
        /// its own threading model with multiple threads as well. Therefore, it's
        /// hard to manage so many threads to meet our performance requirements if
        /// we don't enable this flag `enableIOThreadOnly`.
        .enableIOThreadOnly(true)
        .port(storeConfig.getPort())
        .build();
    drpcServer = new DrpcServer(drpcServerConfig);
    registerAllRpcServices();
  }

  public void run() {
    try {
      drpcServer.run();
    } catch (Throwable e) {
      LOG.error("Failed with the exception: {}", e.toString());
      System.exit(-1);
    }
    LOG.info("Succeeded to start distkv server on port {}.", storeConfig.getPort());
  }

  private void registerAllRpcServices() {
    drpcServer.registerService(
        DistkvService.class, new DistkvServiceImpl(clusterServer));
  }

  public void shutdown() {
    try {
      drpcServer.stop();
    } catch (Throwable e) {
      LOG.error("Failed shutdown DistkvServer with the exception: {}", e.toString());
      System.exit(-1);
    }
    LOG.debug("Succeeded to shutdown DistkvServer.");
  }
}
