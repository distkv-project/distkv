package com.distkv.client.commandlinetool;

import com.distkv.client.DistkvClient;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.RuntimeUtil;
import com.distkv.parser.DistkvParser;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(singleThreaded = true)
public class DistkvCommandExecutorHandlerTest extends BaseTestSupplier {

  private static final String STATUS_OK = "ok";
  private DistkvClient distkvClient = null;

  @Test
  public void testMain() {
    distkvClient = newDistkvClient();
    try {
      final DistkvParser distkvParser = new DistkvParser();

      DistkvParsedResult distKVParsedResult = null;
      String command = null;

      // Test str.put
      command = "str.put k1 v1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .strPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'str.get'
      command = "str.get k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .strGet(distkvClient, distKVParsedResult), "v1");

      // Test command 'str.drop'
      command = "str.drop k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .strDrop(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'list.put'
      command = "list.put k1 v2 v1 v3";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'list.get'
      command = "list.get k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listGet(distkvClient, distKVParsedResult), "[v2, v1, v3]");

      // Test command 'list.lput'
      command = "list.lput k1 v4 v5";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listLPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'list.rput'
      command = "list.rput k1 v6 v7";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listRPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'list.listRemoveOne';
      command = "list.remove k1 0";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listRemove(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'list.listRemoveRange'
      command = "list.remove k1 0 2";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listRemove(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'list.mremove'
      command = "list.mremove k1 0 2";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listMRemove(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'list.drop'
      command = "list.drop k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listDrop(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'set.put'
      command = "set.put k1 v1 v2 v3";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .setPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'set.get'
      command = "set.get k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .setGet(distkvClient, distKVParsedResult), "{v1, v2, v3}");

      // Test command 'set.putItem'
      command = "set.putItem k1 v0";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .setPutItem(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'set.remove'
      command = "set.remove k1 v0";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .setRemoveItem(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'set.removeItem'
      command = "set.removeItem k1 v1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .setRemoveItem(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'set.exists'
      command = "set.exists k1 v1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .setExists(distkvClient, distKVParsedResult), "false");

      // Test command 'set.drop'
      command = "set.drop k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .setDrop(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'dict.put'
      command = "dict.put key k1 v1 k2 v2";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .dictPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'dict.get'
      command = "dict.get key";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .dictGet(distkvClient, distKVParsedResult), "{ k1 : v1, k2 : v2}");

      // Test command 'dict.putItem'
      command = "dict.putItem key k3 v3";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .dictPutItem(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'dict.getItem'
      command = "dict.getItem key k3";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .dictGetItem(distkvClient, distKVParsedResult), "v3");

      // Test command 'dict.popItem'
      command = "dict.popItem key k3";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .dictPopItem(distkvClient, distKVParsedResult), "v3");

      // Test command 'dict.removeItem'
      command = "dict.removeItem key k2";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .dictRemoveItem(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'dict.drop'
      command = "dict.drop key";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .dictDrop(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'slist.put'
      command = "slist.put k1 m1 12 m2 -2 m3 0";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'slist.top'
      command = "slist.top k1 2";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistTop(distkvClient, distKVParsedResult), "[(m1, 12), (m3, 0)]");

      // Test command 'slistIncrScoreDefault'
      command = "slist.incrScore k1 m2";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistIncrScore(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'slistIncrScoreDelta'
      command = "slist.incrScore k1 m1 10";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistIncrScore(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'slist.putMember'
      command = "slist.putMember k1 m4 -3";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistPutMember(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'slist.removeMember'
      command = "slist.removeMember k1 m4";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistRemoveMember(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'slist.getMember'
      command = "slist.getMember k1 m2";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistGetMember(distkvClient, distKVParsedResult), "(m2, -1), 3rd");

      // Test command 'slist.drop'
      command = "slist.drop k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistDrop(distkvClient, distKVParsedResult), STATUS_OK);

      // Test active namespace
      command = "active namespace a";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(
          CommandExecutorHandler.activeNamespace(distkvClient, distKVParsedResult), STATUS_OK);

      // Test Deactive namespace
      command = "deactive namespace";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(
          CommandExecutorHandler.deactiveNamespace(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'int.put'
      command = "int.put k1 12";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(
          CommandExecutorHandler.intPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'int.get'
      command = "int.get k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(
          CommandExecutorHandler.intGet(distkvClient, distKVParsedResult), String.valueOf(12));

      // Test command 'int.incr'
      command = "int.incr k1 -3";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(
          CommandExecutorHandler.intIncr(distkvClient, distKVParsedResult), STATUS_OK);
      Assert.assertEquals(
          CommandExecutorHandler.intGet(
              distkvClient, distkvParser.parse("int.get k1")), String.valueOf(9));
      command = "int.incr k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(
          CommandExecutorHandler.intIncr(distkvClient, distKVParsedResult), STATUS_OK);
      Assert.assertEquals(
          CommandExecutorHandler.intGet(
              distkvClient, distkvParser.parse("int.get k1")), String.valueOf(10));

      // Test command 'int.drop'
      command = "int.drop k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(
          CommandExecutorHandler.intDrop(distkvClient, distKVParsedResult), STATUS_OK);

    } finally {
      distkvClient.disconnect();
    }
  }

  @Test
  public void expireStrTest() {
    distkvClient = newDistkvClient();
    final DistkvParser distkvParser = new DistkvParser();
    DistkvParsedResult distKVParsedResult;
    String command;
    // Put operation.
    command = "str.put k1 v1";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .strPut(distkvClient, distKVParsedResult), STATUS_OK);
    // Expire operation.
    command = "expire.str k1 1000";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .expireStr(distkvClient, distKVParsedResult), STATUS_OK);

    //Test KeyNotFoundException.
    command = "str.get k1";
    final DistkvParsedResult finalDistKVParsedResult = distkvParser.parse(command);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        CommandExecutorHandler.strGet(distkvClient, finalDistKVParsedResult);
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
  }

  @Test
  public void expireListTest() {
    distkvClient = newDistkvClient();
    final DistkvParser distkvParser = new DistkvParser();
    DistkvParsedResult distKVParsedResult;
    String command;

    // Put operation.
    command = "list.put k1 v2 v1 v3";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .listPut(distkvClient, distKVParsedResult), STATUS_OK);
    // Expire operation.
    command = "expire.list k1 1000";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .expireList(distkvClient, distKVParsedResult), STATUS_OK);
    // Test KeyNotFoundException.
    command = "list.get k1";
    final DistkvParsedResult finalDistKVParsedResult = distkvParser.parse(command);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        CommandExecutorHandler.listGet(distkvClient, finalDistKVParsedResult);
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
  }

  @Test
  public void expireSetTest() {
    distkvClient = newDistkvClient();
    final DistkvParser distkvParser = new DistkvParser();
    DistkvParsedResult distKVParsedResult;
    String command;
    // Put operation.
    command = "set.put k1 v1 v2 v3";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .setPut(distkvClient, distKVParsedResult), STATUS_OK);

    // Expire operation.
    command = "expire.set k1 1000";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .expireSet(distkvClient, distKVParsedResult), STATUS_OK);
    // Test KeyNotFoundException.
    command = "set.get k1";
    final DistkvParsedResult finalDistKVParsedResult = distkvParser.parse(command);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        CommandExecutorHandler.setGet(distkvClient, finalDistKVParsedResult);
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
  }

  @Test
  public void expireDictTest() {
    distkvClient = newDistkvClient();
    final DistkvParser distkvParser = new DistkvParser();
    DistkvParsedResult distKVParsedResult;
    String command;
    // Put operation.
    command = "dict.put key k1 v1 k2 v2";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .dictPut(distkvClient, distKVParsedResult), STATUS_OK);
    // Expire operation.
    command = "expire.dict key 1000";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .expireDict(distkvClient, distKVParsedResult), STATUS_OK);
    // Test KeyNotFoundException.
    command = "dict.get key";
    final DistkvParsedResult finalDistKVParsedResult = distkvParser.parse(command);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        CommandExecutorHandler.dictGet(distkvClient, finalDistKVParsedResult);
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
  }

  @Test
  public void expireSlistTest() {
    distkvClient = newDistkvClient();
    final DistkvParser distkvParser = new DistkvParser();
    DistkvParsedResult distKVParsedResult;
    String command;
    // Put operation.
    command = "slist.put k1 m1 12 m2 -2 m3 0";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .slistPut(distkvClient, distKVParsedResult), STATUS_OK);
    // Expire operation.
    command = "expire.slist k1 1000";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .expireSlist(distkvClient, distKVParsedResult), STATUS_OK);
    // Test KeyNotFoundException.
    command = "slist.top k1 2";
    final DistkvParsedResult finalDistKVParsedResult = distkvParser.parse(command);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        CommandExecutorHandler.slistTop(distkvClient, finalDistKVParsedResult);
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
  }

  @Test
  public void expireIntsTest() {
    distkvClient = newDistkvClient();
    final DistkvParser distkvParser = new DistkvParser();
    DistkvParsedResult distKVParsedResult;
    String command;
    // Put operation.
    command = "int.put k1 12";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(
        CommandExecutorHandler.intPut(distkvClient, distKVParsedResult), STATUS_OK);
    // Expire operation
    command = "expire.int k1 1000";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .expireInt(distkvClient, distKVParsedResult), STATUS_OK);

    // Test KeyNotFoundException.
    command = "int.get k1";
    final DistkvParsedResult finalDistKVParsedResult = distkvParser.parse(command);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        CommandExecutorHandler.intGet(distkvClient, finalDistKVParsedResult);
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
  }
}
