package org.dst.client.cmd;

import com.google.common.base.Preconditions;
import org.dst.client.DefaultDstClient;
import org.dst.exception.DstException;

public class StringHandler extends Handler {

  private static final String PUT_COMMAND_STR = "put";
  private static final String GET_COMMAND_STR = "get";

  public StringHandler(DefaultDstClient client) {
    super(client);
  }

  /**
   * @param cmd [put k1 v1] 、[get k1]
   * @return ok、v1
   */
  @Override
  public ClientResult getCmdResult(String[] cmd) {

    Preconditions.checkArgument(cmd != null && cmd.length > 0, "command is empty");

    String result;

    switch (cmd[0]) {
      case PUT_COMMAND_STR:
        try {
          //put k1 v1 or str.put k1 v1
          if (cmd.length == 3) {
            client.strs().put(cmd[1], cmd[2]);
            result = "ok";
          } else {
            // str.put or str.put k1 or str.put k1 v1 k2 v2...
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
          //get k1 or str.get k1
          if (cmd.length == 2) {
            result = client.strs().get(cmd[1]);
          } else {
            //str.get or str.get k1 k2...
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
    }
    clientResult.setResult(result);
    return clientResult;
  }

  @Override
  public ClientResult apply(DstCommandWithType commandWithType) {
    return getCmdResult(commandWithType.getCommand());
  }
}
