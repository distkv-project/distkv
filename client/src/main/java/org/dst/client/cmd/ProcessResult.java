package org.dst.client.cmd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.dst.client.DefaultDstClient;
import org.dst.exception.DstException;
import org.dst.exception.KeyNotFoundException;
import org.dst.exception.DictKeyNotFoundException;

public class ProcessResult {

  public static DefaultDstClient client = null;

  /**
   * @param cmd [put k1 v1] 、[get k1]
   * @return ok、v1
   */
  public String getStringResult(String[] cmd) {
    String result;

    if ("put".equals(cmd[0])) {
      if (cmd.length > 3) {
        return "only need a key and a value";
      }
      try {
        client.strs().put(cmd[1], cmd[2]);
        result = "ok";
      } catch (ArrayIndexOutOfBoundsException e) {
        result = "please specify a value";
      }
    } else if ("get".equals(cmd[0])) {
      if (cmd.length > 2) {
        return "too many key";
      }
      try {
        result = client.strs().get(cmd[1]);
      } catch (KeyNotFoundException e) {
        result = "the key:" + e.getKey() + " is not found";
      } catch (DstException e) {
        result = e.toString();
      }
    } else {
      result = "Unsupport operation";
    }

    return result;
  }

  /**
   * @param cmd [put k1 v1 v2 v3]、[get k1]
   * @return ok、[v1 v2 v3]
   */
  public String getListResult(String[] cmd) {
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
    } else if ("lpush".equals(cmd[0])) {
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
    } else if ("rpush".equals(cmd[0])) {
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
    } else if ("lpop".equals(cmd[0])) {
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
    } else if ("rpop".equals(cmd[0])) {
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

    return result;
  }

  public String getSetResult(String[] cmd) {
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

    return result;
  }

  public String getDictResult(String[] cmd) {
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

    return result;
  }

  //TODO(jyx)
  public static String getTableResult(String[] cmd) {
    return null;
  }
}
