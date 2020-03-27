package com.distkv.server.metaserver.client;

import com.alipay.remoting.exception.RemotingException;
import com.alipay.sofa.jraft.JRaftUtils;
import com.alipay.sofa.jraft.RouteTable;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.CliOptions;
import com.alipay.sofa.jraft.rpc.impl.cli.BoltCliClientService;
import com.distkv.common.NodeInfo;
import com.distkv.server.metaserver.server.bean.HeartbeatRequest;
import com.distkv.server.metaserver.server.bean.HeartbeatResponse;

import java.util.concurrent.TimeoutException;

public class DmetaClient {

  private BoltCliClientService cliClientService = null;

  // TODO(qingw): Refine this.
  public static final String GROUP_ID = "KV";

  public DmetaClient(String confStr) {

    final Configuration conf = JRaftUtils.getConfiguration(confStr);

    RouteTable.getInstance().updateConfiguration(GROUP_ID, conf);

    //init RPC client and update Routing table
    cliClientService = new BoltCliClientService();
    cliClientService.init(new CliOptions());
  }

  public HeartbeatResponse heartbeat(NodeInfo nodeInfo, int timeout) {
    try {
      //refresh leader term.
      if (!RouteTable.getInstance().refreshLeader(cliClientService, GROUP_ID, 1000).isOk()) {
        throw new IllegalStateException("Refresh leader failed");
      }
      //get leader term.
      final PeerId leader = RouteTable.getInstance().selectLeader(GROUP_ID);

      final HeartbeatRequest request = new HeartbeatRequest(nodeInfo);

      HeartbeatResponse response = (HeartbeatResponse) cliClientService.getRpcClient()
          .invokeSync(leader.getEndpoint().toString(), request, timeout);
      if (!response.isSuccess()) {
        throw new RuntimeException("get error");
      }
      return response;
      // TODO(kairbon): Need to handle these exception.
    } catch (InterruptedException e) {
      return null;
    } catch (TimeoutException e) {
      return null;
    } catch (RemotingException e) {
      return null;
    }
  }

}
