package com.distkv.server.storeserver;

import com.google.common.base.Strings;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class StoreConfig {
  private static final Logger LOG = LoggerFactory.getLogger(StoreConfig.class);

  public static final String CUSTOM_CONFIG_FILE = "store.conf";
  public static final String DEFAULT_CONFIG_FILE = "store.default.conf";

  private int listeningPort;
  private int shardsNum;
  /**
   * Mode represents how to start the store server.
   */
  private RunningMode mode;
  /**
   * Meta Server address str.
   */
  private final String metaServerAddresses;

  public int getPort() {
    return listeningPort;
  }

  public void setPort(int port) {
    listeningPort = port;
  }

  public int getShardNum() {
    return shardsNum;
  }

  public RunningMode getMode() {
    return mode;
  }

  public void setMode(RunningMode mode) {
    this.mode = mode;
  }

  public String getMetaServerAddresses() {
    return metaServerAddresses;
  }

  public StoreConfig(Config config) {
    listeningPort = config.getInt("store.listeningPort");
    shardsNum = config.getInt("store.shardsNum");
    metaServerAddresses = config.getString("store.metaServerAddresses");
    mode = config.getEnum(RunningMode.class, "store.mode");
  }

  @Override
  public String toString() {
    return "listeningPort: " + listeningPort + ";\n"
        + "shardNum: " + shardsNum + ";\n";
  }

  public static StoreConfig create() {
    ConfigFactory.invalidateCaches();
    Config config = ConfigFactory.systemProperties();
    String configPath = System.getProperty("distkv.store.config");
    if (Strings.isNullOrEmpty(configPath)) {
      LOG.info("Loading config from \"store.conf\" file in classpath.");
      config = config.withFallback(ConfigFactory.load(CUSTOM_CONFIG_FILE));
    } else {
      LOG.info("Loading config from " + configPath + ".");
      config = config.withFallback(ConfigFactory.parseFile(new File(configPath)));
    }
    config = config.withFallback(ConfigFactory.load(DEFAULT_CONFIG_FILE));
    return new StoreConfig(config);
  }
}


