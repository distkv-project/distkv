package com.distkv.dmeta.server;

import com.alipay.remoting.rpc.RpcServer;
import com.alipay.sofa.jraft.Node;
import com.alipay.sofa.jraft.RaftGroupService;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.NodeOptions;
import com.alipay.sofa.jraft.rpc.RaftRpcServerFactory;
import org.apache.commons.io.FileUtils;
import com.distkv.dmeta.server.statemachine.DmetaStateMachine;
import com.distkv.dmeta.server.processor.GetValueRequestProcessor;
import com.distkv.dmeta.server.processor.PutKVRequestProcessor;

import java.io.File;
import java.io.IOException;

public class DmetaServer {

  private RaftGroupService raftGroupService;
  private Node node;
  private DmetaStateMachine fsm;

  public DmetaServer(final String dataPath, final String groupId, final PeerId serverId,
                     final NodeOptions nodeOptions) throws IOException {
    // init path
    FileUtils.forceMkdir(new File(dataPath));

    // make raft RPC and work RPC use same RPC server
    final RpcServer rpcServer = new RpcServer(serverId.getPort());
    RaftRpcServerFactory.addRaftRequestProcessors(rpcServer);
    // Registration processor
    rpcServer.registerUserProcessor(new GetValueRequestProcessor(this));
    rpcServer.registerUserProcessor(new PutKVRequestProcessor(this));
    // init StateMachine
    this.fsm = new DmetaStateMachine();
    // set StateMachine
    nodeOptions.setFsm(this.fsm);
    // set data path
    // log
    nodeOptions.setLogUri(dataPath + File.separator + "log");
    // meta info
    nodeOptions.setRaftMetaUri(dataPath + File.separator + "raft_meta");
    // snapshot,optional
    nodeOptions.setSnapshotUri(dataPath + File.separator + "counter_snapshot");
    // init raft group framework
    this.raftGroupService = new RaftGroupService(groupId, serverId, nodeOptions, rpcServer);
    // start
    this.node = this.raftGroupService.start();
  }

  public DmetaStateMachine getFsm() {
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
    // Set the election timeout to 1 second.
    nodeOptions.setElectionTimeoutMs(1000);
    // close CLI service。
    nodeOptions.setDisableCli(false);
    //30s snapshot
    nodeOptions.setSnapshotIntervalSecs(30);
    // parser
    final PeerId serverId = new PeerId();
    if (!serverId.parse(serverIdStr)) {
      throw new IllegalArgumentException("Fail to parse serverId:" + serverIdStr);
    }
    final Configuration initConf = new Configuration();
    if (!initConf.parse(initConfStr)) {
      throw new IllegalArgumentException("Fail to parse initConf:" + initConfStr);
    }
    // set origin conf
    nodeOptions.setInitialConf(initConf);

    // start
    final DmetaServer counterServer = new DmetaServer(dataPath, groupId, serverId, nodeOptions);
    System.out.println("Started DMeta server at port:"
        + counterServer.getNode().getNodeId().getPeerId().getPort());
  }
}
