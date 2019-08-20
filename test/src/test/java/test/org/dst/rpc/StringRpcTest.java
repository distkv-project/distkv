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
        try(ProxyOnClient<DstStringService> stringProxy = new ProxyOnClient<>(DstStringService.class)) {
            DstStringService stringService = stringProxy.getService();
            // Test string put request
            StringProtocol.PutRequest putRequest =
                    StringProtocol.PutRequest.newBuilder()
                            .setKey("k1")
                            .setValue("v1")
                            .build();

            StringProtocol.PutResponse putResponse = stringService.put(putRequest);
            Assertions.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());
            // Test string get request
            StringProtocol.GetRequest getRequest =
                    StringProtocol.GetRequest.newBuilder()
                            .setKey("k1")
                            .build();

            StringProtocol.GetResponse getResponse = stringService.get(getRequest);
            Assertions.assertEquals("v1", getResponse.getValue());
        }
        // Stop the server
        TestUtil.stopRpcServer();
    }
}
