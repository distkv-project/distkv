package com.distkv.metaserver.client;

import com.distkv.common.NodeInfo;
import com.distkv.server.metaserver.client.DmetaClient;
import com.distkv.server.metaserver.server.bean.HeartBeatResponse;
import com.distkv.supplier.DmetaTestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TestServer {
  @Test
  public void testd () throws InterruptedException {
    String dmetaServerList = "localhost:8081,localhost:8082,localhost:8083";
    System.out.println(String.format("\n==================== Running the test method: %s.%s",
        "DmetaTest", "testPutAndGet"));
    DmetaTestUtil.startAllDmetaProcess();
    TimeUnit.SECONDS.sleep(5);
    DmetaClient client = new DmetaClient(dmetaServerList);

    try {
      NodeInfo nodeInfo = new NodeInfo(true, "hello",
          String.format("distkv://127.0.0.1:%d", 10086));
      HeartBeatResponse response = client.heartBeat(nodeInfo);
      Assert.assertEquals(response.getNodeTable().get(nodeInfo.getNodeName()).isMaster(), true);
      Assert.assertEquals(response.getNodeTable().get(nodeInfo.getNodeName()).getNodeName(), "hello");
      TimeUnit.SECONDS.sleep(30);
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail();
    } finally {
      DmetaTestUtil.stopAllDmetaProcess();
    }
  }
}
