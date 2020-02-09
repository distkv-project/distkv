package com.distkv.parser;

import com.distkv.common.exception.DistkvException;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistGetMemberRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistIncrScoreRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistPutMemberRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistPutRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistRemoveMemberRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistTopRequest;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParseSortedListCommandTest {

  private static final DistkvParser dstParser = new DistkvParser();

  @Test
  public void testSlistPut() throws InvalidProtocolBufferException {
    final String command = "slist.put k1 m1 12 m2 -2 m3 0";
    DistkvParsedResult result = dstParser.parse(command);
    final DistkvRequest putRequest = result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestType.SORTED_LIST_PUT);
    Assert.assertEquals(putRequest.getKey(), "k1");
    Assert.assertEquals(putRequest.getRequest()
        .unpack(SlistPutRequest.class).getListCount(), 3);
    Assert.assertEquals(putRequest.getRequest()
        .unpack(SlistPutRequest.class).getList(0).getScore(), 12);
    Assert.assertEquals(putRequest.getRequest()
        .unpack(SlistPutRequest.class).getList(0).getMember(), "m1");
    Assert.assertEquals(putRequest.getRequest()
        .unpack(SlistPutRequest.class).getList(1).getScore(), -2);
    Assert.assertEquals(putRequest.getRequest()
        .unpack(SlistPutRequest.class).getList(1).getMember(), "m2");
    Assert.assertEquals(putRequest.getRequest()
        .unpack(SlistPutRequest.class).getList(2).getScore(), 0);
    Assert.assertEquals(putRequest.getRequest()
        .unpack(SlistPutRequest.class).getList(2).getMember(), "m3");
  }

  @Test
  public void testSlistTop() throws InvalidProtocolBufferException {
    final String command = "slist.top k1 2";
    DistkvParsedResult result = dstParser.parse(command);
    final DistkvRequest topRequest = result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestType.SORTED_LIST_TOP);
    Assert.assertEquals(topRequest.getKey(), "k1");
    Assert.assertEquals(topRequest.getRequest()
        .unpack(SlistTopRequest.class).getCount(), 2);
  }

  @Test
  public void testSlistIncrScoreDefault() throws InvalidProtocolBufferException {
    final String command = "slist.incrScore k1 m1";
    DistkvParsedResult result = dstParser.parse(command);
    final DistkvRequest incrScoreRequest = result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestType.SORTED_LIST_INCR_SCORE);
    Assert.assertEquals(incrScoreRequest.getKey(), "k1");
    Assert.assertEquals(incrScoreRequest.getRequest()
        .unpack(SlistIncrScoreRequest.class).getMember(), "m1");
    Assert.assertEquals(incrScoreRequest.getRequest()
        .unpack(SlistIncrScoreRequest.class).getDelta(), 1);
  }

  @Test
  public void testSlistIncrScoreDelta() throws InvalidProtocolBufferException {
    final String command = "slist.incrScore k1 m1 20";
    DistkvParsedResult result = dstParser.parse(command);
    final DistkvRequest incrScoreRequest = result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestType.SORTED_LIST_INCR_SCORE);
    Assert.assertEquals(incrScoreRequest.getKey(), "k1");
    Assert.assertEquals(incrScoreRequest.getRequest()
        .unpack(SlistIncrScoreRequest.class).getMember(), "m1");
    Assert.assertEquals(incrScoreRequest.getRequest()
        .unpack(SlistIncrScoreRequest.class).getDelta(), 20);
  }

  @Test
  public void testSlistPutMember() throws InvalidProtocolBufferException {
    final String command = "slist.putMember k1 m4 4";
    DistkvParsedResult result = dstParser.parse(command);
    final DistkvRequest putMemberRequest = result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestType.SORTED_LIST_PUT_MEMBER);
    Assert.assertEquals(putMemberRequest.getKey(), "k1");
    Assert.assertEquals(putMemberRequest.getRequest()
        .unpack(SlistPutMemberRequest.class).getScore(), 4);
    Assert.assertEquals(putMemberRequest.getRequest()
        .unpack(SlistPutMemberRequest.class).getMember(), "m4");
  }

  @Test
  public void testSlistRemoveMember() throws InvalidProtocolBufferException {
    final String command = "slist.removeMember k1 m4";
    DistkvParsedResult result = dstParser.parse(command);
    final DistkvRequest removeMemberRequest = result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestType.SORTED_LIST_REMOVE_MEMBER);
    Assert.assertEquals(removeMemberRequest.getKey(), "k1");
    Assert.assertEquals(removeMemberRequest.getRequest()
        .unpack(SlistRemoveMemberRequest.class).getMember(), "m4");
  }

  @Test
  public void testSlistDrop() {
    final String command = "slist.drop k1";
    DistkvParsedResult result = dstParser.parse(command);
    final DistkvRequest dropRequest = result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestType.SORTED_LIST_DROP);
    Assert.assertEquals(dropRequest.getKey(), "k1");
  }

  @Test
  public void testSlistGetMember() throws InvalidProtocolBufferException {
    final String command = "slist.getMember k1 m1";
    DistkvParsedResult result = dstParser.parse(command);
    final DistkvRequest getMemberRequest = result.getRequest();

    Assert.assertEquals(result.getRequestType(), RequestType.SORTED_LIST_GET_MEMBER);
    Assert.assertEquals(getMemberRequest.getKey(), "k1");
    Assert.assertEquals(getMemberRequest.getRequest()
        .unpack(SlistGetMemberRequest.class).getMember(), "m1");
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
