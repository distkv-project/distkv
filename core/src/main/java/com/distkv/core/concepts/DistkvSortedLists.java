package com.distkv.core.concepts;

import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;

public interface DistkvSortedLists<T> extends DistkvBaseOperation<T> {

  /**
   * This method will put a item to a sortedList
   *
   * @param key  the key to store
   * @param item the item in sortedList
   */
  void putMember(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

  void putMember(String key, SortedListEntity item);

  /**
   * This method will del a item in sortedList
   *
   * @param key    the key to store
   * @param member the itemEntity info in sortedList, Find the item by info
   */
  void removeMember(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

  /**
   * This method will inc a itemEntity score in sortedList
   *
   * @param key    the key to store
   * @param member the itemEntity info in sortedList, Find the item by info
   * @param delta  amount of change in score
   */
  void incrScore(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

  void incrScore(String key, String member, int delta);

  /**
   * This method will get a top list in map
   *
   * @param key    the key to store
   * @param topNum the size of topList
   */
  void top(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

  List<SortedListEntity> top(String key, int topNum);

  /**
   * Get the score and ranking values of the given key and member
   *
   * @param key    the key to store
   * @param member the itemEntity info in sortedList, Find the item by info
   * @return the DistKVTuple value which the first element is the score and
   * the second element is the ranking of the given key and member
   */
  void getMember(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException;

  void put(String key, List<SortedListEntity> value);
}
