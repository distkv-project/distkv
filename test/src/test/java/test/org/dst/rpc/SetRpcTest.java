package test.org.dst.rpc;

import com.google.common.collect.ImmutableList;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.SetProtocol;
import org.dst.server.service.DstSetService;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.org.dst.rpc.supplier.ProxyOnClient;
import test.org.dst.rpc.supplier.RPCTestSupplier;
import java.util.List;

public class SetRpcTest  extends RPCTestSupplier {

    @Test
    public void testSetRpcCall() {
        try(ProxyOnClient<DstSetService> setProxy = new ProxyOnClient<>(DstSetService.class)) {
            DstSetService setService = setProxy.getService();
            // Test set put.
            SetProtocol.PutRequest.Builder setPutRequestBuilder =
                    SetProtocol.PutRequest.newBuilder();
            setPutRequestBuilder.setKey("k1");
            final List<String> values = ImmutableList.of("v1", "v2", "v3", "v1");
            values.forEach(value -> setPutRequestBuilder.addValues(value));

            SetProtocol.PutResponse setPutResponse =
                    setService.put(setPutRequestBuilder.build());
            Assert.assertEquals(CommonProtocol.Status.OK, setPutResponse.getStatus());

            // Test set get.
            SetProtocol.GetRequest.Builder setGetRequestBuilder =
                    SetProtocol.GetRequest.newBuilder();
            setGetRequestBuilder.setKey("k1");

            SetProtocol.GetResponse setGetResponse =
                    setService.get(setGetRequestBuilder.build());

            final List<String> results = ImmutableList.of("v1", "v2", "v3");
            Assert.assertEquals(CommonProtocol.Status.OK, setGetResponse.getStatus());
            Assert.assertEquals(results, setGetResponse.getValuesList());
        }
    }
}
