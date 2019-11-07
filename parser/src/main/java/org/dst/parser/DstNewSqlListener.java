package org.dst.parser;

import com.google.common.base.Preconditions;
import org.dst.parser.generated.DstNewSQLBaseListener;
import org.dst.parser.generated.DstNewSQLParser;
import org.dst.parser.po.DstParsedResult;
import org.dst.parser.po.RequestTypeEnum;
import org.dst.rpc.protobuf.generated.ListProtocol;
import org.dst.rpc.protobuf.generated.SetProtocol;
import org.dst.rpc.protobuf.generated.StringProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstNewSqlListener extends DstNewSQLBaseListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstNewSqlListener.class);

  private DstParsedResult parsedResult = null;

  public DstParsedResult getParsedResult() {
    return parsedResult;
  }

  @Override
  public void enterStrPut(DstNewSQLParser.StrPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    StringProtocol.PutRequest.Builder builder = StringProtocol.PutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setValue(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.STR_PUT, builder.build());
  }

  @Override
  public void enterStrGet(DstNewSQLParser.StrGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    StringProtocol.GetRequest.Builder builder = StringProtocol.GetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.STR_GET, builder.build());
  }

  @Override
  public void enterListPut(DstNewSQLParser.ListPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.PutRequest.Builder builder = ListProtocol.PutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValue(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_PUT, builder.build());
  }

  @Override
  public void enterListGet(DstNewSQLParser.ListGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    ListProtocol.GetRequest.Builder builder = ListProtocol.GetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_GET, builder.build());
  }

  @Override
  public void enterListLput(DstNewSQLParser.ListLputContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.LPutRequest.Builder builder = ListProtocol.LPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValue(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_LPUT, builder.build());
  }

  @Override
  public void enterListRput(DstNewSQLParser.ListRputContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.RPutRequest.Builder builder = ListProtocol.RPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValue(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_RPUT, builder.build());
  }

  @Override
  public void enterListLdel(DstNewSQLParser.ListLdelContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.LDelRequest.Builder builder = ListProtocol.LDelRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setValue(Integer.valueOf(ctx.children.get(2).getText()));
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_LDEL, builder.build());
  }

  @Override
  public void enterListRdel(DstNewSQLParser.ListRdelContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.RDelRequest.Builder builder = ListProtocol.RDelRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setValue(Integer.valueOf(ctx.children.get(2).getText()));
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_RDEL, builder.build());
  }

  @Override
  public void enterSetPut(DstNewSQLParser.SetPutContext ctx) {
    // The children of the `set_put` should be 3:
    //        `set.put    key     value_array`
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SetProtocol.PutRequest.Builder builder = SetProtocol.PutRequest.newBuilder();
    final String key = ctx.children.get(1).getText();
    builder.setKey(key);
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    SetProtocol.PutRequest request = builder.build();
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_PUT, request);
  }

  @Override
  public void enterSetGet(DstNewSQLParser.SetGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    SetProtocol.GetRequest.Builder builder = SetProtocol.GetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_GET, builder.build());
  }

  @Override
  public void enterSetDropByKey(DstNewSQLParser.SetDropByKeyContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    SetProtocol.DropByKeyRequest.Builder builder = SetProtocol.DropByKeyRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_DROP_BY_KEY, builder.build());
  }

}
