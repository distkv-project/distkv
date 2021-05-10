package com.distkv.server.metaserver.client;

import com.alipay.sofa.jraft.JRaftUtils;
import com.alipay.sofa.jraft.RouteTable;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.CliOptions;
import com.alipay.sofa.jraft.rpc.impl.cli.CliClientServiceImpl;
import com.distkv.common.NodeInfo;
import com.distkv.server.metaserver.server.bean.GetGlobalViewRequest;
import com.distkv.server.metaserver.server.bean.GetGlobalViewResponse;
import com.distkv.server.metaserver.server.bean.HeartbeatRequest;
import com.distkv.server.metaserver.server.bean.HeartbeatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeoutException;

public class DmetaClient {

  private final CliClientServiceImpl cliClientService;

  private static final Logger LOG = LoggerFactory.getLogger(DmetaClient.class);

  public static final String RAFT_GROUP_ID = "META_SERVER";

  public static int HEARTBEAT_TIMEOUT = 3000;

  public DmetaClient(String confStr) {

    final Configuration conf = JRaftUtils.getConfiguration(confStr);

    RouteTable.getInstance().updateConfiguration(RAFT_GROUP_ID, conf);

    //init RPC client and update Routing table
    cliClientService = new CliClientServiceImpl();
    cliClientService.init(new CliOptions());
    refreshLeader();
  }

  public HeartbeatResponse heartbeat(NodeInfo nodeInfo) {
    try {
      // Get leader.
      final PeerId leader = RouteTable.getInstance().selectLeader(RAFT_GROUP_ID);

      final HeartbeatRequest request = new HeartbeatRequest(nodeInfo);

      HeartbeatResponse response = (HeartbeatResponse) cliClientService.getRpcClient()
          .invokeSync(leader.getEndpoint(), request, HEARTBEAT_TIMEOUT);
      if (!response.isSuccess()) {
        if (response.getRedirect().length() > 0) {
          refreshLeader();
          return null;
        }
      }
      return response;
      // TODO(kairbon): Need to handle these exception.
    } catch (com.alipay.sofa.jraft.error.RemotingException e) {
      refreshLeader();
      return null;
    } catch (InterruptedException e) {
      return null;
    }
  }

  public GetGlobalViewResponse getGlobalView() {
    // Get leader.
    final PeerId leader = RouteTable.getInstance().selectLeader(RAFT_GROUP_ID);
    final GetGlobalViewRequest request = new GetGlobalViewRequest();

    try {
      return (GetGlobalViewResponse) cliClientService.getRpcClient()
          .invokeSync(leader.getEndpoint(), request, HEARTBEAT_TIMEOUT);
    } catch (com.alipay.sofa.jraft.error.RemotingException e) {
      refreshLeader();
      return null;
    } catch (InterruptedException e) {
      return null;
    }

  }

  public void refreshLeader() {
    try {
      if (!RouteTable.getInstance().refreshLeader(cliClientService, RAFT_GROUP_ID, 1000).isOk()) {
        throw new IllegalStateException("Refresh leader failed");
      }
    } catch (InterruptedException | TimeoutException e) {
      LOG.error("Refresh leader failed");
    }
  }

}
