package org.dst.client.cmd;

import java.util.HashMap;
import java.util.Map;
import com.google.common.base.Preconditions;
import org.dst.client.DefaultDstClient;
import org.dst.exception.DstException;

public class DictHandler extends Handler {

  private static final String PUT_COMMAND_STR = "put";
  private static final String GET_COMMAND_STR = "get";
  private static final String POP_COMMAND_STR = "pop";
  private static final String DEL_COMMAND_STR = "del";
  private static final String DROP_COMMAND_STR = "drop";

  public DictHandler(DefaultDstClient client) {
    super(client);
  }

  /**
   * @param cmd [put dict1 k1 v1 k2 v2 k3 v3]、[get dict1]
   * @return ok、{k1=v1,k2=v2,k3=v3}
   */
  @Override
  public ClientResult getCmdResult(String[] cmd) {

    Preconditions.checkArgument(cmd != null && cmd.length > 0, "command is empty");

    String result;

    switch (cmd[0]) {
      case PUT_COMMAND_STR:
        try {
          //dict.put dict1 k1 v1 k2 v2
          Map<String, String> map = new HashMap<>();
          for (int i = 2; i < cmd.length; i = i + 2) {
            map.put(cmd[i], cmd[i + 1]);
          }
          client.dicts().put(cmd[1], map);
          result = "ok";
        } catch (ArrayIndexOutOfBoundsException e) {
          result = "please specify the right key-value pair";
        } catch (DstException e) {
          result = e.getMessage();
        } catch (Exception e) {
          result = "not ok";
        }
        break;
      case GET_COMMAND_STR:
        try {
          //dict.get dict1 k1
          if (cmd.length == 3) {
            result = client.dicts().getItemValue(cmd[1], cmd[2]);
          } else if (cmd.length == 2) {
            //dict.get dict1
            result = client.dicts().get(cmd[1]).toString();
          } else {
            //dict.get or dict.get dict1 k1 k2 k3...
            result = "please specify the right argument";
          }
        } catch (DstException e) {
          result = e.getMessage();
        } catch (Exception e) {
          result = "not ok";
        }
        break;
      case POP_COMMAND_STR:
        try {
          //dict.pop dict1 k1
          if (cmd.length == 3) {
            result = client.dicts().popItem(cmd[1], cmd[2]);
          } else {
            //dict.pop dict1 or dict.pop or dict.pop dict1 k1 k2 k3...
            result = "please specify the right argument";
          }
        } catch (DstException e) {
          result = e.getMessage();
        } catch (Exception e) {
          result = "not ok";
        }
        break;
      case DEL_COMMAND_STR:
        try {
          //dict.del dict1 k1
          if (cmd.length == 3) {
            client.dicts().delItem(cmd[1], cmd[2]);
            result = "ok";
          } else {
            //dict.del or dict.del dict1 or dict.del dict1 k1 k2 k3...
            result = "please specify the right argument";
          }
        } catch (DstException e) {
          result = e.getMessage();
        } catch (Exception e) {
          result = "not ok";
        }
        break;
      case DROP_COMMAND_STR:
        try {
          //dict.drop dict1
          if (cmd.length == 1) {
            client.dicts().del(cmd[1]);
            result = "ok";
          } else {
            //dict.drop or dict.drop dict1 k1 k2...
            result = "please specify the right argument";
          }
        } catch (DstException e) {
          result = e.getMessage();
        } catch (Exception e) {
          result = "not ok";
        }
        break;
      default:
        result = "unsupported operation";
        break;
    }
    clientResult.setResult(result);
    return clientResult;
  }

  @Override
  public ClientResult apply(DstCommandWithType commandWithType) {
    return getCmdResult(commandWithType.getCommand());
  }
}
