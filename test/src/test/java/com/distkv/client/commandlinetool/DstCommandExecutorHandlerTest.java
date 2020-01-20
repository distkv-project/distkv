package com.distkv.client.commandlinetool;

import com.distkv.client.DstClient;
import com.distkv.parser.DistKVParser;
import com.distkv.parser.po.DistKVParsedResult;
import com.distkv.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DstCommandExecutorHandlerTest extends BaseTestSupplier {

  private static final String STATUS_OK = "ok";
  private DstClient dstClient = null;

  @Test
  public void testMain() {
    dstClient = newDstClient();
    final DistKVParser dstParser = new DistKVParser();

    DistKVParsedResult distKVParsedResult = null;
    String command = null;

    // Test str.put
    command = "str.put k1 v1";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .strPut(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'str.get'
    command = "str.get k1";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .strGet(dstClient, distKVParsedResult), "v1");

    // Test command 'str.drop'
    command = "str.drop k1";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .strDrop(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'list.put'
    command = "list.put k1 v2 v1 v3";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .listPut(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'list.get'
    command = "list.get k1";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .listGet(dstClient, distKVParsedResult), "[v2, v1, v3]");

    // Test command 'list.lput'
    command = "list.lput k1 v4 v5";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .listLPut(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'list.rput'
    command = "list.rput k1 v6 v7";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .listRPut(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'list.listRemoveOne';
    command = "list.remove k1 0";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .listRemove(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'list.listRemoveRange'
    command = "list.remove k1 0 2";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .listRemove(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'list.mremove'
    command = "list.mremove k1 0 2";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .listMRemove(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'list.drop'
    command = "list.drop k1";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .listDrop(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'set.put'
    command = "set.put k1 v1 v2 v3";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .setPut(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'set.get'
    command = "set.get k1";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .setGet(dstClient, distKVParsedResult), "{v1, v2, v3}");

    // Test command 'set.putItem'
    command = "set.putItem k1 v0";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .setPutItem(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'set.remove'
    command = "set.remove k1 v0";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .setRemoveItem(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'set.removeItem'
    command = "set.removeItem k1 v1";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .setRemoveItem(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'set.exists'
    command = "set.exists k1 v1";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .setExists(dstClient, distKVParsedResult), "false");

    // Test command 'set.drop'
    command = "set.drop k1";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .setDrop(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'dict.put'
    command = "dict.put key k1 v1 k2 v2";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .dictPut(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'dict.get'
    command = "dict.get key";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .dictGet(dstClient, distKVParsedResult), "{ k1 : v1, k2 : v2}");

    // Test command 'dict.putItem'
    command = "dict.putItem key k3 v3";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .dictPutItem(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'dict.getItem'
    command = "dict.getItem key k3";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .dictGetItem(dstClient, distKVParsedResult), "v3");

    // Test command 'dict.popItem'
    command = "dict.popItem key k3";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .dictPopItem(dstClient, distKVParsedResult), "v3");

    // Test command 'dict.removeItem'
    command = "dict.removeItem key k2";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .dictRemoveItem(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'dict.drop'
    command = "dict.drop key";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .dictDrop(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'slist.put'
    command = "slist.put k1 m1 12 m2 -2 m3 0";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .slistPut(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'slist.top'
    command = "slist.top k1 2";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .slistTop(dstClient, distKVParsedResult), "[(m1, 12), (m3, 0)]");

    // Test command 'slistIncrScoreDefault'
    command = "slist.incrScore k1 m2";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .slistIncrScore(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'slistIncrScoreDelta'
    command = "slist.incrScore k1 m1 10";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .slistIncrScore(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'slist.putMember'
    command = "slist.putMember k1 m4 -3";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .slistPutMember(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'slist.removeMember'
    command = "slist.removeMember k1 m4";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .slistRemoveMember(dstClient, distKVParsedResult), STATUS_OK);

    // Test command 'slist.getMember'
    command = "slist.getMember k1 m2";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .slistGetMember(dstClient, distKVParsedResult), "(m2, -1), 3rd");

    // Test command 'slist.drop'
    command = "slist.drop k1";
    distKVParsedResult = dstParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
            .slistDrop(dstClient, distKVParsedResult), STATUS_OK);

    dstClient.disconnect();
  }

}
