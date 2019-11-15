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

  public static ListProtocol.DeleteRequest.Builder ldelRequestBuilder() {
    return ListProtocol.DeleteRequest.newBuilder();
  }

  public static ListProtocol.MDeleteRequest.Builder rdelRequestBuilder() {
    return ListProtocol.MDeleteRequest.newBuilder();
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

  public static ListProtocol.DeleteResponse ldelResponseBuilder(
        ListProtocol.DeleteRequest.Builder builder, ProxyOnClient<DstListService> setProxy) {
    ListProtocol.DeleteRequest build = builder.build();
    return setProxy.getService().delete(build);
  }

  public static ListProtocol.MDeleteResponse rdelResponseBuilder(
        ListProtocol.MDeleteRequest.Builder builder, ProxyOnClient<DstListService> setProxy) {
    ListProtocol.MDeleteRequest build = builder.build();
    return setProxy.getService().mdelete(build);
  }
}
