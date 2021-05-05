package com.distkv.distributed;

import com.distkv.common.NodeInfo;
import com.distkv.common.NodeState;
import com.distkv.common.id.NodeId;
import com.distkv.common.utils.RuntimeUtil;
import com.distkv.server.metaserver.client.DmetaClient;
import com.distkv.server.metaserver.server.bean.GetGlobalViewResponse;
import com.distkv.server.view.NodeTable;
import com.distkv.supplier.MasterSlaveSyncTestUtil;
import com.distkv.utils.DistkvNodesManager;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class NodeDeadDetectionTest {

  public void testMetaServerDetectNodeDead() {
    System.out.println(String.format("\n==================== Running the test method: %s.%s",
        "KillingNodeTest", "test"));
    DistkvNodesManager nodesManager = new DistkvNodesManager(3);
    nodesManager.startAllMetaServers();
    try {
      // It will take some time for MetaServer to start.
      TimeUnit.MILLISECONDS.sleep(500);
      DmetaClient client = new DmetaClient(nodesManager.getMetaServerAddresses());
      NodeInfo nodeInfo = NodeInfo.newBuilder()
          .setAddress("test")
          .setNodeId(NodeId.nil())
          .setIsMaster(false)
          .build();
      client.heartbeat(nodeInfo);
      GetGlobalViewResponse globalViewResponse0 = client.getGlobalView();
      Assert.assertEquals(globalViewResponse0
              .getGlobalView().get("1").getMap().get("test").getState(),
          NodeState.RUNNING);
      // When the time exceeds 3500 ms,
      // the state of node will be changed from running to dead by MetaServer.
      TimeUnit.SECONDS.sleep(4);
      GetGlobalViewResponse globalViewResponse1 = client.getGlobalView();
      Assert.assertEquals(globalViewResponse1
              .getGlobalView().get("1").getMap().get("test").getState(),
          NodeState.DEAD);
    } catch (Exception e) {
      Assert.fail();
    } finally {
      nodesManager.stopAllMetaServers();
    }
  }

  public void testKillSingleNode() {
    DistkvNodesManager nodesManager = new DistkvNodesManager(3);
    nodesManager.startAllMetaServers();
    try {
      // Sleep a while to wait all meta servers get started.
      // TODO(qwang): This should be refined as that `startAllMetaServerProcesses`
      // should do the promise.
      RuntimeUtil.sleepWithoutException(500);

      DmetaClient client = new DmetaClient(nodesManager.getMetaServerAddresses());
      GetGlobalViewResponse globalViewResponse = client.getGlobalView();
      // There is no any store server in global view.
      Assert.assertTrue(globalViewResponse.getGlobalView().isEmpty());

      // Start 3 store servers to connect to the MetaServer.
      MasterSlaveSyncTestUtil.startAGroupOfStoreServers();
      RuntimeUtil.sleepWithoutException(10 * 1000);
      globalViewResponse = client.getGlobalView();
      NodeTable nodeTable = globalViewResponse.getGlobalView().get("1");
      Assert.assertEquals(nodeTable.getMap().size(), 3);

      // Kill a StoreServer.
      MasterSlaveSyncTestUtil.killOneStoreServerRandomly();
      RuntimeUtil.sleepWithoutException(10 * 1000);
      globalViewResponse = client.getGlobalView();
      nodeTable = globalViewResponse.getGlobalView().get("1");
      // TODO(qwang): Enable this assertion once we enabled the timeout
      // of store server in meta server.
      Assert.assertEquals(nodeTable.getMap().size(), 3);
      int deadNode = 0;
      for (String key : nodeTable.getMap().keySet()) {
        if (nodeTable.getMap().get(key).getState() == NodeState.DEAD) {
          deadNode += 1;
        }
      }
      Assert.assertEquals(deadNode, 1);
    } catch (Exception e) {
      Assert.fail();
    } finally {
      nodesManager.stopAllMetaServers();
      MasterSlaveSyncTestUtil.stopAGroupOfStoreServers();
    }
  }
}