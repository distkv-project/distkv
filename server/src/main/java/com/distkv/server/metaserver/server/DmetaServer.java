package com.distkv.server.metaserver.server;

import com.alipay.sofa.jraft.Node;
import com.alipay.sofa.jraft.RaftGroupService;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.NodeOptions;
import com.alipay.sofa.jraft.rpc.RaftRpcServerFactory;
import com.alipay.sofa.jraft.rpc.RpcServer;
import com.distkv.server.metaserver.server.processor.GetGlobalViewRequestProcessor;
import com.distkv.server.metaserver.server.processor.HeartbeatRequestProcessor;
import com.distkv.server.metaserver.server.statemachine.MetaStateMachine;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class DmetaServer {

  private RaftGroupService raftGroupService;
  private Node node;
  private MetaStateMachine fsm;

  public DmetaServer(final String dataPath, final String groupId, final PeerId serverId,
                     final NodeOptions nodeOptions) throws IOException {
    // init path
    FileUtils.forceMkdir(new File(dataPath));

    // make raft RPC and work RPC use same RPC server
    final RpcServer rpcServer = RaftRpcServerFactory.createRaftRpcServer(serverId.getEndpoint());
    // Registration processor
    rpcServer.registerProcessor(new HeartbeatRequestProcessor(this));
    rpcServer.registerProcessor(new GetGlobalViewRequestProcessor(this));
    // init StateMachine
    this.fsm = new MetaStateMachine();
    // set StateMachine
    nodeOptions.setFsm(this.fsm);
    // set data path
    // log
    nodeOptions.setLogUri(dataPath + File.separator + "log" + serverId.getPort());
    // meta info
    nodeOptions.setRaftMetaUri(dataPath + File.separator + "raft_meta");
    // snapshot,optional
    nodeOptions.setSnapshotUri(dataPath + File.separator + "counter_snapshot");
    // init raft group framework
    this.raftGroupService = new RaftGroupService(groupId, serverId, nodeOptions, rpcServer);
    // start
    this.node = this.raftGroupService.start();
  }


  public MetaStateMachine getFsm() {
    return this.fsm;
  }

  public Node getNode() {
    return this.node;
  }

  public RaftGroupService getRaftGroupService() {
    return this.raftGroupService;
  }

  /**
   * Get Redirect to new leader
   */
  public String getRedirect() {
    if (this.node != null) {
      final PeerId leader = this.node.getLeaderId();
      if (leader != null) {
        return leader.toString();
      }
    }
    return new String();
  }

  public static void main(final String[] args) throws IOException {
    if (args.length != 4) {
      System.out
          .println("Useage :  {dataPath} {groupId} {serverId} {initConf}");
      System.out
          .println("Example:  /tmp/server1 counter 127.0.0.1:8081 " +
              "127.0.0.1:8081,127.0.0.1:8082,127.0.0.1:8083");
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
    final DmetaServer dmetaServer = new DmetaServer(dataPath, groupId, serverId, nodeOptions);
    System.out.println("Started counter server at port:"
        + dmetaServer.getNode().getNodeId().getPeerId().getPort());
  }
}
