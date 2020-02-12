package com.distkv.server.service;

import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.service.DistkvSortedListService;
import com.distkv.supplier.BaseTestSupplier;
import com.distkv.supplier.ProxyOnClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedList;

public class SortedListRpcTest extends BaseTestSupplier {

  @Test
  public void testPut() {
    try (ProxyOnClient<DistkvSortedListService> sortedListProxy =
             new ProxyOnClient<>(DistkvSortedListService.class, rpcServerPort)) {
      DistkvSortedListService service = sortedListProxy.getService();
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
      SortedListProtocol.PutResponse put = FutureUtils.get(
          service.put(requestBuilder.build()));

      SortedListProtocol.TopRequest.Builder topRequestBuilder =
          SortedListProtocol.TopRequest.newBuilder();
      topRequestBuilder.setKey("k1");
      topRequestBuilder.setCount(2);
      SortedListProtocol.TopResponse top = FutureUtils.get(
          service.top(topRequestBuilder.build()));
      Assert.assertEquals(top.getList(0).getMember(), "fw");
      Assert.assertEquals(top.getList(1).getMember(), "xswl");

      SortedListProtocol.PutMemberRequest.Builder putRequestBuilder =
          SortedListProtocol.PutMemberRequest.newBuilder();
      putRequestBuilder.setKey("k1");
      putRequestBuilder.setMember("asd");
      putRequestBuilder.setScore(1000);
      SortedListProtocol.PutMemberResponse response = FutureUtils.get(
          service.putMember(putRequestBuilder.build()));

      topRequestBuilder.setKey("k1");
      topRequestBuilder.setCount(2);
      SortedListProtocol.TopResponse top1 = FutureUtils.get(
          service.top(topRequestBuilder.build()));
      Assert.assertEquals(top1.getList(0).getMember(), "asd");

      SortedListProtocol.GetMemberRequest.Builder getMemberRequestBuilder =
              SortedListProtocol.GetMemberRequest.newBuilder();
      getMemberRequestBuilder.setKey("k1");
      getMemberRequestBuilder.setMember("asd");
      SortedListProtocol.GetMemberResponse getMemberResponse = FutureUtils.get(
              service.getMember(getMemberRequestBuilder.build()));
      SortedListProtocol.SortedListEntity sortedListEntity =
              getMemberResponse.getEntity();
      Assert.assertEquals(sortedListEntity.getMember(), "asd");
      Assert.assertEquals(sortedListEntity.getScore(), 1000);
      Assert.assertEquals(getMemberResponse.getCount(), 1);
    }
  }
}
