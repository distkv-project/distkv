package org.dst.rpc.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.DictProtocol;

public interface DstDictService {

  @BrpcMeta(serviceName = "DstDictService", methodName = "put")
  DictProtocol.PutResponse put(DictProtocol.PutRequest request);

  @BrpcMeta(serviceName = "DstDictService", methodName = "get")
  DictProtocol.GetResponse get(DictProtocol.GetRequest request);

  @BrpcMeta(serviceName = "DstDictService", methodName = "getItemValue")
  DictProtocol.GetItemValueResponse getItemValue(DictProtocol.GetItemValueRequest request);

  @BrpcMeta(serviceName = "DstDictService", methodName = "popItem")
  DictProtocol.PopItemResponse popItem(DictProtocol.PopItemRequest request);

  @BrpcMeta(serviceName = "DstDictService", methodName = "putItem")
  DictProtocol.PutItemResponse putItem(DictProtocol.PutItemRequest request);

  @BrpcMeta(serviceName = "DstDictService", methodName = "drop")
  CommonProtocol.DropResponse drop(CommonProtocol.DropRequest request);

  @BrpcMeta(serviceName = "DstDictService", methodName = "delItem")
  DictProtocol.DelItemResponse delItem(DictProtocol.DelItemRequest request);
}
