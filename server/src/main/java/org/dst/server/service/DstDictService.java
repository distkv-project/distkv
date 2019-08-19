package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.protocol.DictProtocol;

public interface DstDictService {

  @BrpcMeta(serviceName = "DstDictService", methodName = "put")
  DictProtocol.DictPutResponse put(DictProtocol.DictPutRequest request);

  @BrpcMeta(serviceName = "DstDictService", methodName = "get")
  DictProtocol.DictGetResponse get(DictProtocol.DictGetRequest request);
}
