package test.org.dst.rpc;

import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.StringProtocol;
import org.dst.server.service.DstStringService;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.org.dst.rpc.supplier.ProxyOnClient;
import test.org.dst.rpc.supplier.RPCTestSupplier;

public class StringRpcTest extends RPCTestSupplier {
    @Test
    public void testRpcServer() {
        try(ProxyOnClient<DstStringService> setProxy = new ProxyOnClient<>(DstStringService.class)) {
            DstStringService stringService = setProxy.getService();
            // Test string put request
            StringProtocol.PutRequest putRequest =
                    StringProtocol.PutRequest.newBuilder()
                            .setKey("k1")
                            .setValue("v1")
                            .build();

            StringProtocol.PutResponse putResponse = stringService.put(putRequest);
            Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());
            // Test string get request
            StringProtocol.GetRequest getRequest =
                    StringProtocol.GetRequest.newBuilder()
                            .setKey("k1")
                            .build();

            StringProtocol.GetResponse getResponse = stringService.get(getRequest);
            Assert.assertEquals("v1", getResponse.getValue());
        }
    }
}
