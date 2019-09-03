package org.dst.client.cmd;

import java.util.HashMap;
import java.util.Map;
import org.dst.client.DefaultDstClient;
import org.dst.exception.DstException;
import org.dst.exception.KeyNotFoundException;
import org.dst.exception.DictKeyNotFoundException;

public class DictHandler extends Handler {

  public DictHandler(DefaultDstClient client) {
    super(client);
  }

  /**
   * @param cmd [put dict1 k1 v1 k2 v2 k3 v3]、[get dict1]
   * @return ok、{k1=v1,k2=v2,k3=v3}
   */
  @Override
  public ClientResult getCmdResult(String[] cmd) {
    String result;

    if ("put".equals(cmd[0])) {
      try {
        Map<String, String> map = new HashMap<>();
        for (int i = 2; i < cmd.length; i = i + 2) {
          map.put(cmd[i], cmd[i + 1]);
        }
        client.dicts().put(cmd[1], map);
        result = "ok";
      } catch (ArrayIndexOutOfBoundsException e) {
        result = "not ok";
      }
    } else if ("get".equals(cmd[0])) {
      try {
        //dict.get dict1 k1
        if (cmd.length == 3) {
          result = client.dicts().getItemValue(cmd[1], cmd[2]);
        } else {
          result = client.dicts().get(cmd[1]).toString();
        }
      } catch (DictKeyNotFoundException e) {
        result = "the dictionary key:" + e.getKey() + " is not found";
      } catch (KeyNotFoundException e) {
        result = "the key:" + e.getKey() + " is not found";
      } catch (DstException e) {
        result = e.toString();
      } catch (Exception e) {
        result = "not ok";
      }
    } else if ("pop".equals(cmd[0])) {
      try {
        result = client.dicts().popItem(cmd[1], cmd[2]);
      } catch (DictKeyNotFoundException e) {
        result = "the dictionary key:" + e.getKey() + " is not found";
      } catch (KeyNotFoundException e) {
        result = "the key:" + e.getKey() + " is not found";
      } catch (DstException e) {
        result = e.toString();
      } catch (Exception e) {
        result = "not ok";
      }
    } else if ("del".equals(cmd[0])) {
      try {
        client.dicts().delItem(cmd[1], cmd[2]);
        result = "ok";
      } catch (DictKeyNotFoundException e) {
        result = "the dictionary key:" + e.getKey() + " is not found";
      } catch (KeyNotFoundException e) {
        result = "the key:" + e.getKey() + " is not found";
      } catch (DstException e) {
        result = e.toString();
      } catch (Exception e) {
        result = "not ok";
      }
    } else if ("drop".equals(cmd[0])) {
      try {
        client.dicts().del(cmd[1]);
        result = "ok";
      } catch (DictKeyNotFoundException e) {
        result = "the dictionary key:" + e.getKey() + " is not found";
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
