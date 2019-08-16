package test.org.dst.rpc;

import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.baidu.brpc.client.RpcClientOptions;
import com.baidu.brpc.protocol.Options;

public class ProxyOnClient<T> {
    private RpcClient client;
    private Class<T> tClass;

    public ProxyOnClient(Class<T> tClass){
        this.tClass = tClass;
    }

    public T openConnection(){
        TestUtil.startRpcServer();
        RpcClientOptions options = new RpcClientOptions();
        options.setProtocolType(Options.ProtocolType.PROTOCOL_BAIDU_STD_VALUE);
        options.setWriteTimeoutMillis(1000);
        options.setReadTimeoutMillis(1000);
        options.setMaxTotalConnections(1000);
        options.setMinIdleConnections(10);
        final String url = "list://127.0.0.1:8082";
        client = new RpcClient(url, options);
        return BrpcProxy.getProxy(client, tClass);
    }

    public void closeConnection(){
        client.stop();
        TestUtil.stopRpcServer();
    }
}
