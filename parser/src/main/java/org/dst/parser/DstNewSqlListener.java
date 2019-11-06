package org.dst.parser;

import com.google.common.base.Preconditions;
import org.dst.parser.generated.DstNewSQLBaseListener;
import org.dst.parser.generated.DstNewSQLParser;
import org.dst.parser.po.DstParsedResult;
import org.dst.parser.po.RequestTypeEnum;
import org.dst.rpc.protobuf.generated.SetProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstNewSqlListener extends DstNewSQLBaseListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstNewSqlListener.class);

  private DstParsedResult parsedResult = null;

  public DstParsedResult getParsedResult() {
    return parsedResult;
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
