package org.dst.client.cmd;

import org.dst.client.DefaultDstClient;

public class UnkonwnHandler extends Handler {
  public UnkonwnHandler(DefaultDstClient client) {
    super(client);
  }

  @Override
  public ClientResult getCmdResult(String[] command) {
    clientResult.setResult("unsupported data type");
    return clientResult;
  }

  @Override
  public ClientResult apply(DstCommandWithType commandWithType) {
    return getCmdResult(commandWithType.command);
  }
}
