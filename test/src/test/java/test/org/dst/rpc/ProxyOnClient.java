package test.org.dst.rpc;

import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.baidu.brpc.client.RpcClientOptions;
import com.baidu.brpc.protocol.Options;

public class ProxyOnClient<T> {

    /**
     * The url of the rpc server address.
     */
    private final static String SERVER_URL = "list://127.0.0.1:8082";

    /**
     * The client that connects to the rpc server.
     */
    private RpcClient client;

    /**
     * The type of the service proxy.
     */
    private Class<T> proxyClass;

    public ProxyOnClient(Class<T> proxyClass){
        this.proxyClass = proxyClass;
    }

    public T openConnection(){
        RpcClientOptions options = new RpcClientOptions();
        options.setProtocolType(Options.ProtocolType.PROTOCOL_BAIDU_STD_VALUE);
        options.setWriteTimeoutMillis(1000);
        options.setReadTimeoutMillis(1000);
        options.setMaxTotalConnections(1000);
        options.setMinIdleConnections(10);
        client = new RpcClient(SERVER_URL, options);
        return BrpcProxy.getProxy(client, proxyClass);
    }

    public void closeConnection(){
        client.stop();
        TestUtil.stopRpcServer();
    }
}
