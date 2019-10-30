package org.dst.rpc.protocol.example.sync;

/**
 * @author zrj CreateDate: 2019/10/28
 */
public class IServerImpl implements IServer {

  @Override
  public String say() {
    return "dst";
  }

  @Override
  public String say(String name) {
    return "dst " + name;
  }
}
