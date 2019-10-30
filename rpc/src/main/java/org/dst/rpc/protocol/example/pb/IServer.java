package org.dst.rpc.protocol.example.pb;

/**
 * @author zrj CreateDate: 2019/10/28
 */
public interface IServer {

  StringProtocol.GetResponse say(StringProtocol.GetRequest request);

}
