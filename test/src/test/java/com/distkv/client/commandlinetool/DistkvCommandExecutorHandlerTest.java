package com.distkv.client.commandlinetool;

import com.distkv.client.DistkvClient;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.timeunit.TimeUnit;
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
      command = "str.put str_k1 v1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .strPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'str.get'
      command = "str.get str_k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .strGet(distkvClient, distKVParsedResult), "v1");

      // Test command 'drop'
      command = "drop str_k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .drop(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'list.put'
      command = "list.put list_k1 v2 v1 v3";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'list.get'
      command = "list.get list_k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listGet(distkvClient, distKVParsedResult), "[v2, v1, v3]");

      // Test command 'list.lput'
      command = "list.lput list_k1 v4 v5";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listLPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'list.rput'
      command = "list.rput list_k1 v6 v7";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listRPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'list.listRemoveOne';
      command = "list.remove list_k1 0";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listRemove(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'list.listRemoveRange'
      command = "list.remove list_k1 0 2";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listRemove(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'list.mremove'
      command = "list.mremove list_k1 0 2";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .listMRemove(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'drop'
      command = "drop list_k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .drop(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'set.put'
      command = "set.put set_k1 v1 v2 v3";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .setPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'set.get'
      command = "set.get set_k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .setGet(distkvClient, distKVParsedResult), "{v1, v2, v3}");

      // Test command 'set.putItem'
      command = "set.putItem set_k1 v0";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .setPutItem(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'set.remove'
      command = "set.remove set_k1 v0";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .setRemoveItem(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'set.removeItem'
      command = "set.removeItem set_k1 v1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .setRemoveItem(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'set.exists'
      command = "set.exists set_k1 v1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .setExists(distkvClient, distKVParsedResult), "false");

      // Test command 'drop'
      command = "drop set_k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .drop(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'dict.put'
      command = "dict.put dict_key k1 v1 k2 v2";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .dictPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'dict.get'
      command = "dict.get dict_key";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .dictGet(distkvClient, distKVParsedResult), "{ k1 : v1, k2 : v2}");

      // Test command 'dict.putItem'
      command = "dict.putItem dict_key k3 v3";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .dictPutItem(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'dict.getItem'
      command = "dict.getItem dict_key k3";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .dictGetItem(distkvClient, distKVParsedResult), "v3");

      // Test command 'dict.popItem'
      command = "dict.popItem dict_key k3";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .dictPopItem(distkvClient, distKVParsedResult), "v3");

      // Test command 'dict.removeItem'
      command = "dict.removeItem dict_key k2";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .dictRemoveItem(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'drop'
      command = "drop dict_key";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .drop(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'slist.put'
      command = "slist.put slist_k1 m1 12 m2 -2 m3 0";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'slist.top'
      command = "slist.top slist_k1 2";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistTop(distkvClient, distKVParsedResult), "[(m1, 12), (m3, 0)]");

      // Test command 'slistIncrScoreDefault'
      command = "slist.incrScore slist_k1 m2";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistIncrScore(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'slistIncrScoreDelta'
      command = "slist.incrScore slist_k1 m1 10";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistIncrScore(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'slist.putMember'
      command = "slist.putMember slist_k1 m4 -3";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistPutMember(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'slist.removeMember'
      command = "slist.removeMember slist_k1 m4";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistRemoveMember(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'slist.getMember'
      command = "slist.getMember slist_k1 m2";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .slistGetMember(distkvClient, distKVParsedResult), "(m2, -1), 3rd");

      // Test command 'drop'
      command = "drop slist_k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(CommandExecutorHandler
          .drop(distkvClient, distKVParsedResult), STATUS_OK);

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
      command = "int.put int_k1 12";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(
          CommandExecutorHandler.intPut(distkvClient, distKVParsedResult), STATUS_OK);

      // Test command 'int.get'
      command = "int.get int_k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(
          CommandExecutorHandler.intGet(distkvClient, distKVParsedResult), String.valueOf(12));

      // Test command 'int.incr'
      command = "int.incr int_k1 -3";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(
          CommandExecutorHandler.intIncr(distkvClient, distKVParsedResult), STATUS_OK);
      Assert.assertEquals(
          CommandExecutorHandler.intGet(
              distkvClient, distkvParser.parse("int.get int_k1")), String.valueOf(9));
      command = "int.incr int_k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(
          CommandExecutorHandler.intIncr(distkvClient, distKVParsedResult), STATUS_OK);
      Assert.assertEquals(
          CommandExecutorHandler.intGet(
              distkvClient, distkvParser.parse("int.get int_k1")), String.valueOf(10));

      // Test command 'int.drop'
      command = "drop int_k1";
      distKVParsedResult = distkvParser.parse(command);
      Assert.assertEquals(
          CommandExecutorHandler.drop(distkvClient, distKVParsedResult), STATUS_OK);

    } finally {
      distkvClient.disconnect();
    }
  }

  @Test
  public void testListGetOne() {
    distkvClient = newDistkvClient();
    final DistkvParser distkvParser = new DistkvParser();

    String command = "list.put k1 v2 v1 v3 v4 v5 v7 v9";
    DistkvParsedResult distKVParsedResult = distkvParser.parse(command);
    CommandExecutorHandler.listPut(distkvClient, distKVParsedResult);

    // Test command 'list.get'
    command = "list.get k1 3";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .listGet(distkvClient, distKVParsedResult), "[v4]");

    //'drop'
    command = "drop k1";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(
        CommandExecutorHandler.drop(distkvClient, distKVParsedResult), STATUS_OK);
  }

  @Test
  public void testListGetRange() {
    distkvClient = newDistkvClient();
    final DistkvParser distkvParser = new DistkvParser();
    String command = "list.put k1 v2 v1 v3 v4 v5 v7 v9";
    DistkvParsedResult distKVParsedResult = distkvParser.parse(command);
    CommandExecutorHandler.listPut(distkvClient, distKVParsedResult);

    // Test command 'list.get'
    command = "list.get k1 3 5";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .listGet(distkvClient, distKVParsedResult), "[v4, v5]");

    //'drop'
    command = "drop k1";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(
        CommandExecutorHandler.drop(distkvClient, distKVParsedResult), STATUS_OK);
  }

  @Test
  public void expireStrTest() {
    distkvClient = newDistkvClient();
    final DistkvParser distkvParser = new DistkvParser();
    DistkvParsedResult distKVParsedResult;
    String command;
    // Put operation.
    command = "str.put str_k1 v1";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .strPut(distkvClient, distKVParsedResult), STATUS_OK);
    // Expire operation.
    command = "expire str_k1 1000";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .expire(distkvClient, distKVParsedResult), STATUS_OK);

    //Test KeyNotFoundException.
    command = "str.get str_k1";
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
    command = "list.put list_k1 v2 v1 v3";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .listPut(distkvClient, distKVParsedResult), STATUS_OK);
    // Expire operation.
    command = "expire list_k1 1000";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .expire(distkvClient, distKVParsedResult), STATUS_OK);
    // Test KeyNotFoundException.
    command = "list.get list_k1";
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
    command = "set.put set_k1 v1 v2 v3";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .setPut(distkvClient, distKVParsedResult), STATUS_OK);

    // Expire operation.
    command = "expire set_k1 1000";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .expire(distkvClient, distKVParsedResult), STATUS_OK);
    // Test KeyNotFoundException.
    command = "set.get set_k1";
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
    command = "dict.put dict_key k1 v1 k2 v2";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .dictPut(distkvClient, distKVParsedResult), STATUS_OK);
    // Expire operation.
    command = "expire dict_key 1000";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .expire(distkvClient, distKVParsedResult), STATUS_OK);
    // Test KeyNotFoundException.
    command = "dict.get dict_key";
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
    command = "slist.put slist_k1 m1 12 m2 -2 m3 0";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .slistPut(distkvClient, distKVParsedResult), STATUS_OK);
    // Expire operation.
    command = "expire slist_k1 1000";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .expire(distkvClient, distKVParsedResult), STATUS_OK);
    // Test KeyNotFoundException.
    command = "slist.top slist_k1 2";
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
    command = "int.put int_k1 12";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(
        CommandExecutorHandler.intPut(distkvClient, distKVParsedResult), STATUS_OK);
    // Expire operation
    command = "expire int_k1 1000";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .expire(distkvClient, distKVParsedResult), STATUS_OK);

    // Test KeyNotFoundException.
    command = "int.get int_k1";
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

  @Test
  public void testExists() {
    distkvClient = newDistkvClient();
    final DistkvParser distkvParser = new DistkvParser();
    DistkvParsedResult distKVParsedResult;
    String command;
    // Put operation.
    command = "str.put str_k1 v1";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .strPut(distkvClient, distKVParsedResult), STATUS_OK);
    // Exist operation.
    command = "exists str_k1";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .exists(distkvClient, distKVParsedResult), "true");
    //'drop'
    command = "drop str_k1";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(
        CommandExecutorHandler.drop(distkvClient, distKVParsedResult), STATUS_OK);
  }

  @Test
  public void testTtlNoExpire() {
    distkvClient = newDistkvClient();
    final DistkvParser distkvParser = new DistkvParser();
    DistkvParsedResult distKVParsedResult;
    String command;
    // Put operation.
    command = "str.put str_k1 v1";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .strPut(distkvClient, distKVParsedResult), STATUS_OK);

    // TTL whit no expire operation.
    command = "ttl str_k1";
    distKVParsedResult = distkvParser.parse(command);
    String timeToLive = CommandExecutorHandler.ttl(distkvClient, distKVParsedResult)
        .replace(TimeUnit.MILLISECOND, "");
    Assert.assertEquals(timeToLive, "-1");

    //'drop'
    command = "drop str_k1";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(
        CommandExecutorHandler.drop(distkvClient, distKVParsedResult), STATUS_OK);
  }

  @Test
  public void testTtlKeyNotFound() {
    distkvClient = newDistkvClient();
    final DistkvParser distkvParser = new DistkvParser();
    DistkvParsedResult distKVParsedResult;
    String command;
    command = "ttl str_k1";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertThrows(KeyNotFoundException.class, () -> CommandExecutorHandler
        .ttl(distkvClient, distKVParsedResult));
  }

  @Test
  public void testTtlWithExpire() {
    distkvClient = newDistkvClient();
    final DistkvParser distkvParser = new DistkvParser();
    DistkvParsedResult distKVParsedResult;
    String command;
    // Put operation.
    command = "str.put str_k1 v1";
    distKVParsedResult = distkvParser.parse(command);
    Assert.assertEquals(CommandExecutorHandler
        .strPut(distkvClient, distKVParsedResult), STATUS_OK);

    // Expire operation
    command = "expire str_k1 2000";
    distKVParsedResult = distkvParser.parse(command);
    CommandExecutorHandler.expire(distkvClient, distKVParsedResult);

    // TTL key not expire operation.
    command = "ttl str_k1";
    final DistkvParsedResult ttlParsedResult = distkvParser.parse(command);
    boolean ttlResult = RuntimeUtil.waitForCondition(() -> {
      String timeToLive = CommandExecutorHandler.ttl(distkvClient, ttlParsedResult)
          .replace(TimeUnit.MILLISECOND, "");
      long result = Long.parseLong(timeToLive);
      return result < 2000 && result > 0;
    }, 1000);
    Assert.assertTrue(ttlResult);

    // TTL key expire operation.
    command = "ttl str_k1";
    DistkvParsedResult finalDistKVParsedResult = distkvParser.parse(command);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        CommandExecutorHandler.ttl(distkvClient, finalDistKVParsedResult);
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
  }
}
