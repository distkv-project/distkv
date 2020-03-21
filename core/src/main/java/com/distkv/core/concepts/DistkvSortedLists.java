package com.distkv.core.concepts;

import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.common.exception.DistkvException;
import com.distkv.core.struct.slist.SortedList;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;

import java.util.List;

public interface DistkvSortedLists extends DistkvBaseOperation<SortedList> {

  void putMember(String key, Any request) throws DistkvException;

  /**
   * This method will put a item to a sortedList
   *
   * @param key  the key to store
   * @param item the item in sortedList
   */
  void putMember(String key, SortedListEntity item) throws DistkvException;

  /**
   * This method will del a item in sortedList
   *
   * @param key     the key to store
   * @param request the request.
   */
  void removeMember(String key, Any request) throws DistkvException;

  /**
   * increase the given itemEntity score in sortedList related to the key.
   *
   * @param key     the key related to given sortedList.
   * @param request the request.
   */
  void incrScore(String key, Any request) throws DistkvException;

  /**
   * This method will inc a itemEntity score in sortedList
   *
   * @param key    the key to store
   * @param member the itemEntity info in sortedList, Find the item by info
   * @param delta  amount of change in score
   */
  void incrScore(String key, String member, int delta) throws DistkvException;

  void top(String key, Any request, Builder builder) throws DistkvException;

  /**
   * This method will get a top list in map
   *
   * @param key    the key to store
   * @param topNum the size of topList
   */
  List<SortedListEntity> top(String key, int topNum) throws DistkvException;

  /**
   * Get the score and ranking values of the given key and member
   *
   * @param key     the key to store
   * @param request the request information.
   */
  void getMember(String key, Any request, Builder builder) throws DistkvException;

  void put(String key, List<SortedListEntity> value) throws DistkvException;
}
