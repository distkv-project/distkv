package com.distkv.dst.rpc.service;

import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;

public interface DstSetService {
  SetProtocol.PutResponse put(SetProtocol.PutRequest request);

  SetProtocol.GetResponse get(SetProtocol.GetRequest request);

  SetProtocol.PutItemResponse putItem(SetProtocol.PutItemRequest request);

  SetProtocol.RemoveItemResponse removeItem(SetProtocol.RemoveItemRequest request);

  CommonProtocol.DropResponse drop(CommonProtocol.DropRequest request);

  SetProtocol.ExistsResponse exists(SetProtocol.ExistsRequest request);

}
