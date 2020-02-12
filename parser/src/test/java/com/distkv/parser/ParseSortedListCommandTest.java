package com.distkv.parser;

import com.distkv.common.RequestTypeEnum;
import com.distkv.common.exception.DistkvException;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseSortedListCommandTest {

  private static final DistkvParser dstParser = new DistkvParser();

  @Test
  public void testSlistPut() {
    final String command = "slist.put k1 m1 12 m2 -2 m3 0";
    DistkvParsedResult result = dstParser.parse(command);
    final SortedListProtocol.PutRequest putRequest =
            (SortedListProtocol.PutRequest) result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.SLIST_PUT);
    Assert.assertEquals(putRequest.getKey(), "k1");
    Assert.assertEquals(putRequest.getListCount(), 3);
    Assert.assertEquals(putRequest.getList(0).getScore(), 12);
    Assert.assertEquals(putRequest.getList(0).getMember(), "m1");
    Assert.assertEquals(putRequest.getList(1).getScore(), -2);
    Assert.assertEquals(putRequest.getList(1).getMember(), "m2");
    Assert.assertEquals(putRequest.getList(2).getScore(), 0);
    Assert.assertEquals(putRequest.getList(2).getMember(), "m3");
  }

  @Test
  public void testSlistTop() {
    final String command = "slist.top k1 2";
    DistkvParsedResult result = dstParser.parse(command);
    final SortedListProtocol.TopRequest topRequest =
            (SortedListProtocol.TopRequest) result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.SLIST_TOP);
    Assert.assertEquals(topRequest.getKey(), "k1");
    Assert.assertEquals(topRequest.getCount(), 2);
  }

  @Test
  public void testSlistIncrScoreDefault() {
    final String command = "slist.incrScore k1 m1";
    DistkvParsedResult result = dstParser.parse(command);
    final SortedListProtocol.IncrScoreRequest incrScoreRequest =
            (SortedListProtocol.IncrScoreRequest) result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.SLIST_INCR_SCORE);
    Assert.assertEquals(incrScoreRequest.getKey(), "k1");
    Assert.assertEquals(incrScoreRequest.getMember(), "m1");
    Assert.assertEquals(incrScoreRequest.getDelta(), 1);
  }

  @Test
  public void testSlistIncrScoreDelta() {
    final String command = "slist.incrScore k1 m1 20";
    DistkvParsedResult result = dstParser.parse(command);
    final SortedListProtocol.IncrScoreRequest incrScoreRequest =
            (SortedListProtocol.IncrScoreRequest) result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.SLIST_INCR_SCORE);
    Assert.assertEquals(incrScoreRequest.getKey(), "k1");
    Assert.assertEquals(incrScoreRequest.getMember(), "m1");
    Assert.assertEquals(incrScoreRequest.getDelta(), 20);
  }

  @Test
  public void testSlistPutMember() {
    final String command = "slist.putMember k1 m4 4";
    DistkvParsedResult result = dstParser.parse(command);
    final SortedListProtocol.PutMemberRequest putMemberRequest =
            (SortedListProtocol.PutMemberRequest) result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.SLIST_PUT_MEMBER);
    Assert.assertEquals(putMemberRequest.getKey(), "k1");
    Assert.assertEquals(putMemberRequest.getScore(), 4);
    Assert.assertEquals(putMemberRequest.getMember(), "m4");
  }

  @Test
  public void testSlistRemoveMember() {
    final String command = "slist.removeMember k1 m4";
    DistkvParsedResult result = dstParser.parse(command);
    final SortedListProtocol.RemoveMemberRequest removeMemberRequest =
            (SortedListProtocol.RemoveMemberRequest) result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.SLIST_REMOVE_MEMBER);
    Assert.assertEquals(removeMemberRequest.getKey(), "k1");
    Assert.assertEquals(removeMemberRequest.getMember(), "m4");
  }

  @Test
  public void testSlistDrop() {
    final String command = "slist.drop k1";
    DistkvParsedResult result = dstParser.parse(command);
    final CommonProtocol.DropRequest dropRequest =
            (CommonProtocol.DropRequest) result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.SLIST_DROP);
    Assert.assertEquals(dropRequest.getKey(), "k1");
  }

  @Test
  public void testSlistGetMember() {
    final String command = "slist.getMember k1 m1";
    DistkvParsedResult result = dstParser.parse(command);
    final SortedListProtocol.GetMemberRequest getMemberRequest =
            (SortedListProtocol.GetMemberRequest) result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestTypeEnum.SLIST_GET_MEMBER);
    Assert.assertEquals(getMemberRequest.getKey(), "k1");
    Assert.assertEquals(getMemberRequest.getMember(), "m1");
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidPutCommand() {
    final String command = "slist.put k1 m1 012";
    dstParser.parse(command);
  }

  @Test(expectedExceptions = DistkvException.class)
  public void testInvalidTopCommand() {
    final String command = "slist.top k1 0";
    dstParser.parse(command);
  }

}
