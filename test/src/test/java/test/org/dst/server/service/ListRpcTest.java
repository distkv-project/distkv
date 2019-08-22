package test.org.dst.server.service;

import com.google.common.collect.ImmutableList;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.ListProtocol;
import org.dst.server.service.DstListService;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.org.dst.supplier.BaseTestSupplier;
import test.org.dst.supplier.ListRpcWrapper;
import test.org.dst.supplier.ProxyOnClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListRpcTest extends BaseTestSupplier {

    private static List<String> listForRPCTest() {
        List<String> list = new ArrayList<>();
        list.add("v0");
        list.add("v1");
        list.add("v2");
        return list;
    }

    @Test
    public void testPutAndGet() {
        try(ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(DstListService.class)) {
            //put
            ListProtocol.PutRequest.Builder putRequest = ListRpcWrapper.putRequest();
            List<String> values = listForRPCTest();
            putRequest.setKey("k1");
            values.forEach(value -> putRequest.addValues(value));
            ListProtocol.PutResponse putResponse = ListRpcWrapper.putResponse(putRequest,proxy);
            Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

            //get
            ListProtocol.GetRequest.Builder getRequest = ListRpcWrapper.getRequest();
            getRequest.setKey("k1");
            ListProtocol.GetResponse getResponse = ListRpcWrapper.getResponse(getRequest,proxy);
            Assert.assertEquals(listForRPCTest(), getResponse.getValuesList());
        }
    }

    @Test
    public void testDel() {
        try(ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(DstListService.class)) {
            //put
            ListProtocol.PutRequest.Builder putRequest = ListRpcWrapper.putRequest();
            List<String> values = listForRPCTest();
            putRequest.setKey("k1");
            values.forEach(value -> putRequest.addValues(value));
            ListProtocol.PutResponse putResponse = ListRpcWrapper.putResponse(putRequest,proxy);
            Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

            //del
            ListProtocol.DelRequest.Builder delRequest = ListRpcWrapper.delRequest();
            delRequest.setKey("k1");
            ListProtocol.DelResponse delResponse = ListRpcWrapper.delResponse(delRequest, proxy);
            Assert.assertEquals(CommonProtocol.Status.OK, delResponse.getStatus());

            //KEY_NOT_FOUND
            delRequest.setKey("k2");
            ListProtocol.DelResponse delResponse2 = ListRpcWrapper.delResponse(delRequest, proxy);
            Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, delResponse2.getStatus());
        }
    }

    @Test
    public void testLPut() {
        try(ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(DstListService.class)) {
            //put
            ListProtocol.PutRequest.Builder putRequest = ListRpcWrapper.putRequest();
            List<String> values = listForRPCTest();
            putRequest.setKey("k1");
            values.forEach(value -> putRequest.addValues(value));
            ListProtocol.PutResponse putResponse = ListRpcWrapper.putResponse(putRequest, proxy);
            Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

            //lput
            ListProtocol.LPutRequest.Builder lputRequest = ListRpcWrapper.lputRequest();
            lputRequest.setKey("k1");
            List<String> valuesLput = new ArrayList<>();
            valuesLput.add("v3");
            valuesLput.add("v4");
            valuesLput.forEach(value -> lputRequest.addValues(value));
            ListProtocol.LPutResponse lputResponse = ListRpcWrapper.lputResponse(lputRequest, proxy);
            System.out.println(lputResponse.getStatus());
            Assert.assertEquals(CommonProtocol.Status.OK, lputResponse.getStatus());

            //get
            ListProtocol.GetRequest.Builder getRequest = ListRpcWrapper.getRequest();
            getRequest.setKey("k1");
            ListProtocol.GetResponse getResponse = ListRpcWrapper.getResponse(getRequest,proxy);
            Assert.assertEquals(Arrays.asList("v3","v4","v0","v1","v2"), getResponse.getValuesList());

            //KEY_NOT_FOUND
            lputRequest.setKey("k2");
            List<String> valuesLput2 = new ArrayList<>();
            valuesLput2.add("v3");
            valuesLput2.add("v4");
            valuesLput2.forEach(value -> lputRequest.addValues(value));
            ListProtocol.LPutResponse lputResponse2 = ListRpcWrapper.lputResponse(lputRequest, proxy);
            Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, lputResponse2.getStatus());
        }
    }

    @Test
    public void testRPut() {
        try(ProxyOnClient<DstListService> proxy = new ProxyOnClient<>(DstListService.class)) {
            //put
            ListProtocol.PutRequest.Builder putRequest = ListRpcWrapper.putRequest();
            List<String> values = listForRPCTest();
            putRequest.setKey("k1");
            values.forEach(value -> putRequest.addValues(value));
            ListProtocol.PutResponse putResponse = ListRpcWrapper.putResponse(putRequest, proxy);
            Assert.assertEquals(CommonProtocol.Status.OK, putResponse.getStatus());

            //lput
            ListProtocol.RPutRequest.Builder rputRequest = ListRpcWrapper.rputRequest();
            rputRequest.setKey("k1");
            List<String> valuesRput = new ArrayList<>();
            valuesRput.add("v3");
            valuesRput.add("v4");
            valuesRput.forEach(value -> rputRequest.addValues(value));
            ListProtocol.RPutResponse rputResponse = ListRpcWrapper.rputResponse(rputRequest, proxy);
            System.out.println(rputResponse.getStatus());
            Assert.assertEquals(CommonProtocol.Status.OK, rputResponse.getStatus());

            //get
            ListProtocol.GetRequest.Builder getRequest = ListRpcWrapper.getRequest();
            getRequest.setKey("k1");
            ListProtocol.GetResponse getResponse = ListRpcWrapper.getResponse(getRequest,proxy);
            Assert.assertEquals(Arrays.asList("v0","v1","v2","v3","v4"), getResponse.getValuesList());

            //KEY_NOT_FOUND
            rputRequest.setKey("k2");
            List<String> valuesRput2 = new ArrayList<>();
            valuesRput2.add("v3");
            valuesRput2.add("v4");
            valuesRput2.forEach(value -> rputRequest.addValues(value));
            ListProtocol.RPutResponse rputResponse2 = ListRpcWrapper.rputResponse(rputRequest, proxy);
            Assert.assertEquals(CommonProtocol.Status.KEY_NOT_FOUND, rputResponse2.getStatus());
        }
    }

    @Test
    public void testLDel() {

    }

    @Test
    public void testRDel() {

    }
}
