package org.dst.client.cmd;

import java.util.Arrays;
import org.dst.client.DefaultDstClient;
import org.dst.exception.DstException;
import org.dst.exception.KeyNotFoundException;

public class ListHandler extends Handler {

  public ListHandler(DefaultDstClient client) {
    super(client);
  }

  /**
   * @param cmd [put k1 v1 v2 v3]、[get k1]
   * @return ok、[v1 v2 v3]
   */
  @Override
  public ClientResult getCmdResult(String[] cmd) {
    String result;

    if ("put".equals(cmd[0])) {
      try {
        String[] str = Arrays.copyOfRange(cmd, 2, cmd.length);
        client.lists().put(cmd[1], Arrays.asList(str));
        result = "ok";
      } catch (ArrayIndexOutOfBoundsException e) {
        result = "not ok";
      }
    } else if ("get".equals(cmd[0])) {
      try {
        result = client.lists().get(cmd[1]).toString();
      } catch (KeyNotFoundException e) {
        result = "the key:" + e.getKey() + " is not found";
      } catch (DstException e) {
        result = e.toString();
      } catch (Exception e) {
        result = "not ok";
      }
    } else if ("lput".equals(cmd[0])) {
      try {
        String[] str = Arrays.copyOfRange(cmd, 2, cmd.length);
        client.lists().lput(cmd[1], Arrays.asList(str));
        result = "ok";
      } catch (KeyNotFoundException e) {
        result = "the key:" + e.getKey() + " is not found";
      } catch (DstException e) {
        result = e.toString();
      } catch (Exception e) {
        result = "not ok";
      }
    } else if ("rput".equals(cmd[0])) {
      try {
        String[] str = Arrays.copyOfRange(cmd, 2, cmd.length);
        client.lists().rput(cmd[1], Arrays.asList(str));
        result = "ok";
      } catch (KeyNotFoundException e) {
        result = "the key:" + e.getKey() + " is not found";
      } catch (DstException e) {
        result = e.toString();
      } catch (Exception e) {
        result = "not ok";
      }
    } else if ("ldel".equals(cmd[0])) {
      try {
        client.lists().ldel(cmd[1], Integer.parseInt(cmd[2]));
        result = "ok";
      } catch (KeyNotFoundException e) {
        result = "the key:" + e.getKey() + " is not found";
      } catch (DstException e) {
        result = e.toString();
      } catch (Exception e) {
        result = "not ok";
      }
    } else if ("rdel".equals(cmd[0])) {
      try {
        client.lists().rdel(cmd[1], Integer.parseInt(cmd[2]));
        result = "ok";
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
