package com.distkv.pine.components.topper;

import com.distkv.client.DistkvClient;
import com.distkv.common.DistkvTuple;
import com.distkv.common.entity.sortedList.SlistEntity;
import com.distkv.pine.components.AbstractPineHandle;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PineTopperImpl  extends AbstractPineHandle implements PineTopper {

  private static final String COMPONENT_TYPE = "TOPPER";

  private DistkvClient distkvClient;

  public PineTopperImpl(DistkvClient distkvClient) {
    super();
    this.distkvClient = distkvClient;
    // Construct an empty sorted-list to the store.
    distkvClient.slists().put(getKey(), new LinkedList<>());
  }

  @Override
  public void addMember(String memberName, int memberScore) {
    distkvClient.slists().putMember(getKey(), new SlistEntity(memberName, memberScore));
  }

  public void removeMember(String memberName) {
    distkvClient.slists().removeMember(getKey(), memberName);
  }

  public List<DistkvTuple<String, Integer>> top(int num) {
    try {
      LinkedList<SlistEntity> result = distkvClient.slists().top(getKey(), num);
      // Covert the result type.
      List<DistkvTuple<String, Integer>> ret = new ArrayList<>(result.size());
      for (SlistEntity entity : result) {
        ret.add(new DistkvTuple<>(entity.getMember(), entity.getScore()));
      }
      return ret;
    } catch (InvalidProtocolBufferException e) {
      // TODO(qwang): Refine this exception.
      throw new RuntimeException(e);
    }
  }

  /**
   * Get the member of the given name.
   *
   * @param memberName The name of the member that will be find.
   */
  @Override
  public TopperMember getMember(String memberName) {
    try {
      DistkvTuple<Integer, Integer> result = distkvClient.slists().getMember(
          getKey(), memberName);
      return new TopperMember(memberName, result.getSecond(), result.getFirst());
    } catch (InvalidProtocolBufferException e) {
      // TODO(qwang): Refine this exception.
      throw new RuntimeException(e);
    }
  }

  @Override
  protected String getComponentType() {
    return COMPONENT_TYPE;
  }
}
