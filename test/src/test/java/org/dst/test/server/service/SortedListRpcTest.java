package org.dst.test.server.service;

import org.dst.common.protobuf.generated.SortedListProtocol;
import org.dst.entity.SortedListEntity;
import org.dst.server.service.DstSortedListService;
import org.dst.test.supplier.BaseTestSupplier;
import org.dst.test.supplier.ProxyOnClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedList;

public class SortedListRpcTest extends BaseTestSupplier {

  @Test
  public void testMain() {
    testPut();
  }

  public void testPut() {
    try (ProxyOnClient<DstSortedListService> sortedListProxy =
             new ProxyOnClient<>(DstSortedListService.class)) {
      DstSortedListService service = sortedListProxy.getService();
      LinkedList<SortedListEntity> list = new LinkedList<>();
      list.add(new SortedListEntity("xswl", 9));
      list.add(new SortedListEntity("wlll", 8));
      list.add(new SortedListEntity("fw", 9));
      list.add(new SortedListEntity("55", 6));

      SortedListProtocol.PutRequest.Builder requestBuilder =
          SortedListProtocol.PutRequest.newBuilder();
      requestBuilder.setKey("k1");
      LinkedList<SortedListProtocol.SortedListEntity> listEntities =
          new LinkedList<>();
      for (SortedListEntity entity : list) {
        SortedListProtocol.SortedListEntity.Builder builder =
            SortedListProtocol.SortedListEntity.newBuilder();
        builder.setInfo(entity.getInfo());
        builder.setScore(entity.getScore());
        listEntities.add(builder.build());
      }
      requestBuilder.addAllList(listEntities);
      SortedListProtocol.PutResponse put =
          service.put(requestBuilder.build());

      SortedListProtocol.TopRequest.Builder topRequestBuilder =
          SortedListProtocol.TopRequest.newBuilder();
      topRequestBuilder.setKey("k1");
      topRequestBuilder.setTopNum(2);
      SortedListProtocol.TopResponse top =
          service.top(topRequestBuilder.build());
      Assert.assertEquals(top.getList(0).getInfo(), "xswl");
      Assert.assertEquals(top.getList(1).getInfo(), "fw");

      SortedListProtocol.PutItemRequest.Builder putRequestBuilder =
          SortedListProtocol.PutItemRequest.newBuilder();
      putRequestBuilder.setKey("k1");
      putRequestBuilder.setInfo("asd");
      putRequestBuilder.setScore(1000);
      SortedListProtocol.PutItemResponse response =
          service.putItem(putRequestBuilder.build());

      topRequestBuilder.setKey("k1");
      topRequestBuilder.setTopNum(2);
      SortedListProtocol.TopResponse top1 =
          service.top(topRequestBuilder.build());
      Assert.assertEquals(top1.getList(0).getInfo(), "asd");
    }
  }
}
