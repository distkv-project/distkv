package com.distkv.server.storeserver.cluster;

import com.alipay.sofa.jraft.RouteTable;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.CliOptions;
import com.alipay.sofa.jraft.rpc.impl.cli.BoltCliClientService;
import com.distkv.client.DefaultDistkvClient;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.concurrent.TimeoutException;

/**
 * @author : kairbon
 * @date : 2021/3/25
 */
public class SimpleClient {


  public static final String RAFT_GROUP_ID = "test";

  public static void main(String[] args) throws
      TimeoutException, InterruptedException, InvalidProtocolBufferException {
    BoltCliClientService cliClientService = null;
    String groupId = "test";
    String confStr = "127.0.0.1:18080";
    final Configuration conf = new Configuration();
    if (!conf.parse(confStr)) {
      throw new IllegalArgumentException("Fail to parse conf:" + confStr);
    }

    RouteTable.getInstance().updateConfiguration(groupId, conf);

    cliClientService = new BoltCliClientService();
    cliClientService.init(new CliOptions());


    if (!RouteTable.getInstance().refreshLeader(cliClientService, groupId, 1000).isOk()) {
      throw new IllegalStateException("Refresh leader failed");
    }

    final PeerId leader = RouteTable.getInstance().selectLeader(groupId);
    System.out.println("Leader is " + leader);

    DefaultDistkvClient client = new DefaultDistkvClient(
        String.format("distkv://%s:%d", leader.getIp(), 8082));

    client.ints().put("k1", 1);
    int k1 = client.ints().get("k1");
    System.out.println(k1);

  }

  public void refreshLeader(BoltCliClientService cliClientService) throws
      TimeoutException, InterruptedException {

    if (!RouteTable.getInstance().refreshLeader(cliClientService, RAFT_GROUP_ID, 1000).isOk()) {
      throw new IllegalStateException("Refresh leader failed");
    }

  }
}
