package com.distkv.supplier;

import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.NodeOptions;
import com.distkv.server.storeserver.cluster.ClusterServer;

import java.io.IOException;

/**
 * @author : kairbon
 * @date : 2021/5/1
 */
public class StaticServer {
  private static volatile  ClusterServer SingletonServer;

  private StaticServer() {
  }

  public static ClusterServer newInstance() {
    if (SingletonServer == null) {
      synchronized (ClusterServer.class) {
        if (SingletonServer == null) {
          final NodeOptions nodeOptions = new NodeOptions();
          // 为了测试,调整 snapshot 间隔等参数
          // 设置选举超时时间为 1 秒
          nodeOptions.setElectionTimeoutMs(1000);
          // 关闭 CLI 服务。
          nodeOptions.setDisableCli(false);
          // 每隔30秒做一次 snapshot
          nodeOptions.setSnapshotIntervalSecs(30);
          // 解析参数
          final PeerId serverId = new PeerId();
          if (!serverId.parse(BaseTestSupplier.TEST_CONFIG)) {
            throw new IllegalArgumentException("Fail to parse serverId:" +
                BaseTestSupplier.TEST_CONFIG);
          }
          final Configuration initConf = new Configuration();
          if (!initConf.parse(BaseTestSupplier.TEST_CONFIG)) {
            throw new IllegalArgumentException("Fail to parse initConf:" +
                BaseTestSupplier.TEST_CONFIG);
          }
          // 设置初始集群配置
          nodeOptions.setInitialConf(initConf);
          try {
            SingletonServer = new ClusterServer(BaseTestSupplier.TEST_DATA_PATH,
                BaseTestSupplier.GROUP_ID, serverId, nodeOptions);
            SingletonServer.run();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return SingletonServer;
  }
}

