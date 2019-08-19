package test.org.dst.rpc;

import com.google.common.collect.ImmutableList;
import junit.framework.Assert;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.ListProtocol;
import org.dst.server.service.DstListService;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ListRpcTest {

    @Test
    public void testListRpcCall() {
        // Run the server
        TestUtil.startRpcServer();
        try(ProxyOnClient<DstListService> listProxy = new ProxyOnClient<>(DstListService.class)) {
            DstListService listService = listProxy.getService();
            // Test list put.
            ListProtocol.ListPutRequest.Builder putRequestBuilder =
                    ListProtocol.ListPutRequest.newBuilder();
            putRequestBuilder.setKey("k1");
            final List<String> values = ImmutableList.of("v1", "v2", "v3");
            values.forEach(value -> putRequestBuilder.addValues(value));

            ListProtocol.ListPutResponse listPutResponse =
                    listService.listPut(putRequestBuilder.build());
            Assert.assertEquals(CommonProtocol.Status.OK, listPutResponse.getStatus());

            // Test list get.
            ListProtocol.ListGetRequest.Builder getRequestBuilder =
                    ListProtocol.ListGetRequest.newBuilder();
            getRequestBuilder.setKey("k1");
            ListProtocol.ListGetResponse listGetResponse =
                    listService.listGet(getRequestBuilder.build());

            Assert.assertEquals(CommonProtocol.Status.OK, listGetResponse.getStatus());
            Assert.assertEquals(values, listGetResponse.getValuesList());
        }
        // Stop the server
        TestUtil.stopRpcServer();
    }
}
