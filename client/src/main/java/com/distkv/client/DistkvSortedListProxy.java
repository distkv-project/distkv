package com.distkv.client;

import com.distkv.common.DistkvTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistGetMemberResponse;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistPutRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistTopResponse;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

public class DistkvSortedListProxy {

  private String typeCode = "E";

  public DistkvSortedListProxy(DistkvService service) {
    this.service = service;
  }

  public void put(String key, LinkedList<SortedListEntity> list) {
  }

  public void incrScore(String key, String member, int delta) {

  }

  public LinkedList<SortedListEntity> top(String key, int topNum)
      throws InvalidProtocolBufferException {

  }

  public void drop(String key) {

  }

  public void removeMember(String key, String member) {

  }

  public void putMember(String key, SortedListEntity entity) {

  }

  public DistkvTuple<Integer, Integer> getMember(String key, String member)
      throws InvalidProtocolBufferException {
  }
}
