package com.distkv.utils;

import com.distkv.supplier.TestUtil;
import com.google.common.collect.ImmutableList;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A helper class to help you to start meta servers and store servers.
 * So that you will be easy to add your distributed tests.
 */
public class DistkvNodesManager {
  public static final String DEFAULT_META_SERVER_ADDRESSES =
      "127.0.0.1:8081,127.0.0.1:8082,127.0.0.1:8083";

  private static final String SERVER_SUFFIX_JAR_DIR = "server" + File.separator + "target"
      + File.separator + "distkv-server-0.1.4-SNAPSHOT-jar-with-dependencies.jar";

  public DistkvNodesManager(int metaServerProcessesNum) {
    this.metaServersNum = metaServerProcessesNum;
    metaServers = new ArrayList<>();
  }

  private int metaServersNum;

  private List<Process> metaServers;

  public void startAllMetaServers() {
    final File userDir = new File(System.getProperty("user.dir"));
    final String jarDir;
    if (userDir.getPath().contains("test")) {
      jarDir = userDir.getParent() + File.separator + SERVER_SUFFIX_JAR_DIR;
    } else {
      jarDir = userDir.getPath() + File.separator + SERVER_SUFFIX_JAR_DIR;
    }
    final long currentTime = System.currentTimeMillis();
    for (int i = 0; i < metaServersNum; i++) {
      final List<String> startCommand = ImmutableList.of(
          "java",
          "-classpath",
          jarDir,
          "com.distkv.server.metaserver.server.DmetaServer",
          File.separator + "tmp" + File.separator + "dmeta" + currentTime
              + File.separator + "server" + (i + 1),
          "META_SERVER",
          "127.0.0.1:808" + (i + 1),
          DEFAULT_META_SERVER_ADDRESSES
      );
      metaServers.add(TestUtil.executeCommand(startCommand));
    }
  }

  public void stopAllMetaServers() {
    for (Process process : metaServers) {
      TestUtil.stopProcess(process);
    }
  }

  public String getMetaServerAddresses() {
    return DEFAULT_META_SERVER_ADDRESSES;
  }
}