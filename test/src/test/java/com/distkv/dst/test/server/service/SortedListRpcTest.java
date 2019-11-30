package com.distkv.dst.test.server.service;

import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import com.distkv.dst.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.dst.rpc.service.DstSortedListService;
import com.distkv.dst.test.supplier.BaseTestSupplier;
import com.distkv.dst.test.supplier.ProxyOnClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedList;

public class SortedListRpcTest extends BaseTestSupplier {

  @Test
  public void testPut() {
    try (ProxyOnClient<DstSortedListService> sortedListProxy =
             new ProxyOnClient<>(DstSortedListService.class, rpcServerPort)) {
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
        builder.setMember(entity.getMember());
        builder.setScore(entity.getScore());
        listEntities.add(builder.build());
      }
      requestBuilder.addAllList(listEntities);
      SortedListProtocol.PutResponse put =
          service.put(requestBuilder.build());

      SortedListProtocol.TopRequest.Builder topRequestBuilder =
          SortedListProtocol.TopRequest.newBuilder();
      topRequestBuilder.setKey("k1");
      topRequestBuilder.setCount(2);
      SortedListProtocol.TopResponse top =
          service.top(topRequestBuilder.build());
      Assert.assertEquals(top.getList(0).getMember(), "xswl");
      Assert.assertEquals(top.getList(1).getMember(), "fw");

      SortedListProtocol.PutMemberRequest.Builder putRequestBuilder =
          SortedListProtocol.PutMemberRequest.newBuilder();
      putRequestBuilder.setKey("k1");
      putRequestBuilder.setMember("asd");
      putRequestBuilder.setScore(1000);
      SortedListProtocol.PutMemberResponse response =
          service.putItem(putRequestBuilder.build());

      topRequestBuilder.setKey("k1");
      topRequestBuilder.setCount(2);
      SortedListProtocol.TopResponse top1 =
          service.top(topRequestBuilder.build());
      Assert.assertEquals(top1.getList(0).getMember(), "asd");
    }
  }
}
