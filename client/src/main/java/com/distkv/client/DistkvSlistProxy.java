package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncSlistProxy;
import com.distkv.common.DistkvTuple;
import com.distkv.common.entity.sortedList.SlistEntity;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.SlistProtocol;
import com.distkv.rpc.protobuf.generated.SlistProtocol.SlistGetMemberResponse;
import com.distkv.rpc.protobuf.generated.SlistProtocol.SlistTopResponse;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.LinkedList;


public class DistkvSlistProxy {

  private static final String typeCode = "E";

  private DistkvAsyncSlistProxy asyncSlistProxy;

  public DistkvSlistProxy(DistkvAsyncSlistProxy asyncSlistProxy) {
    this.asyncSlistProxy = asyncSlistProxy;
  }

  public void put(String key, LinkedList<SlistEntity> list) {
    DistkvResponse response = FutureUtils.get(asyncSlistProxy.put(key, list));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void incrScore(String key, String member, int delta) {

    DistkvResponse response = FutureUtils.get(asyncSlistProxy.incrScore(key, member, delta));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public LinkedList<SlistEntity> top(String key, int topNum)
      throws InvalidProtocolBufferException {
    DistkvResponse response = FutureUtils.get(asyncSlistProxy.top(key, topNum));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    LinkedList<SlistEntity> list = new LinkedList<>();
    SlistTopResponse slistTopResponse = response.getResponse().unpack(SlistTopResponse.class);
    for (SlistProtocol.SlistEntity entity : slistTopResponse.getListList()) {
      list.add(new SlistEntity(entity.getMember(), entity.getScore()));
    }
    return list;
  }

  public void removeMember(String key, String member) {
    DistkvResponse response = FutureUtils.get(asyncSlistProxy.removeMember(key, member));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void putMember(String key, SlistEntity entity) {
    DistkvResponse response = FutureUtils.get(asyncSlistProxy.putMember(key, entity));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public DistkvTuple<Integer, Integer> getMember(String key, String member)
      throws InvalidProtocolBufferException {
    DistkvResponse response = FutureUtils.get(asyncSlistProxy.getMember(key, member));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    SlistGetMemberResponse slistGetMemberResponse = response.getResponse()
        .unpack(SlistGetMemberResponse.class);
    SlistProtocol.SlistEntity sortedListEntity = slistGetMemberResponse.getEntity();
    return new DistkvTuple<>(sortedListEntity.getScore(), slistGetMemberResponse.getCount());
  }

}
