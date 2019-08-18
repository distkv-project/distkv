package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.DictProtocol;

public interface DstDictService {

  @BrpcMeta(serviceName = "DstDictService", methodName = "dictPut")
  DictProtocol.DictPutResponse dictPut(DictProtocol.DictPutRequest request);

  @BrpcMeta(serviceName = "DstDictService", methodName = "dictGet")
  DictProtocol.DictGetResponse dictGet(DictProtocol.DictGetRequest request);
}
