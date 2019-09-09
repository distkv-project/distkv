package org.dst.server.service;

import com.baidu.brpc.client.RpcCallback;
import org.dst.server.generated.StringProtocol;

import java.util.concurrent.Future;

public interface DstStringServiceAsync extends DstStringService {

  Future<StringProtocol.GetResponse> get(StringProtocol.GetRequest request,
                                         RpcCallback<StringProtocol.GetResponse> callback);
}
