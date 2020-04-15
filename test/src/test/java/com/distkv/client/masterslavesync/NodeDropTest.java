package com.distkv.client.masterslavesync;

import com.distkv.common.NodeInfo;
import com.distkv.common.NodeStatus;
import com.distkv.common.id.NodeId;
import com.distkv.server.metaserver.client.DmetaClient;
import com.distkv.server.metaserver.server.bean.GetGlobalViewResponse;
import com.distkv.server.metaserver.server.bean.HeartbeatResponse;
import com.distkv.supplier.DmetaTestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class NodeDropTest {

  @Test(singleThreaded = true)
  public void testNodeDrop() {
    System.out.println(String.format("\n==================== Running the test method: %s.%s",
        "NodeDropTest", "test"));
    DmetaTestUtil.startAllDmetaProcess();
    try {
      // It will take some time for MetaServer to start.
      TimeUnit.MILLISECONDS.sleep(500);
      DmetaClient client = new DmetaClient(DmetaTestUtil.DEFAULT_META_SERVER_ADDRESSES);
      NodeInfo nodeInfo = NodeInfo.newBuilder()
          .setAddress("test")
          .setNodeId(NodeId.nil())
          .setIsMaster(false)
          .build();
      HeartbeatResponse heartbeatResponse = client.heartbeat(nodeInfo);
      GetGlobalViewResponse globalViewResponse0 = client.getGlobalView();
      Assert.assertEquals(globalViewResponse0
          .getGlobalView().get("1").getMap().get("test").getStatus(),
          NodeStatus.RUNNING);
      // When the time exceeds 3500ms,
      // the state of node will be changed from running to dead by dmeta.
      TimeUnit.SECONDS.sleep(4);
      GetGlobalViewResponse globalViewResponse1 = client.getGlobalView();
      Assert.assertEquals(globalViewResponse1
              .getGlobalView().get("1").getMap().get("test").getStatus(),
          NodeStatus.DEAD);
    } catch (Exception e) {
      Assert.fail();
    } finally {
      DmetaTestUtil.stopAllDmetaProcess();
    }
  }
}
