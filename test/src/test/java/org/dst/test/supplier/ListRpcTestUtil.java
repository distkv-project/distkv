package org.dst.test.supplier;


import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.ListProtocol;
import org.dst.rpc.service.DstListService;

public class ListRpcTestUtil {

  //all types of requests
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

  ////all types of response
  public static ListProtocol.PutResponse putResponseBuilder(
        ListProtocol.PutRequest.Builder builder, ProxyOnClient<DstListService> setProxy) {
    ListProtocol.PutRequest build = builder.build();
    return setProxy.getService().put(build);
  }

  public static ListProtocol.GetResponse getResponseBuilder(
        ListProtocol.GetRequest.Builder builder, ProxyOnClient<DstListService> setProxy) {
    ListProtocol.GetRequest build = builder.build();
    return setProxy.getService().get(build);
  }

  public static CommonProtocol.DropResponse dropResponseBuilder(
          CommonProtocol.DropRequest.Builder builder, ProxyOnClient<DstListService> setProxy) {
    CommonProtocol.DropRequest build = builder.build();
    return setProxy.getService().drop(build);
  }

  public static ListProtocol.LPutResponse lputResponseBuilder(
        ListProtocol.LPutRequest.Builder builder, ProxyOnClient<DstListService> setProxy) {
    ListProtocol.LPutRequest build = builder.build();
    return setProxy.getService().lput(build);
  }

  public static ListProtocol.RPutResponse rputResponseBuilder(
        ListProtocol.RPutRequest.Builder builder, ProxyOnClient<DstListService> setProxy) {
    ListProtocol.RPutRequest build = builder.build();
    return setProxy.getService().rput(build);
  }

  public static ListProtocol.RemoveResponse removeResponseBuilder(
        ListProtocol.RemoveRequest.Builder builder, ProxyOnClient<DstListService> setProxy) {
    ListProtocol.RemoveRequest build = builder.build();
    return setProxy.getService().remove(build);
  }

  public static ListProtocol.MRemoveResponse multipleRemoveResponseBuilder(
        ListProtocol.MRemoveRequest.Builder builder, ProxyOnClient<DstListService> setProxy) {
    ListProtocol.MRemoveRequest build = builder.build();
    return setProxy.getService().multipleRemove(build);
  }
}
