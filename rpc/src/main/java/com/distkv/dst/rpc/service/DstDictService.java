package com.distkv.dst.rpc.service;

import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;

public interface DstDictService {

  DictProtocol.PutResponse put(DictProtocol.PutRequest request);

  DictProtocol.GetResponse get(DictProtocol.GetRequest request);

  DictProtocol.GetItemResponse getItemValue(DictProtocol.GetItemRequest request);

  DictProtocol.PopItemResponse popItem(DictProtocol.PopItemRequest request);

  DictProtocol.PutItemResponse putItem(DictProtocol.PutItemRequest request);

  CommonProtocol.DropResponse drop(CommonProtocol.DropRequest request);

  DictProtocol.RemoveItemResponse removeItem(DictProtocol.RemoveItemRequest request);
}
