package com.distkv.server.storeserver;

import com.distkv.rpc.service.DistkvService;
import com.distkv.server.AbstractService;
import com.distkv.server.storeserver.runtime.StoreRuntime;
import com.distkv.server.storeserver.services.DistkvServiceImpl;
import org.dousi.DousiServer;
import org.dousi.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoreService extends AbstractService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StoreService.class);

  private StoreRuntime storeRuntime;
  private DousiServer dousiServer;
  private StoreConfig storeConfig;

  public StoreService(StoreConfig storeConfig) {
    super("Store Service");
    this.storeConfig = storeConfig;
  }

  @Override
  public void serviceInit() {
    ServerConfig dousiServerConfig = ServerConfig.builder()
        /// Note: This is a very important flag for `StoreServer` because it
        /// affects the threading model of `StoreServer`.
        /// For a `StoreServer`, it should have the rigorous threading model
        /// for the performance requirements. `Dousi` RPC has its own threading
        /// model with multiple worker threads, and `StoreServer` should have
        /// its own threading model with multiple threads as well. Therefore, it's
        /// hard to manage so many threads to meet our performance requirements if
        /// we don't enable this flag `enableIOThreadOnly`.
        .enableIOThreadOnly(true)
        .port(storeConfig.getPort())
        .build();

    dousiServer = new DousiServer(dousiServerConfig);
    storeRuntime = new StoreRuntime(storeConfig);
    registerAllRpcServices();
  }

  private void registerAllRpcServices() {
    dousiServer.registerService(
        DistkvService.class, new DistkvServiceImpl(this.storeRuntime));
  }

  @Override
  protected void serviceRun() {
    try {
      dousiServer.run();
    } catch (Throwable e) {
      LOGGER.error("Failed with the exception: {}", e.toString());
      System.exit(-1);
    }
    LOGGER.info("Succeeded to start dst server on port {}.", storeConfig.getPort());
  }

  @Override
  protected void serviceStop() {
    dousiServer.stop();
  }
}
