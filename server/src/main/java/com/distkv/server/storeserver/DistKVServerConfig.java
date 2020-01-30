package com.distkv.server.storeserver;

import com.google.common.base.Strings;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.List;

public class DistKVServerConfig {
  private static final Logger LOGGER = LoggerFactory.getLogger(DistKVServerConfig.class);

  public static final String CUSTOM_CONFIG_FILE = "distkv.conf";
  public static final String DEFAULT_CONFIG_FILE = "distkv.default.conf";

  private int listeningPort;
  private boolean isMaster;
  private int shardsNum;
  private List<String> slaveAddresses;

  public boolean isMaster() {
    return isMaster;
  }

  public int getPort() {
    return listeningPort;
  }

  public void setPort(int port) {
    listeningPort = port;
  }

  public int getShardNum() {
    return shardsNum;
  }

  public List<String> getSlaveAddresses() {
    return slaveAddresses;
  }

  public DistKVServerConfig(Config config) {
    listeningPort = config.getInt("store.listeningPort");
    isMaster = config.getBoolean("store.isMaster");
    shardsNum = config.getInt("store.shardsNum");
    if (isMaster) {
      slaveAddresses = config.getStringList("store.slaveAddresses");
    } else {
      slaveAddresses = null;
    }
  }

  @Override
  public String toString() {
    return "listeningPort: " + listeningPort + ";\n"
        + "isMaster: " + isMaster + ";\n"
        + "shardNum: " + shardsNum + ";\n"
        + "slaves" + slaveAddresses.toString() + "\n";
  }

  public static DistKVServerConfig create() {
    ConfigFactory.invalidateCaches();
    Config config = ConfigFactory.systemProperties();
    String configPath = System.getProperty("distkv.config");
    if (Strings.isNullOrEmpty(configPath)) {
      LOGGER.info("Loading config from \"distkv.conf\" file in classpath.");
      config = config.withFallback(ConfigFactory.load(CUSTOM_CONFIG_FILE));
    } else {
      LOGGER.info("Loading config from " + configPath + ".");
      config = config.withFallback(ConfigFactory.parseFile(new File(configPath)));
    }
    config = config.withFallback(ConfigFactory.load(DEFAULT_CONFIG_FILE));
    return new DistKVServerConfig(config);
  }
}


