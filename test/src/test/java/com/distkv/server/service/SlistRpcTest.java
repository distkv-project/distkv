package com.distkv.server.service;

import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistGetMemberResponse;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistTopResponse;
import com.distkv.rpc.service.DistkvService;
import com.distkv.supplier.BaseTestSupplier;
import com.distkv.supplier.ProxyOnClient;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedList;

public class SlistRpcTest extends BaseTestSupplier {

  @Test
  public void testPut() throws InvalidProtocolBufferException {
    try (ProxyOnClient<DistkvService> sortedListProxy =
        new ProxyOnClient<>(DistkvService.class, rpcServerPort.get())) {
      DistkvService service = sortedListProxy.getService();
      LinkedList<SortedListEntity> list = new LinkedList<>();
      list.add(new SortedListEntity("xswl", 9));
      list.add(new SortedListEntity("wlll", 8));
      list.add(new SortedListEntity("fw", 10));
      list.add(new SortedListEntity("55", 6));

      SortedListProtocol.SlistPutRequest.Builder requestBuilder =
          SortedListProtocol.SlistPutRequest.newBuilder();
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
      DistkvRequest putRequest = DistkvRequest.newBuilder()
          .setKey("k1")
          .setRequestType(RequestType.SORTED_LIST_PUT)
          .setRequest(Any.pack(requestBuilder.build()))
          .build();
      FutureUtils.get(
          service.call(putRequest));

      SortedListProtocol.SlistTopRequest.Builder topRequestBuilder =
          SortedListProtocol.SlistTopRequest.newBuilder();
      topRequestBuilder.setCount(2);
      DistkvRequest topRequest = DistkvRequest.newBuilder()
          .setKey("k1")
          .setRequestType(RequestType.SORTED_LIST_TOP)
          .setRequest(Any.pack(topRequestBuilder.build()))
          .build();
      DistkvResponse top = FutureUtils.get(
          service.call(topRequest));
      Assert.assertEquals(top.getResponse()
          .unpack(SlistTopResponse.class).getList(0).getMember(), "fw");
      Assert.assertEquals(top.getResponse()
          .unpack(SlistTopResponse.class).getList(1).getMember(), "xswl");

      SortedListProtocol.SlistPutMemberRequest.Builder putRequestBuilder =
          SortedListProtocol.SlistPutMemberRequest.newBuilder();
      putRequestBuilder.setMember("asd");
      putRequestBuilder.setScore(1000);
      DistkvRequest putMemberRequest = DistkvRequest.newBuilder()
          .setKey("k1")
          .setRequestType(RequestType.SORTED_LIST_PUT_MEMBER)
          .setRequest(Any.pack(putRequestBuilder.build()))
          .build();
      DistkvResponse response = FutureUtils.get(
          service.call(putMemberRequest));

      topRequestBuilder.setCount(2);
      DistkvResponse top1 = FutureUtils.get(
          service.call(topRequest));
      Assert.assertEquals(top1.getResponse()
          .unpack(SlistTopResponse.class).getList(0).getMember(), "asd");

      SortedListProtocol.SlistGetMemberRequest.Builder getMemberRequestBuilder =
          SortedListProtocol.SlistGetMemberRequest.newBuilder();
      getMemberRequestBuilder.setMember("asd");
      DistkvRequest getMemberRequest = DistkvRequest.newBuilder()
          .setKey("k1")
          .setRequestType(RequestType.SORTED_LIST_GET_MEMBER)
          .setRequest(Any.pack(getMemberRequestBuilder.build()))
          .build();
      DistkvResponse getMemberResponse = FutureUtils.get(
          service.call(getMemberRequest));
      SortedListProtocol.SortedListEntity sortedListEntity =
          getMemberResponse.getResponse().unpack(SlistGetMemberResponse.class).getEntity();
      Assert.assertEquals(sortedListEntity.getMember(), "asd");
      Assert.assertEquals(sortedListEntity.getScore(), 1000);
      Assert.assertEquals(getMemberResponse.getResponse()
          .unpack(SlistGetMemberResponse.class).getCount(), 1);
    }
  }
}
