package org.dst.rpc.service;

import com.baidu.brpc.protocol.BrpcMeta;
import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.ListProtocol;

public interface DstListService {

  @BrpcMeta(serviceName = "DstListService", methodName = "put")
  ListProtocol.PutResponse put(ListProtocol.PutRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "get")
  ListProtocol.GetResponse get(ListProtocol.GetRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "drop")
  CommonProtocol.DropResponse drop(CommonProtocol.DropRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "lput")
  ListProtocol.LPutResponse lput(ListProtocol.LPutRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "rput")
  ListProtocol.RPutResponse rput(ListProtocol.RPutRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "delete")
  ListProtocol.DeleteResponse delete(ListProtocol.DeleteRequest request);

  @BrpcMeta(serviceName = "DstListService", methodName = "mdelete")
  ListProtocol.MDeleteResponse mdelete(ListProtocol.MDeleteRequest request);

}
