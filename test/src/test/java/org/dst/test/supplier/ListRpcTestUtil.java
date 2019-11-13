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

  public static CommonProtocol.DropRequest.Builder delRequestBuilder() {
    return CommonProtocol.DropRequest.newBuilder();
  }

  public static ListProtocol.LPutRequest.Builder lputRequestBuilder() {
    return ListProtocol.LPutRequest.newBuilder();
  }

  public static ListProtocol.RPutRequest.Builder rputRequestBuilder() {
    return ListProtocol.RPutRequest.newBuilder();
  }

  public static ListProtocol.LDelRequest.Builder ldelRequestBuilder() {
    return ListProtocol.LDelRequest.newBuilder();
  }

  public static ListProtocol.RDelRequest.Builder rdelRequestBuilder() {
    return ListProtocol.RDelRequest.newBuilder();
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

  public static CommonProtocol.DropResponse delResponseBuilder(
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

  public static ListProtocol.LDelResponse ldelResponseBuilder(
        ListProtocol.LDelRequest.Builder builder, ProxyOnClient<DstListService> setProxy) {
    ListProtocol.LDelRequest build = builder.build();
    return setProxy.getService().ldel(build);
  }

  public static ListProtocol.RDelResponse rdelResponseBuilder(
        ListProtocol.RDelRequest.Builder builder, ProxyOnClient<DstListService> setProxy) {
    ListProtocol.RDelRequest build = builder.build();
    return setProxy.getService().rdel(build);
  }
}
