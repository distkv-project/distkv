package org.dst.client.cmd;

import org.dst.client.DefaultDstClient;

public class TableHandler extends Handler {

  public TableHandler(DefaultDstClient client) {
    super(client);
  }

  //TODO(jyx)
  @Override
  public ClientResult getCmdResult(String[] cmd) {
    return null;
  }

  @Override
  public ClientResult apply(DstCommandWithType commandWithType) {
    return getCmdResult(commandWithType.getCommand());
  }
}
