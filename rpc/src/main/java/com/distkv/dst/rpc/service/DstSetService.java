package com.distkv.dst.rpc.service;

import com.baidu.brpc.protocol.BrpcMeta;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;

public interface DstSetService {
  @BrpcMeta(serviceName = "DstSetService", methodName = "put")
  SetProtocol.PutResponse put(SetProtocol.PutRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "get")
  SetProtocol.GetResponse get(SetProtocol.GetRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "putItem")
  SetProtocol.PutItemResponse putItem(SetProtocol.PutItemRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "removeItem")
  SetProtocol.RemoveItemResponse removeItem(SetProtocol.RemoveItemRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "drop")
  CommonProtocol.DropResponse drop(CommonProtocol.DropRequest request);

  @BrpcMeta(serviceName = "DstSetService", methodName = "exists")
  SetProtocol.ExistsResponse exists(SetProtocol.ExistsRequest request);

}
