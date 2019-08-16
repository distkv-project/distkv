package test.org.dst.rpc;

import org.dst.server.generated.DstServerProtocol;
import org.dst.server.service.DstStringService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServerTest {
    @Test
    public void testRpcServer() {

        ProxyOnClient<DstStringService> setProxy = new ProxyOnClient(DstStringService.class);
        DstStringService stringService = setProxy.openConnection();

        // Test string put request
        DstServerProtocol.StringPutRequest stringPutRequest =
                DstServerProtocol.StringPutRequest.newBuilder()
                        .setKey("k1")
                        .setValue("v1")
                        .build();

        DstServerProtocol.StringPutResponse stringResponse = stringService.strPut(stringPutRequest);
        Assertions.assertEquals("ok", stringResponse.getStatus());

        // Test string get request
        DstServerProtocol.StringGetRequest strGetRequest =
                DstServerProtocol.StringGetRequest.newBuilder()
                        .setKey("k1")
                        .build();

        DstServerProtocol.StringGetResponse stringGetRequest = stringService.strGet(strGetRequest);
        Assertions.assertEquals("v1", stringGetRequest.getValue());

        setProxy.closeConnection();
    }
}
