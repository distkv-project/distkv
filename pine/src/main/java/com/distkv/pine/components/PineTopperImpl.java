package com.distkv.pine.components;

import com.distkv.client.DistkvClient;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.google.protobuf.InvalidProtocolBufferException;
import javafx.util.Pair;
import java.util.LinkedList;
import java.util.List;

public class PineTopperImpl  extends PineHandle implements PineTopper {


  private static final String COMPONENT_TYPE = "TOPPER";

  private DistkvClient distkvClient;

  public PineTopperImpl(DistkvClient distkvClient) {
    this.distkvClient = distkvClient;
    // Construct an empty sorted-list to the store.
    distkvClient.sortedLists().put(getKey(), new LinkedList<>());
  }

  @Override
  public void addMember(String memberName, int memberScore) {
    distkvClient.sortedLists().putMember(getKey(), new SortedListEntity(memberName, memberScore));
  }

  public void removeMember(String memberName) {
    distkvClient.sortedLists().removeMember(getKey(), memberName);
  }

  public List<Pair<String, Integer>> top(int num) {
    try {
      LinkedList<SortedListEntity> result = distkvClient.sortedLists().top(getKey(), num);
      // Covert the result type.

    } catch (InvalidProtocolBufferException e) {
      // TODO(qwang): Refine this.
      throw new RuntimeException(e);
    }
  }

  @Override
  protected String getComponentType() {
    return COMPONENT_TYPE;
  }

}
