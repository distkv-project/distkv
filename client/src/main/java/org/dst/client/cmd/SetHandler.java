package org.dst.client.cmd;

import java.util.Arrays;
import java.util.HashSet;
import org.dst.client.DefaultDstClient;
import org.dst.exception.DstException;
import org.dst.exception.KeyNotFoundException;

public class SetHandler extends Handler {

  public SetHandler(DefaultDstClient client) {
    super(client);
  }

  /**
   * @param cmd [put k1 v1 v2 v3 v3]、[get k1]
   * @return ok、[v1 v2 v3]
   */
  @Override
  public ClientResult getCmdResult(String[] cmd) {
    String result;

    if ("put".equals(cmd[0])) {
      try {
        String[] str = Arrays.copyOfRange(cmd, 2, cmd.length);
        client.sets().put(cmd[1], new HashSet<>(Arrays.asList(str)));
        result = "ok";
      } catch (ArrayIndexOutOfBoundsException e) {
        result = "not ok";
      }
    } else if ("get".equals(cmd[0])) {
      try {
        result = client.sets().get(cmd[1]).toString();
      } catch (KeyNotFoundException e) {
        result = "the key:" + e.getKey() + " is not found";
      } catch (DstException e) {
        result = e.toString();
      } catch (Exception e) {
        result = "not ok";
      }
    } else if ("del".equals(cmd[0])) {
      try {
        client.sets().delete(cmd[1], cmd[2]);
        result = "ok";
      } catch (KeyNotFoundException e) {
        result = "the key:" + e.getKey() + " is not found";
      } catch (DstException e) {
        result = e.toString();
      } catch (Exception e) {
        result = "not ok";
      }
    } else if ("drop".equals(cmd[0])) {
      try {
        client.sets().dropByKey(cmd[1]);
        result = "ok";
      } catch (KeyNotFoundException e) {
        result = "the key:" + e.getKey() + " is not found";
      } catch (DstException e) {
        result = e.toString();
      } catch (Exception e) {
        result = "not ok";
      }
    } else if ("exists".equals(cmd[0])) {
      try {
        result = String.valueOf(client.sets().exists(cmd[1], cmd[2]));
      } catch (KeyNotFoundException e) {
        result = "the key:" + e.getKey() + " is not found";
      } catch (DstException e) {
        result = e.toString();
      } catch (Exception e) {
        result = "not ok";
      }
    } else {
      result = "Unsupport operation";
    }
    clientResult.setResult(result);
    return clientResult;
  }

  @Override
  public ClientResult apply(DstCommandWithType commandWithType) {
    return getCmdResult(commandWithType.getCommand());
  }
}
