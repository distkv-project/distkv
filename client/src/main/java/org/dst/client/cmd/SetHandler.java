package org.dst.client.cmd;

import java.util.Arrays;
import java.util.HashSet;
import org.dst.client.DefaultDstClient;
import org.dst.exception.DstException;

public class SetHandler extends Handler {

  private static final String PUT = "put";
  private static final String GET = "get";
  private static final String DEL = "del";
  private static final String DROP = "drop";
  private static final String EXISTS = "exists";

  public SetHandler(DefaultDstClient client) {
    super(client);
  }

  /**
   * @param cmd [put k1 v1 v2 v3 v3]、[get k1]
   * @return ok、[v1 v2 v3]
   */
  @Override
  public ClientResult getCmdResult(String[] cmd) {

    if (cmd == null) {
      return clientResult;
    }

    String result;

    switch (cmd[0]) {
      case PUT:
        try {
          //set.put k1 v1...
          if (cmd.length > 2) {
            String[] str = Arrays.copyOfRange(cmd, 2, cmd.length);
            client.sets().put(cmd[1], new HashSet<>(Arrays.asList(str)));
            result = "ok";
          } else { //set.put or set.put k1
            result = "please specify the right parameter";
          }
        } catch (DstException e) {
          result = e.getMessage();
        } catch (Exception e) {
          result = "not ok";
        }
        break;
      case GET:
        try {
          //set.get k1
          if (cmd.length == 2) {
            result = client.sets().get(cmd[1]).toString();
          } else { //set.get or set.get k1 k2...
            result = "please specify the right parameter";
          }
        } catch (DstException e) {
          result = e.getMessage();
        } catch (Exception e) {
          result = "not ok";
        }
        break;
      case DEL:
        try {
          //set.del k1 v1
          if (cmd.length == 3) {
            client.sets().delete(cmd[1], cmd[2]);
            result = "ok";
          } else { //set.del or set.del k1 or set.del k1 k2 k3..
            result = "please specify the right parameter";
          }
        } catch (DstException e) {
          result = e.getMessage();
        } catch (Exception e) {
          result = "not ok";
        }
        break;
      case DROP:
        try {
          //set.drop k1
          if (cmd.length == 2) {
            result = String.valueOf(client.sets().dropByKey(cmd[1]));
          } else { //set.drop or set.drop k1 k2...
            result = "please specify the right parameter";
          }
        } catch (DstException e) {
          result = e.getMessage();
        } catch (Exception e) {
          result = "not ok";
        }
        break;
      case EXISTS:
        try {
          //set.exists k1 v1
          if (cmd.length == 3) {
            result = String.valueOf(client.sets().exists(cmd[1], cmd[2]));
          } else { //set.exists or set.exists k1 or set.exists k1 k2 k3
            result = "please specify the right parameter";
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
