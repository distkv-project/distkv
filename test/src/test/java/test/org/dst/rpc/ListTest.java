package test.org.dst.rpc;

import com.google.common.collect.ImmutableList;
import junit.framework.Assert;
import org.dst.server.generated.DstServerProtocol;
import org.dst.server.service.DstStringService;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ListTest {

    @Test
    public void testListRpcCall() {

        DstStringService listService = TestUtil.openConnection(DstStringService.class);
                // Test list put.
        DstServerProtocol.ListPutRequest.Builder putRequestBuilder =
                DstServerProtocol.ListPutRequest.newBuilder();
        putRequestBuilder.setKey("k1");
        final List<String> values = ImmutableList.of("v1", "v2", "v3");
        values.forEach(value -> putRequestBuilder.addValues(value));

        DstServerProtocol.ListPutResponse listPutResponse =
                listService.listPut(putRequestBuilder.build());
        Assert.assertEquals("ok", listPutResponse.getStatus());

        // Test list get.
        DstServerProtocol.ListGetRequest.Builder getRequestBuilder =
                DstServerProtocol.ListGetRequest.newBuilder();
        getRequestBuilder.setKey("k1");
        DstServerProtocol.ListGetResponse listGetResponse =
                listService.listGet(getRequestBuilder.build());

        Assert.assertEquals("ok", listGetResponse.getStatus());
        Assert.assertEquals(values, listGetResponse.getValuesList());

        TestUtil.closeConnection();
    }
}
