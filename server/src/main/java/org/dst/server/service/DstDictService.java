package org.dst.server.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.server.generated.DictProtocol;

public interface DstDictService {

  @BrpcMeta(serviceName = "DstDictService", methodName = "put")
  DictProtocol.PutResponse put(DictProtocol.PutRequest request);

  @BrpcMeta(serviceName = "DstDictService", methodName = "get")
  DictProtocol.GetResponse get(DictProtocol.GetRequest request);

  @BrpcMeta(serviceName = "DstDictService", methodName = "getItemValue")
  DictProtocol.GetItemValueResponse getItemValue(DictProtocol.GetItemValueRequest request);

  @BrpcMeta(serviceName = "DstDictService", methodName = "popItem")
  DictProtocol.PopItemResponse popItem(DictProtocol.PopItemRequest request);

  @BrpcMeta(serviceName = "DstDictService", methodName = "setItem")
  DictProtocol.SetItemResponse setItem(DictProtocol.SetItemRequest request);

  @BrpcMeta(serviceName = "DstDictService", methodName = "del")
  DictProtocol.DelResponse del(DictProtocol.DelRequest request);

  @BrpcMeta(serviceName = "DstDictService", methodName = "delItem")
  DictProtocol.DelItemResponse delItem(DictProtocol.DelItemRequest request);
}
