package org.dst.rpc.protocol.example.pb;

import org.dst.rpc.transport.api.async.Response;

/**
 * @author zrj CreateDate: 2019/10/28
 */
public interface IServer {

  StringProtocol.GetResponse say(StringProtocol.GetRequest request);

  Response asyncSay(StringProtocol.GetRequest request);

}
