package com.distkv.server.metaserver.client;

import com.alipay.remoting.exception.RemotingException;
import com.alipay.sofa.jraft.RouteTable;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.CliOptions;
import com.alipay.sofa.jraft.rpc.impl.cli.BoltCliClientService;
import com.distkv.server.metaserver.server.bean.GetRequest;
import com.distkv.server.metaserver.server.bean.GetResponse;
import com.distkv.server.metaserver.server.bean.PutRequest;
import com.distkv.server.metaserver.server.bean.PutResponse;

import java.util.concurrent.TimeoutException;

public class DmetaClient {

  private BoltCliClientService cliClientService = null;

  // TODO(qingw): Refine this.
  public static final String groupId = "KV";

  // TODO(qingw): Refine this.
  public static final String confStr = "127.0.0.1:8081,127.0.0.1:8082,127.0.0.1:8083";

  public DmetaClient() {

    final Configuration conf = new Configuration();
    if (!conf.parse(confStr)) {
      throw new IllegalArgumentException("Fail to parse conf:" + confStr);
    }

    RouteTable.getInstance().updateConfiguration(groupId, conf);

    //init RPC client and update Routing table
    cliClientService = new BoltCliClientService();
    cliClientService.init(new CliOptions());
  }

  public void put(String key, String value) {
    try {
      if (!RouteTable.getInstance().refreshLeader(cliClientService, groupId, 1000).isOk()) {
        throw new IllegalStateException("Refresh leader failed");
      }
      //get leader term
      final PeerId leader = RouteTable.getInstance().selectLeader(groupId);

      PutRequest request = new PutRequest();
      request.setKey(key);
      request.setValue(value);
      PutResponse response = (PutResponse) cliClientService.getRpcClient()
          .invokeSync(leader.getEndpoint().toString(), request, 3000);
      if (!response.isSuccess()) {
        throw new RuntimeException("put error");
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    } catch (RemotingException e) {
      e.printStackTrace();
    }
  }

  public String get(String key) {
    try {
      if (!RouteTable.getInstance().refreshLeader(cliClientService, groupId, 1000).isOk()) {
        throw new IllegalStateException("Refresh leader failed");
      }
      //get leader term
      final PeerId leader = RouteTable.getInstance().selectLeader(groupId);

      final GetRequest request = new GetRequest();
      request.setKey(key);

      GetResponse response = (GetResponse) cliClientService.getRpcClient()
          .invokeSync(leader.getEndpoint().toString(), request, 3000);
      if (!response.isSuccess()) {
        throw new RuntimeException("get error");
      }
      return response.getValue();

    } catch (InterruptedException e) {
      return null;
    } catch (TimeoutException e) {
      return null;
    } catch (RemotingException e) {
      return null;
    }
  }

}
