package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncSortedListProxy;
import com.distkv.common.DistkvTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistGetMemberResponse;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistTopResponse;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.LinkedList;


public class DistkvSortedListProxy {

  private static final String typeCode = "E";

  private DistkvAsyncSortedListProxy asyncSortedListProxy;

  public DistkvSortedListProxy(DistkvAsyncSortedListProxy asyncSortedListProxy) {
    this.asyncSortedListProxy = asyncSortedListProxy;
  }

  public void put(String key, LinkedList<SortedListEntity> list) {
    DistkvResponse response = FutureUtils.get(asyncSortedListProxy.put(key, list));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void incrScore(String key, String member, int delta) {

    DistkvResponse response = FutureUtils.get(asyncSortedListProxy.incrScore(key, member, delta));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public LinkedList<SortedListEntity> top(String key, int topNum)
      throws InvalidProtocolBufferException {
    DistkvResponse response = FutureUtils.get(asyncSortedListProxy.top(key, topNum));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    LinkedList<SortedListEntity> list = new LinkedList<>();
    SlistTopResponse slistTopResponse = response.getResponse().unpack(SlistTopResponse.class);
    for (SortedListProtocol.SortedListEntity entity : slistTopResponse.getListList()) {
      list.add(new SortedListEntity(entity.getMember(), entity.getScore()));
    }
    return list;
  }

  public void drop(String key) {
    DistkvResponse response = FutureUtils.get(asyncSortedListProxy.drop(key));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void removeMember(String key, String member) {
    DistkvResponse response = FutureUtils.get(asyncSortedListProxy.removeMember(key, member));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public void putMember(String key, SortedListEntity entity) {
    DistkvResponse response = FutureUtils.get(asyncSortedListProxy.putMember(key, entity));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public DistkvTuple<Integer, Integer> getMember(String key, String member)
      throws InvalidProtocolBufferException {
    DistkvResponse response = FutureUtils.get(asyncSortedListProxy.getMember(key, member));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    SlistGetMemberResponse slistGetMemberResponse = response.getResponse()
        .unpack(SlistGetMemberResponse.class);
    SortedListProtocol.SortedListEntity sortedListEntity = slistGetMemberResponse.getEntity();
    return new DistkvTuple<>(sortedListEntity.getScore(), slistGetMemberResponse.getCount());
  }


  /**
   * Expire a key.
   *
   * @param key The key to be expired.
   * @param expireTime Millisecond level to set expire.
   */
  public void expire(String key, long expireTime) {
    DistkvResponse response = FutureUtils.get(asyncSortedListProxy.expire(key, expireTime));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

}
