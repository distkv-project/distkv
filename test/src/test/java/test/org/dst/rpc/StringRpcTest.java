package test.org.dst.rpc;

import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.StringProtocol;
import org.dst.server.service.DstStringService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringRpcTest {
    @Test
    public void testRpcServer() {
        // Run the server
        TestUtil.startRpcServer();
        try(ProxyOnClient<DstStringService> setProxy = new ProxyOnClient<>(DstStringService.class)) {
            DstStringService stringService = setProxy.getService();
            // Test string put request
            StringProtocol.StringPutRequest stringPutRequest =
                    StringProtocol.StringPutRequest.newBuilder()
                            .setKey("k1")
                            .setValue("v1")
                            .build();

            StringProtocol.StringPutResponse stringResponse = stringService.strPut(stringPutRequest);
            Assertions.assertEquals(CommonProtocol.Status.OK, stringResponse.getStatus());
            // Test string get request
            StringProtocol.StringGetRequest strGetRequest =
                    StringProtocol.StringGetRequest.newBuilder()
                            .setKey("k1")
                            .build();

            StringProtocol.StringGetResponse stringGetRequest = stringService.strGet(strGetRequest);
            Assertions.assertEquals("v1", stringGetRequest.getValue());
        }
        // Stop the server
        TestUtil.stopRpcServer();
    }
}
