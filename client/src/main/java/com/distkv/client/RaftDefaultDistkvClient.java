package com.distkv.client;

import com.alipay.sofa.jraft.RouteTable;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.CliOptions;
import com.alipay.sofa.jraft.rpc.CliClientService;
import com.alipay.sofa.jraft.rpc.impl.cli.CliClientServiceImpl;
import com.distkv.asyncclient.DefaultAsyncClient;
import com.distkv.asyncclient.DistkvAsyncClient;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.concurrent.TimeoutException;

/**
 * @author : kairbon
 * @date : 2021/4/8
 */
public class RaftDefaultDistkvClient implements DistkvClient {

  private static final String typeCode = "X";

  /// The `DistkvSyncClient` is wrapped with a `DistkvAsyncClient`.
  private DistkvAsyncClient asyncClient;

  private final CliClientService cliClientService;

  private volatile String leaderAddr;

  private String groupId;

  private DistkvStringProxy stringProxy;

  private DistkvListProxy listProxy;

  private DistkvSetProxy setProxy;

  private DistkvDictProxy dictProxy;

  private DistkvSlistProxy slistProxy;

  private DistkvIntProxy intProxy;


  public RaftDefaultDistkvClient(String groupId, String clusterConfig) {

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

    stringProxy = new DistkvStringProxy(asyncClient.strs());
    listProxy = new DistkvListProxy(asyncClient.lists());
    setProxy = new DistkvSetProxy(asyncClient.sets());
    dictProxy = new DistkvDictProxy(asyncClient.dicts());
    slistProxy = new DistkvSlistProxy(asyncClient.slists());
    intProxy = new DistkvIntProxy(asyncClient.ints());
  }

  @Override
  public boolean connect() {
    return true;
  }

  @Override
  public boolean isConnected() {
    return true;
  }

  @Override
  public boolean disconnect() {
    cliClientService.shutdown();
    asyncClient.disconnect();
    return true;
  }

  @Override
  public DistkvStringProxy strs() {
    refreshLeader();
    return stringProxy;
  }

  @Override
  public DistkvDictProxy dicts() {
    refreshLeader();
    return dictProxy;
  }

  @Override
  public DistkvListProxy lists() {
    refreshLeader();
    return listProxy;
  }

  @Override
  public DistkvSetProxy sets() {
    refreshLeader();
    return setProxy;
  }

  @Override
  public DistkvSlistProxy slists() {
    refreshLeader();
    return slistProxy;
  }

  @Override
  public DistkvIntProxy ints() {
    refreshLeader();
    return intProxy;
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
  public void drop(String key) {
    refreshLeader();
    DistkvProtocol.DistkvResponse response = FutureUtils.get(asyncClient.drop(key));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  @Override
  public void expire(String key, long expireTime) {
    refreshLeader();
    DistkvProtocol.DistkvResponse response = FutureUtils.get(asyncClient.expire(key, expireTime));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  @Override
  public boolean exists(String key) {
    refreshLeader();
    DistkvProtocol.DistkvResponse response = FutureUtils.get(asyncClient.exists(key));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    try {
      return response.getResponse().unpack(CommonProtocol.ExistsResponse.class).getExists();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  @Override
  public long ttl(String key) {
    refreshLeader();
    DistkvProtocol.DistkvResponse response = FutureUtils.get(asyncClient.ttl(key));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    try {
      return response.getResponse().unpack(CommonProtocol.TTLResponse.class).getTtl();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  @Override
  public String getActivedNamespace() {
    refreshLeader();
    return asyncClient.getActivedNamespace();
  }
}
