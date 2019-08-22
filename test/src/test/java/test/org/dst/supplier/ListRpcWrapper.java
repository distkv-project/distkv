package test.org.dst.supplier;

import org.dst.server.generated.ListProtocol;
import org.dst.server.service.DstListService;

public class ListRpcWrapper {

    //all types of requests
    public static ListProtocol.PutRequest.Builder putRequest(){
        return  ListProtocol.PutRequest.newBuilder();
    }

    public static ListProtocol.GetRequest.Builder getRequest(){
        return  ListProtocol.GetRequest.newBuilder();
    }

    public static ListProtocol.DelRequest.Builder delRequest(){
        return  ListProtocol.DelRequest.newBuilder();
    }

    public static ListProtocol.LPutRequest.Builder lputRequest(){
        return  ListProtocol.LPutRequest.newBuilder();
    }

    public static ListProtocol.RPutRequest.Builder rputRequest(){
        return  ListProtocol.RPutRequest.newBuilder();
    }

    public static ListProtocol.LDelRequest.Builder ldelRequest(){
        return  ListProtocol.LDelRequest.newBuilder();
    }

    public static ListProtocol.RDelRequest.Builder rdelRequest(){
        return  ListProtocol.RDelRequest.newBuilder();
    }

    ////all types of response
    public static ListProtocol.PutResponse putResponse(ListProtocol.PutRequest.Builder builder,ProxyOnClient<DstListService> setProxy){
        ListProtocol.PutRequest build = builder.build();
        return setProxy.getService().put(build);
    }

    public static ListProtocol.GetResponse getResponse(ListProtocol.GetRequest.Builder builder,ProxyOnClient<DstListService> setProxy){
            ListProtocol.GetRequest build = builder.build();
            return setProxy.getService().get(build);
    }

    public static ListProtocol.DelResponse delResponse(ListProtocol.DelRequest.Builder builder,ProxyOnClient<DstListService> setProxy){
            ListProtocol.DelRequest build = builder.build();
            return setProxy.getService().del(build);
    }

    public static ListProtocol.LPutResponse lputResponse(ListProtocol.LPutRequest.Builder builder,ProxyOnClient<DstListService> setProxy){
            ListProtocol.LPutRequest build = builder.build();
            return setProxy.getService().lput(build);
    }

    public static ListProtocol.RPutResponse rputResponse(ListProtocol.RPutRequest.Builder builder,ProxyOnClient<DstListService> setProxy){
            ListProtocol.RPutRequest build = builder.build();
            return setProxy.getService().rput(build);
    }

    public static ListProtocol.LDelResponse ldelResponse(ListProtocol.LDelRequest.Builder builder,ProxyOnClient<DstListService> setProxy){
            ListProtocol.LDelRequest build = builder.build();
            return setProxy.getService().ldel(build);
    }

    public static ListProtocol.RDelResponse rdelResponse(ListProtocol.RDelRequest.Builder builder,ProxyOnClient<DstListService> setProxy){
            ListProtocol.RDelRequest build = builder.build();
            return setProxy.getService().rdel(build);
    }
}
