package org.dst.client.cmd;

import org.dst.client.DefaultDstClient;
import org.dst.exception.DstException;
import org.dst.exception.KeyNotFoundException;

public class StringHandler extends Handler {

  public StringHandler(DefaultDstClient client) {
    super(client);
  }

  /**
   * @param cmd [put k1 v1] 、[get k1]
   * @return ok、v1
   */
  @Override
  public ClientResult getCmdResult(String[] cmd) {
    String result;

    if ("put".equals(cmd[0])) {
      if (cmd.length > 3) {
        result = "only need a key and a value";
      } else {
        try {
          client.strs().put(cmd[1], cmd[2]);
          result = "ok";
        } catch (ArrayIndexOutOfBoundsException e) {
          result = "please specify a value";
        }
      }
    } else if ("get".equals(cmd[0])) {
      if (cmd.length > 2) {
        result = "too many key";
      } else {
        try {
          result = client.strs().get(cmd[1]);
        } catch (KeyNotFoundException e) {
          result = "the key:" + e.getKey() + " is not found";
        } catch (DstException e) {
          result = e.toString();
        }
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
