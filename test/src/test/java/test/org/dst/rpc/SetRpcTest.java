package test.org.dst.rpc;

import com.google.common.collect.ImmutableList;
import junit.framework.Assert;
import org.dst.protocol.SetProtocol;
import org.dst.protocol.StatusProtocol;
import org.dst.server.service.DstSetService;
import org.junit.jupiter.api.Test;
import java.util.List;

public class SetRpcTest {

    @Test
    public void testSetRpcCall() {
        // Run the server
        TestUtil.startRpcServer();
        try(ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
            DstSetService setService = setProxy.getService();
            // Test set put.
            SetProtocol.SetPutRequest.Builder setPutRequestBuilder =
                    SetProtocol.SetPutRequest.newBuilder();
            setPutRequestBuilder.setKey("k1");
            final List<String> values = ImmutableList.of("v1", "v2", "v3", "v1");
            values.forEach(value -> setPutRequestBuilder.addValues(value));

            SetProtocol.SetPutResponse setPutResponse =
                    setService.setPut(setPutRequestBuilder.build());
            Assert.assertEquals(StatusProtocol.Status.OK, setPutResponse.getStatus());

            // Test set get.
            SetProtocol.SetGetRequest.Builder setGetRequestBuilder =
                    SetProtocol.SetGetRequest.newBuilder();
            setGetRequestBuilder.setKey("k1");

            SetProtocol.SetGetResponse setGetResponse =
                    setService.setGet(setGetRequestBuilder.build());

            final List<String> results = ImmutableList.of("v1", "v2", "v3");
            Assert.assertEquals(StatusProtocol.Status.OK, setGetResponse.getStatus());
            Assert.assertEquals(results, setGetResponse.getValuesList());
        }
        // Stop the server
        TestUtil.stopRpcServer();
    }
}
