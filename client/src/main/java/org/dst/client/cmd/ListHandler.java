package org.dst.client.cmd;

import java.util.Arrays;
import com.google.common.base.Preconditions;
import org.dst.client.DefaultDstClient;
import org.dst.exception.DstException;

public class ListHandler extends Handler {

  private static final String PUT_COMMAND_STR = "put";
  private static final String GET_COMMAND_STR = "get";
  private static final String LPUT_COMMAND_STR = "lput";
  private static final String RPUT_COMMAND_STR = "rput";
  private static final String LDEL_COMMAND_STR = "ldel";
  private static final String RDEL_COMMAND_STR = "rdel";

  public ListHandler(DefaultDstClient client) {
    super(client);
  }

  /**
   * @param cmd [put k1 v1 v2 v3]、[get k1]
   * @return ok、[v1 v2 v3]
   */
  @Override
  public ClientResult getCmdResult(String[] cmd) {

    Preconditions.checkArgument(cmd != null && cmd.length > 0, "command is empty");

    String result;

    switch (cmd[0]) {
      case PUT_COMMAND_STR:
        try {
          //list.put k1 v1 v1 v3...
          if (cmd.length > 2) {
            String[] str = Arrays.copyOfRange(cmd, 2, cmd.length);
            client.lists().put(cmd[1], Arrays.asList(str));
            result = "ok";
          } else {
            //list.put or list.put k1
            result = "please specify the right argument";
          }
        } catch (DstException e) {
          result = e.getMessage();
        } catch (Exception e) {
          result = "not ok";
        }
        break;
      case GET_COMMAND_STR:
        try {
          //list.get k1
          if (cmd.length == 1) {
            result = client.lists().get(cmd[1]).toString();
          } else {
            //list.get or list.get k1 k2...
            result = "please specify the right argument";
          }
        } catch (DstException e) {
          result = e.getMessage();
        } catch (Exception e) {
          result = "not ok";
        }
        break;
      case LPUT_COMMAND_STR:
        try {
          //list.lput k1 v1...
          if (cmd.length > 2) {
            String[] str = Arrays.copyOfRange(cmd, 2, cmd.length);
            client.lists().lput(cmd[1], Arrays.asList(str));
            result = "ok";
          } else {
            //list.lput or list.lput k1
            result = "please specify the right argument";
          }
        } catch (DstException e) {
          result = e.getMessage();
        } catch (Exception e) {
          result = "not ok";
        }
        break;
      case RPUT_COMMAND_STR:
        try {
          //list.rput k1 v1...
          if (cmd.length > 2) {
            String[] str = Arrays.copyOfRange(cmd, 2, cmd.length);
            client.lists().rput(cmd[1], Arrays.asList(str));
            result = "ok";
          } else {
            //list.rput or list.rput k1
            result = "please specify the right argument";
          }
        } catch (DstException e) {
          result = e.getMessage();
        } catch (Exception e) {
          result = "not ok";
        }
        break;
      case LDEL_COMMAND_STR:
        try {
          //list.ldel k1 3
          if (cmd.length == 3) {
            client.lists().ldel(cmd[1], Integer.parseInt(cmd[2]));
            result = "ok";
          } else {
            //list.ldel or or list.ldel k1 or list.ldel k1 1 2..
            result = "please specify the right argument";
          }
        } catch (DstException e) {
          result = e.getMessage();
        } catch (NumberFormatException e) {
          result = "please specify an integer";
        } catch (Exception e) {
          result = "not ok";
        }
        break;
      case RDEL_COMMAND_STR:
        try {
          //list.rdel k1 3
          if (cmd.length == 3) {
            client.lists().rdel(cmd[1], Integer.parseInt(cmd[2]));
            result = "ok";
          } else {
            //list.rdel or list.rdel k1 or list.rdel k1 1 2..
            result = "please specify the right argument";
          }
        } catch (DstException e) {
          result = e.getMessage();
        } catch (NumberFormatException e) {
          result = "please specify an integer";
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
