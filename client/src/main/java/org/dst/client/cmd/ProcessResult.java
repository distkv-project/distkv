package org.dst.client.cmd;

import org.dst.client.DefaultDstClient;
import org.dst.exception.*;

public class ProcessResult {

  public static DefaultDstClient client = null;

  public static String getStringResult(String[] cmd) {
    String result = "UNKNOWN";

    if ("put".equals(cmd[0])) {
      try {
        client.strs().put(cmd[1], cmd[2]);
        result = "OK";
      } catch (ArrayIndexOutOfBoundsException e) {
        result = "NOT OK";
      }
    } else if ("get".equals(cmd[0])) {
      try {
        result = client.strs().get(cmd[1]);
      } catch (KeyNotFoundException e) {
        result = "the key :"+e.getKey()+"is not found";
      }
    }

    return result;
  }
}
