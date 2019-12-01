package com.distkv.dst.test.supplier;

import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.ListProtocol;
import com.distkv.dst.rpc.service.DstListService;
import java.util.concurrent.CompletableFuture;

public class ListRpcTestUtil {

  // All types of requests.
  public static ListProtocol.PutRequest.Builder putRequestBuilder() {
    return ListProtocol.PutRequest.newBuilder();
  }

  public static ListProtocol.GetRequest.Builder getRequestBuilder() {
    return ListProtocol.GetRequest.newBuilder();
  }

  public static CommonProtocol.DropRequest.Builder dropRequestBuilder() {
    return CommonProtocol.DropRequest.newBuilder();
  }

  public static ListProtocol.LPutRequest.Builder lputRequestBuilder() {
    return ListProtocol.LPutRequest.newBuilder();
  }

  public static ListProtocol.RPutRequest.Builder rputRequestBuilder() {
    return ListProtocol.RPutRequest.newBuilder();
  }

  public static ListProtocol.RemoveRequest.Builder removeRequestBuilder() {
    return ListProtocol.RemoveRequest.newBuilder();
  }

  public static ListProtocol.MRemoveRequest.Builder multipleRemoveRequestBuilder() {
    return ListProtocol.MRemoveRequest.newBuilder();
  }

  // All types of responses.
  // TODO(qwang): Remove this methods.
  public static CompletableFuture<ListProtocol.PutResponse> putResponseBuilder(
        ListProtocol.PutRequest.Builder builder, ProxyOnClient<DstListService> listProxy) {
    ListProtocol.PutRequest build = builder.build();
    return listProxy.getService().put(build);
  }

  public static CompletableFuture<ListProtocol.GetResponse> getResponseBuilder(
        ListProtocol.GetRequest.Builder builder, ProxyOnClient<DstListService> listProxy) {
    ListProtocol.GetRequest build = builder.build();
    return listProxy.getService().get(build);
  }

  public static CompletableFuture<ListProtocol.RPutResponse> rputResponseBuilder(
        ListProtocol.RPutRequest.Builder builder, ProxyOnClient<DstListService> listProxy) {
    ListProtocol.RPutRequest build = builder.build();
    return listProxy.getService().rput(build);
  }

  public static CompletableFuture<ListProtocol.RemoveResponse> removeResponseBuilder(
        ListProtocol.RemoveRequest.Builder builder, ProxyOnClient<DstListService> listProxy) {
    ListProtocol.RemoveRequest build = builder.build();
    return listProxy.getService().remove(build);
  }

  public static CompletableFuture<ListProtocol.MRemoveResponse> multipleRemoveResponseBuilder(
        ListProtocol.MRemoveRequest.Builder builder, ProxyOnClient<DstListService> listProxy) {
    ListProtocol.MRemoveRequest build = builder.build();
    return listProxy.getService().multipleRemove(build);
  }
}
