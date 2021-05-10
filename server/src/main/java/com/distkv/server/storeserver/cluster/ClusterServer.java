package com.distkv.server.storeserver.cluster;

import com.alipay.sofa.jraft.rpc.RpcServer;
import com.alipay.sofa.jraft.Node;
import com.alipay.sofa.jraft.RaftGroupService;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.NodeOptions;
import com.alipay.sofa.jraft.rpc.RaftRpcServerFactory;
import com.distkv.server.storeserver.StoreConfig;
import com.distkv.server.storeserver.cluster.server.KVStoreClusterServer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author : kairbon
 * @date : 2021/3/20
 */
public class ClusterServer {

  private RaftGroupService raftGroupService;
  private Node node;
  private KVStoreStateMachine fsm;
  private KVStoreClusterServer storeClusterServer;
  private final RpcServer rpcServer;

  private String groupId;
  private PeerId serverId;
  private NodeOptions nodeOptions;

  public ClusterServer(final String dataPath, final String groupId, final PeerId serverId,
                       final NodeOptions nodeOptions) throws IOException {
    // 初始化路径
    FileUtils.forceMkdir(new File(dataPath));

    // 这里让 raft RPC 和业务 RPC 使用同一个 RPC server, 通常也可以分开
    rpcServer = RaftRpcServerFactory.createRaftRpcServer(serverId.getEndpoint());
    // 启动distkvServer.
    StoreConfig config = StoreConfig.create();
    storeClusterServer = new KVStoreClusterServer(config, this);

    // 初始化状态机
    this.fsm = new KVStoreStateMachine(config);
    // 设置状态机到启动参数
    nodeOptions.setFsm(this.fsm);
    // 设置存储路径
    // 日志, 必须
    nodeOptions.setLogUri(dataPath + File.separator + "log");
    // 元信息, 必须
    nodeOptions.setRaftMetaUri(dataPath + File.separator + "raft_meta");
    // snapshot, 可选, 一般都推荐
    nodeOptions.setSnapshotUri(dataPath + File.separator + "snapshot");

    this.groupId = groupId;
    this.serverId = serverId;
    this.nodeOptions = nodeOptions;

  }

  public void run() {
    new Thread(() -> {
      storeClusterServer.run();
    }).start();
    // 初始化 raft group 服务框架
    this.raftGroupService = new RaftGroupService(groupId, serverId, nodeOptions, rpcServer);
    // 启动
    this.node = this.raftGroupService.start();
  }

  public void shutdown() {
    raftGroupService.shutdown();
    node.shutdown();
    storeClusterServer.shutdown();
    rpcServer.shutdown();
  }

  public KVStoreStateMachine getFsm() {
    return this.fsm;
  }

  public Node getNode() {
    return this.node;
  }

  public static void main(String[] args) throws IOException {
    if (args.length != 4) {
      System.out
          .println("Useage : java com.alipay.sofa.jraft.example.counter.CounterServer " +
              "{dataPath} {groupId} {serverId} {initConf}");
      System.out
          .println("Example: java com.alipay.sofa.jraft.example.counter.CounterServer " +
              "/tmp/server1 counter 127.0.0.1:8081 127.0.0.1:8081,127.0.0.1:8082,127.0.0.1:8083");
      System.exit(1);
    }

    final String dataPath = args[0];
    final String groupId = args[1];
    final String serverIdStr = args[2];
    final String initConfStr = args[3];

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
    if (!serverId.parse(serverIdStr)) {
      throw new IllegalArgumentException("Fail to parse serverId:" + serverIdStr);
    }
    final Configuration initConf = new Configuration();
    if (!initConf.parse(initConfStr)) {
      throw new IllegalArgumentException("Fail to parse initConf:" + initConfStr);
    }
    // 设置初始集群配置
    nodeOptions.setInitialConf(initConf);

    // 启动
    final ClusterServer clusterServer =
        new ClusterServer(dataPath, groupId, serverId, nodeOptions);
    clusterServer.run();
    System.out.println("Started raft server at port:"
        + clusterServer.getNode().getNodeId().getPeerId().getPort());
  }

  /**
   * Redirect request to new leader
   */
  public String redirect() {
    if (this.node != null) {
      final PeerId leader = this.node.getLeaderId();
      if (leader != null) {
        return leader.getEndpoint().toString();
      }
    }
    return "";
  }


}
