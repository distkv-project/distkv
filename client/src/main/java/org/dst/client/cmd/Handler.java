package org.dst.client.cmd;

import java.util.function.Function;
import org.dst.client.DefaultDstClient;

public abstract class Handler implements Function<DstCommandWithType, ClientResult> {

  public DefaultDstClient client;

  public ClientResult clientResult = new ClientResult();

  public Handler(DefaultDstClient client) {
    this.client = client;
  }

  public abstract ClientResult getCmdResult(String[] command);
}
