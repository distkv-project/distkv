package com.distkv.server.service;

import com.distkv.common.entity.sortedList.SlistEntity;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.SlistProtocol;
import com.distkv.rpc.protobuf.generated.SlistProtocol.SlistGetMemberResponse;
import com.distkv.rpc.protobuf.generated.SlistProtocol.SlistTopResponse;
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
        new ProxyOnClient<>(DistkvService.class, KVSTORE_PORT)) {
      DistkvService service = sortedListProxy.getService();
      LinkedList<SlistEntity> list = new LinkedList<>();
      list.add(new SlistEntity("xswl", 9));
      list.add(new SlistEntity("wlll", 8));
      list.add(new SlistEntity("fw", 10));
      list.add(new SlistEntity("55", 6));

      SlistProtocol.SlistPutRequest.Builder requestBuilder =
          SlistProtocol.SlistPutRequest.newBuilder();
      LinkedList<SlistProtocol.SlistEntity> listEntities =
          new LinkedList<>();
      for (SlistEntity entity : list) {
        SlistProtocol.SlistEntity.Builder builder =
            SlistProtocol.SlistEntity.newBuilder();
        builder.setMember(entity.getMember());
        builder.setScore(entity.getScore());
        listEntities.add(builder.build());
      }
      requestBuilder.addAllList(listEntities);
      DistkvRequest putRequest = DistkvRequest.newBuilder()
          .setKey("slist_key")
          .setRequestType(RequestType.SLIST_PUT)
          .setRequest(Any.pack(requestBuilder.build()))
          .build();
      FutureUtils.get(
          service.call(putRequest));

      SlistProtocol.SlistTopRequest.Builder topRequestBuilder =
          SlistProtocol.SlistTopRequest.newBuilder();
      topRequestBuilder.setCount(2);
      DistkvRequest topRequest = DistkvRequest.newBuilder()
          .setKey("slist_key")
          .setRequestType(RequestType.SLIST_TOP)
          .setRequest(Any.pack(topRequestBuilder.build()))
          .build();
      DistkvResponse top = FutureUtils.get(
          service.call(topRequest));
      Assert.assertEquals(top.getResponse()
          .unpack(SlistTopResponse.class).getList(0).getMember(), "fw");
      Assert.assertEquals(top.getResponse()
          .unpack(SlistTopResponse.class).getList(1).getMember(), "xswl");

      SlistProtocol.SlistPutMemberRequest.Builder putRequestBuilder =
          SlistProtocol.SlistPutMemberRequest.newBuilder();
      putRequestBuilder.setMember("asd");
      putRequestBuilder.setScore(1000);
      DistkvRequest putMemberRequest = DistkvRequest.newBuilder()
          .setKey("slist_key")
          .setRequestType(RequestType.SLIST_PUT_MEMBER)
          .setRequest(Any.pack(putRequestBuilder.build()))
          .build();
      DistkvResponse response = FutureUtils.get(
          service.call(putMemberRequest));

      topRequestBuilder.setCount(2);
      DistkvResponse top1 = FutureUtils.get(
          service.call(topRequest));
      Assert.assertEquals(top1.getResponse()
          .unpack(SlistTopResponse.class).getList(0).getMember(), "asd");

      SlistProtocol.SlistGetMemberRequest.Builder getMemberRequestBuilder =
          SlistProtocol.SlistGetMemberRequest.newBuilder();
      getMemberRequestBuilder.setMember("asd");
      DistkvRequest getMemberRequest = DistkvRequest.newBuilder()
          .setKey("slist_key")
          .setRequestType(RequestType.SLIST_GET_MEMBER)
          .setRequest(Any.pack(getMemberRequestBuilder.build()))
          .build();
      DistkvResponse getMemberResponse = FutureUtils.get(
          service.call(getMemberRequest));
      SlistProtocol.SlistEntity sortedListEntity =
          getMemberResponse.getResponse().unpack(SlistGetMemberResponse.class).getEntity();
      Assert.assertEquals(sortedListEntity.getMember(), "asd");
      Assert.assertEquals(sortedListEntity.getScore(), 1000);
      Assert.assertEquals(getMemberResponse.getResponse()
          .unpack(SlistGetMemberResponse.class).getCount(), 1);
    }
  }
}
