package com.distkv.asyncclient;

import com.alipay.sofa.jraft.RouteTable;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.CliOptions;
import com.alipay.sofa.jraft.rpc.CliClientService;
import com.alipay.sofa.jraft.rpc.impl.cli.CliClientServiceImpl;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

/**
 * @author : kairbon
 * @date : 2021/4/30
 */
public class RaftAsyncClient implements DistkvAsyncClient {

  private static final String typeCode = "X";

  /// The `DistkvSyncClient` is wrapped with a `DistkvAsyncClient`.
  private DistkvAsyncClient asyncClient;

  private final CliClientService cliClientService;

  private volatile String leaderAddr;

  private String groupId;

  public RaftAsyncClient(String groupId, String clusterConfig) {
    final Configuration conf = new Configuration();

    if (!conf.parse(clusterConfig)) {
      throw new IllegalArgumentException("Fail to parse conf:" + clusterConfig);
    }
    RouteTable.getInstance().updateConfiguration(groupId, conf);
    this.groupId = groupId;

    cliClientService = new CliClientServiceImpl();
    cliClientService.init(new CliOptions());

    refreshLeader();
  }

  private void refreshLeader() {
    try {
      if (!RouteTable.getInstance().refreshLeader(cliClientService, groupId, 1000).isOk()) {
        throw new IllegalStateException("Refresh leader failed");
      }
      final PeerId leader = RouteTable.getInstance().selectLeader(groupId);
      if (!leader.getIp().equals(leaderAddr)) {
        leaderAddr = leader.getIp();
        //TODO(kairbon): Replace this line using conf
        refreshDistkvClient(String.format("distkv://%s:%d", leaderAddr, 8082));
      }
    } catch (InterruptedException | TimeoutException e) {
      e.printStackTrace();
    }
  }

  private void refreshDistkvClient(String serverAddress) {
    asyncClient = new DefaultAsyncClient(serverAddress);
  }

  @Override
  public boolean connect() {
    return asyncClient.connect();
  }

  @Override
  public boolean isConnected() {
    return asyncClient.isConnected();
  }

  @Override
  public boolean disconnect() {
    return asyncClient.disconnect();
  }

  @Override
  public void activeNamespace(String namespace) {
    refreshLeader();
    asyncClient.activeNamespace(namespace);
  }

  @Override
  public void deactiveNamespace() {
    refreshLeader();
    asyncClient.deactiveNamespace();
  }

  @Override
  public String getActivedNamespace() {
    refreshLeader();
    return asyncClient.getActivedNamespace();
  }

  @Override
  public CompletableFuture<DistkvProtocol.DistkvResponse> drop(String key) {
    refreshLeader();
    return asyncClient.drop(key);
  }

  @Override
  public CompletableFuture<DistkvProtocol.DistkvResponse> expire(String key, long expireTime) {
    refreshLeader();
    return asyncClient.expire(key, expireTime);
  }

  @Override
  public CompletableFuture<DistkvProtocol.DistkvResponse> exists(String key) {
    refreshLeader();
    return asyncClient.exists(key);
  }

  @Override
  public CompletableFuture<DistkvProtocol.DistkvResponse> ttl(String key) {
    refreshLeader();
    return asyncClient.ttl(key);
  }

  @Override
  public DistkvAsyncStringProxy strs() {
    refreshLeader();
    return asyncClient.strs();
  }

  @Override
  public DistkvAsyncListProxy lists() {
    refreshLeader();
    return asyncClient.lists();
  }

  @Override
  public DistkvAsyncSetProxy sets() {
    refreshLeader();
    return asyncClient.sets();
  }

  @Override
  public DistkvAsyncDictProxy dicts() {
    refreshLeader();
    return asyncClient.dicts();
  }

  @Override
  public DistkvAsyncSlistProxy slists() {
    refreshLeader();
    return asyncClient.slists();
  }

  @Override
  public DistkvAsyncIntProxy ints() {
    refreshLeader();
    return asyncClient.ints();
  }
}
